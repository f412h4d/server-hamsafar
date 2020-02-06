package org.hamsafar.hamsafar.graphql.services;

import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hamsafar.hamsafar.model.Event;
import org.hamsafar.hamsafar.model.Place;
import org.hamsafar.hamsafar.model.User;
import org.hamsafar.hamsafar.repository.EventRepository;
import org.hamsafar.hamsafar.repository.PlaceRepository;
import org.hamsafar.hamsafar.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@GraphQLApi
@CrossOrigin
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;
    private final EventRepository eventRepository;

    @GraphQLQuery
    public List<User> getAllUsers() {
        return this.userRepository.findAllByVerifiedTrue();
    }

    @GraphQLQuery
    public User getUserById(@GraphQLNonNull String userId) {
        Optional<User> optionalUser = this.userRepository.findByIdAndVerifiedTrue(userId);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Invalid User Id, Not Found");
        }
        return optionalUser.get();
    }

    @GraphQLQuery
    public User getUserByPhoneNumber(@GraphQLNonNull String phoneNumber) {
        Optional<User> optionalUser = this.userRepository.findByPhoneNumberAndVerifiedTrue(phoneNumber);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Invalid User Phone Number, Not Found");
        }
        return optionalUser.get();
    }

    @GraphQLQuery
    public User isCheckedInPlace(@GraphQLNonNull String phoneNumber, @GraphQLNonNull String placeId) {
        Optional<User> optionalUser = this.userRepository.findByPhoneNumberAndVerifiedTrue(phoneNumber);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Invalid User Phone Number, Not Found");
        }
        Optional<Place> optionalPlace = this.placeRepository.findByIdAndVerifiedTrue(placeId);
        if (optionalPlace.isEmpty()) {
            throw new RuntimeException("Invalid Place Id, Not Found");
        }
        if (optionalUser.get().getCheckedInPlaces().contains(optionalPlace.get())) {
            return optionalUser.get();
        }
        return null;
    }

    @GraphQLQuery
    public User isCheckedInEvent(@GraphQLNonNull String phoneNumber, @GraphQLNonNull String eventId) {
        Optional<User> optionalUser = this.userRepository.findByPhoneNumberAndVerifiedTrue(phoneNumber);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Invalid User Phone Number, Not Found");
        }
        Optional<Event> optionalEvent = this.eventRepository.findByIdAndVerifiedTrue(eventId);
        if (optionalEvent.isEmpty()) {
            throw new RuntimeException("Invalid Event Id, Not Found");
        }
        if (optionalUser.get().getCheckedInEvents().contains(optionalEvent.get())) {
            return optionalUser.get();
        }
        return null;
    }

    @GraphQLQuery
    public User hadBookedPlace(@GraphQLNonNull String phoneNumber, @GraphQLNonNull String placeId) {
        Optional<User> optionalUser = this.userRepository.findByPhoneNumberAndVerifiedTrue(phoneNumber);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Invalid User Phone Number, Not Found");
        }
        Optional<Place> optionalPlace = this.placeRepository.findByIdAndVerifiedTrue(placeId);
        if (optionalPlace.isEmpty()) {
            throw new RuntimeException("Invalid Place Id, Not Found");
        }
        if (optionalUser.get().getBookedPlaces().contains(optionalPlace.get())) {
            return optionalUser.get();
        }
        return null;
    }

    @GraphQLQuery
    public User hadBookedEvent(@GraphQLNonNull String phoneNumber, @GraphQLNonNull String eventId) {
        Optional<User> optionalUser = this.userRepository.findByPhoneNumberAndVerifiedTrue(phoneNumber);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Invalid User Phone Number, Not Found");
        }
        Optional<Event> optionalEvent = this.eventRepository.findByIdAndVerifiedTrue(eventId);
        if (optionalEvent.isEmpty()) {
            throw new RuntimeException("Invalid Event Id, Not Found");
        }
        if (optionalUser.get().getBookedEvents().contains(optionalEvent.get())) {
            return optionalUser.get();
        }
        return null;
    }
}
