package org.hamsafar.hamsafar.graphql.services.adjust;

import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hamsafar.hamsafar.model.Event;
import org.hamsafar.hamsafar.model.Feedback;
import org.hamsafar.hamsafar.model.Place;
import org.hamsafar.hamsafar.model.User;
import org.hamsafar.hamsafar.repository.EventRepository;
import org.hamsafar.hamsafar.repository.FeedbackRepository;
import org.hamsafar.hamsafar.repository.PlaceRepository;
import org.hamsafar.hamsafar.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@GraphQLApi
@CrossOrigin
@AllArgsConstructor
public class FeedbackAdjust {
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;
    private final EventRepository eventRepository;
    private final FeedbackRepository feedbackRepository;

    // todo the default confirm value is false but for testing purposes is set to true

    @GraphQLMutation
    public Feedback addFeedbackToPlace(@GraphQLNonNull String content,
                                       @GraphQLNonNull Integer rate,
                                       @GraphQLNonNull String placeId,
                                       @GraphQLNonNull String phoneNumber) {
        Optional<Place> optionalPlace = this.placeRepository.findByIdAndVerifiedTrue(placeId);
        if (optionalPlace.isEmpty()) {
            throw new RuntimeException("Invalid Place Id, Not Found");
        }
        Optional<User> optionalUser = this.userRepository.findByPhoneNumberAndVerifiedTrue(phoneNumber);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Invalid User Id, Not Found");
        }

        Feedback feedback = this.feedbackRepository.save(Feedback.builder()
                .content(content)
                .rate(rate)
                .isConfirmed(true)
                .event(null)
                .place(optionalPlace.get())
                .user(optionalUser.get())
                .build());

        optionalPlace.get().getFeedbacks().add(feedback);

        Set<Feedback> placeFeedbacks = optionalPlace.get().getFeedbacks();
        int placeFeedbacksLength = placeFeedbacks.size();
        float placeAverage = 0;

        for (Feedback f : placeFeedbacks) {
            placeAverage += f.getRate();
        }
        placeAverage /= placeFeedbacksLength;

        List<Feedback> allFeedbacks = this.feedbackRepository.findAllByVerifiedTrue();
        int allFeedbacksLength = allFeedbacks.size();
        float allPlacesAverage = 0;

        for (Feedback f : allFeedbacks) {
            allPlacesAverage += f.getRate();
        }
        placeAverage /= allFeedbacksLength;

        float newAverage = (placeAverage * placeFeedbacksLength + 5 * allPlacesAverage) / (placeFeedbacksLength + 5);

        optionalPlace.get().setRate(newAverage);
        this.placeRepository.save(optionalPlace.get());

        return feedback;
    }

    @GraphQLMutation
    public Feedback addFeedbackToEvent(@GraphQLNonNull String content,
                                       @GraphQLNonNull Integer rate,
                                       @GraphQLNonNull String eventId,
                                       @GraphQLNonNull String phoneNumber) {
        Optional<Event> optionalEvent = this.eventRepository.findByIdAndVerifiedTrue(eventId);
        if (optionalEvent.isEmpty()) {
            throw new RuntimeException("Invalid Event Id, Not Found");
        }
        Optional<User> optionalUser = this.userRepository.findByPhoneNumberAndVerifiedTrue(phoneNumber);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Invalid User Id, Not Found");
        }
        Feedback feedback = this.feedbackRepository.save(Feedback.builder()
                .content(content)
                .rate(rate)
                .isConfirmed(true)
                .event(optionalEvent.get())
                .place(null)
                .user(optionalUser.get())
                .build());

        optionalEvent.get().getFeedbacks().add(feedback);

        Set<Feedback> placeFeedbacks = optionalEvent.get().getFeedbacks();
        int eventFeedbacksLength = placeFeedbacks.size();
        float eventAverage = 0;

        for (Feedback f : placeFeedbacks) {
            eventAverage += f.getRate();
        }
        eventAverage /= eventFeedbacksLength;

        List<Feedback> allFeedbacks = this.feedbackRepository.findAllByVerifiedTrue();
        int allFeedbacksLength = allFeedbacks.size();
        float allEventsAverage = 0;

        for (Feedback f : allFeedbacks) {
            allEventsAverage += f.getRate();
        }
        eventAverage /= allFeedbacksLength;

        float newAverage = (eventAverage * eventFeedbacksLength + 5 * allEventsAverage) / (eventFeedbacksLength + 5);

        optionalEvent.get().setRate(newAverage);
        this.eventRepository.save(optionalEvent.get());

        return feedback;
    }

    @GraphQLMutation
    public Feedback confirmFeedback(@GraphQLNonNull String feedbackId) {
        Optional<Feedback> optionalFeedback = this.feedbackRepository.findByIdAndVerifiedTrue(feedbackId);
        if (optionalFeedback.isEmpty()) {
            throw new RuntimeException("Invalid Feedback Id, Not Found");
        }
        optionalFeedback.get().setIsConfirmed(true);
        return optionalFeedback.get();
    }
}
