package org.hamsafar.hamsafar.graphql.services.adjust;

import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hamsafar.hamsafar.model.*;
import org.hamsafar.hamsafar.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.LinkedHashSet;
import java.util.Optional;

@Slf4j
@Service
@GraphQLApi
@CrossOrigin
@AllArgsConstructor
public class UserAdjust {
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final CityRepository cityRepository;
    private final PlaceRepository placeRepository;
    private final EventRepository eventRepository;

    @GraphQLMutation
    public Admin addAdminOfUser(@GraphQLNonNull String phoneNumber, @GraphQLNonNull String password) {
        Optional<User> optionalUser = this.userRepository.findByPhoneNumberAndVerifiedTrue(phoneNumber);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Invalid User Phone Number, Not Found");
        }
        return this.adminRepository.save(Admin.builder()
                .name(optionalUser.get().getName())
                .username(optionalUser.get().getPhoneNumber())
                .credit(0)
                .password(password)
                .places(new LinkedHashSet<>())
                .build());
    }

    @GraphQLMutation
    public User addUser(@GraphQLNonNull String phoneNumber,
                        @GraphQLNonNull String name,
                        @GraphQLNonNull String cityId) {
        Optional<City> optionalCity = this.cityRepository.findByIdAndVerifiedTrue(cityId);
        if (optionalCity.isEmpty()) {
            throw new RuntimeException("Invalid City Id, Not Found");
        }
        return this.userRepository.save(User.builder()
                .phoneNumber(phoneNumber)
                .name(name)
                .city(optionalCity.get())
                .level(1)
                .score(0)
                .levelLimit(15)
                .badges(new LinkedHashSet<>())
                .bookedPlaces(new LinkedHashSet<>())
                .bookedEvents(new LinkedHashSet<>())
                .viewedPlaces(new LinkedHashSet<>())
                .viewedEvents(new LinkedHashSet<>())
                .checkedInPlaces(new LinkedHashSet<>())
                .checkedInEvents(new LinkedHashSet<>())
                .build());
    }

    @GraphQLMutation
    public User editUser(
            @GraphQLNonNull String phoneNumber,
            String name,
            String cityId) {
        Optional<User> optionalUser = this.userRepository.findByPhoneNumberAndVerifiedTrue(phoneNumber);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Invalid User Id, Not Found");
        }
        if (cityId != null) {
            Optional<City> optionalCity = this.cityRepository.findByIdAndVerifiedTrue(cityId);
            if (optionalCity.isEmpty()) {
                throw new RuntimeException("Invalid City Id, Not Found");
            }
            optionalUser.get().setCity(optionalCity.get());
        }
        if (name != null) {
            optionalUser.get().setName(name);
        }
        return this.userRepository.save(optionalUser.get());
    }

    @GraphQLMutation
    public User checkInPlace(@GraphQLNonNull String phoneNumber,
                             @GraphQLNonNull String placeId) {
        Optional<User> optionalUser = this.userRepository.findByPhoneNumberAndVerifiedTrue(phoneNumber);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Invalid User Id, Not Found");
        }
        Optional<Place> optionalPlace = this.placeRepository.findByIdAndVerifiedTrue(placeId);
        if (optionalPlace.isEmpty()) {
            throw new RuntimeException("Invalid Place Id, Not Found");
        }
        optionalPlace.get().getCheckedIns().add(optionalUser.get());
        this.placeRepository.save(optionalPlace.get());

        optionalUser.get().getCheckedInPlaces().add(optionalPlace.get());

        return this.userRepository.save(optionalUser.get());
    }

    @GraphQLMutation
    public User checkInEvent(@GraphQLNonNull String phoneNumber,
                             @GraphQLNonNull String eventId) {
        Optional<User> optionalUser = this.userRepository.findByPhoneNumberAndVerifiedTrue(phoneNumber);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Invalid User Id, Not Found");
        }
        Optional<Event> optionalEvent = this.eventRepository.findByIdAndVerifiedTrue(eventId);
        if (optionalEvent.isEmpty()) {
            throw new RuntimeException("Invalid Event Id, Not Found");
        }
        optionalEvent.get().getCheckedIns().add(optionalUser.get());
        this.eventRepository.save(optionalEvent.get());

        optionalUser.get().getCheckedInEvents().add(optionalEvent.get());

        return this.userRepository.save(optionalUser.get());
    }
}
