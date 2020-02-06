package org.hamsafar.hamsafar.graphql.services;

import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hamsafar.hamsafar.model.Feedback;
import org.hamsafar.hamsafar.repository.FeedbackRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@GraphQLApi
@CrossOrigin
@AllArgsConstructor
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;

    @GraphQLQuery
    public List<Feedback> getAllFeedbacks() {
        return this.feedbackRepository.findAllByVerifiedTrue();
    }

    @GraphQLQuery
    public Feedback getFeedbackById(@GraphQLNonNull String eventId) {
        Optional<Feedback> optionalFeedback = this.feedbackRepository.findByIdAndVerifiedTrue(eventId);
        if (optionalFeedback.isEmpty()) {
            throw new RuntimeException("Invalid Feedback Id, Not Found");
        }
        return optionalFeedback.get();
    }
}
