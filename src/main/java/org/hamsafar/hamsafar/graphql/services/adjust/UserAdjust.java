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

import java.util.*;

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
    private final LogRepository logRepository;

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
                .events(new LinkedHashSet<>())
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
                .logs(new LinkedList<>())
                .badges(new LinkedHashSet<>())
                .bookedPlaces(new LinkedHashSet<>())
                .bookedEvents(new LinkedHashSet<>())
                .viewedPlaces(new LinkedHashSet<>())
                .viewedEvents(new LinkedHashSet<>())
                .checkedInPlaces(new LinkedList<>())
                .checkedInEvents(new LinkedList<>())
                .bookedPlaces(new LinkedHashSet<>())
                .bookedEvents(new LinkedHashSet<>())
                .invitedPlaces(new LinkedHashSet<>())
                .invitedEvents(new LinkedHashSet<>())
                .build());
    }

    @GraphQLMutation
    public User editUser(
            @GraphQLNonNull String phoneNumber,
            String name,
            String cityId) {
        Optional<User> optionalUser = this.userRepository.findByPhoneNumberAndVerifiedTrue(phoneNumber);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Invalid User Phone Number, Not Found");
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
            throw new RuntimeException("Invalid User Phone Number, Not Found");
        }
        Optional<Place> optionalPlace = this.placeRepository.findByIdAndVerifiedTrue(placeId);
        if (optionalPlace.isEmpty()) {
            throw new RuntimeException("Invalid Place Id, Not Found");
        }
        List<Log> logs = this.logRepository.findAllByUserIdAndPlaceIdAndActionOrderByDateDesc(optionalUser.get().getId(), optionalPlace.get().getId(), "checkInPlace");

        if (!logs.isEmpty()) {
            Date oldDate = logs.get(0).getDate();
            Calendar c = Calendar.getInstance();
            c.setTime(oldDate);
            c.add(Calendar.DAY_OF_MONTH, 1);
            if (new Date().before(c.getTime())) {
                throw new RuntimeException("You Can CheckIn Once A Day");
            }
        }

        optionalPlace.get().getCheckedIns().add(optionalUser.get());
        this.placeRepository.save(optionalPlace.get());

        Log placeLog = this.logRepository.save(Log.builder()
                .action("checkInPlace")
                .date(new Date())
                .place(optionalPlace.get())
                .event(null)
                .user(optionalUser.get())
                .build());

        optionalUser.get().getLogs().add(placeLog);
        optionalUser.get().getCheckedInPlaces().add(optionalPlace.get());

        log.error(optionalUser.get().toString());

        return this.userRepository.save(optionalUser.get());
    }

    @GraphQLMutation
    public User checkInEvent(@GraphQLNonNull String phoneNumber,
                             @GraphQLNonNull String eventId) {
        Optional<User> optionalUser = this.userRepository.findByPhoneNumberAndVerifiedTrue(phoneNumber);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Invalid User Phone Number, Not Found");
        }
        Optional<Event> optionalEvent = this.eventRepository.findByIdAndVerifiedTrue(eventId);
        if (optionalEvent.isEmpty()) {
            throw new RuntimeException("Invalid Event Id, Not Found");
        }
        List<Log> logs = this.logRepository.findAllByUserIdAndEventIdAndActionOrderByDateDesc(optionalUser.get().getId(), optionalEvent.get().getId(), "checkInEvent");

        if (!logs.isEmpty()) {
            throw new RuntimeException("You Can CheckIn Only Once");
        }

        optionalEvent.get().getCheckedIns().add(optionalUser.get());
        this.eventRepository.save(optionalEvent.get());

        Log log = this.logRepository.save(Log.builder()
                .action("checkInEvent")
                .date(new Date())
                .place(null)
                .event(optionalEvent.get())
                .user(optionalUser.get())
                .build());
        optionalUser.get().getLogs().add(log);
        optionalUser.get().getCheckedInEvents().add(optionalEvent.get());

        return this.userRepository.save(optionalUser.get());
    }

    @GraphQLMutation
    public User viewPlace(@GraphQLNonNull String phoneNumber,
                          @GraphQLNonNull String placeId) {
        Optional<User> optionalUser = this.userRepository.findByPhoneNumberAndVerifiedTrue(phoneNumber);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Invalid User Phone Number, Not Found");
        }
        Optional<Place> optionalPlace = this.placeRepository.findByIdAndVerifiedTrue(placeId);
        if (optionalPlace.isEmpty()) {
            throw new RuntimeException("Invalid Place Id, Not Found");
        }
        optionalPlace.get().getViews().add(optionalUser.get());
        this.placeRepository.save(optionalPlace.get());

        optionalUser.get().getViewedPlaces().add(optionalPlace.get());

        return this.userRepository.save(optionalUser.get());
    }

    @GraphQLMutation
    public User viewEvent(@GraphQLNonNull String phoneNumber,
                          @GraphQLNonNull String eventId) {
        Optional<User> optionalUser = this.userRepository.findByPhoneNumberAndVerifiedTrue(phoneNumber);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Invalid User Phone Number, Not Found");
        }
        Optional<Event> optionalEvent = this.eventRepository.findByIdAndVerifiedTrue(eventId);
        if (optionalEvent.isEmpty()) {
            throw new RuntimeException("Invalid Event Id, Not Found");
        }
        optionalEvent.get().getViews().add(optionalUser.get());
        this.eventRepository.save(optionalEvent.get());

        optionalUser.get().getViewedEvents().add(optionalEvent.get());

        return this.userRepository.save(optionalUser.get());
    }

    @GraphQLMutation
    public User bookPlace(@GraphQLNonNull String phoneNumber,
                          @GraphQLNonNull String placeId) {
        Optional<User> optionalUser = this.userRepository.findByPhoneNumberAndVerifiedTrue(phoneNumber);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Invalid User Phone Number, Not Found");
        }
        Optional<Place> optionalPlace = this.placeRepository.findByIdAndVerifiedTrue(placeId);
        if (optionalPlace.isEmpty()) {
            throw new RuntimeException("Invalid Place Id, Not Found");
        }
        optionalPlace.get().getBooks().add(optionalUser.get());
        this.placeRepository.save(optionalPlace.get());

        optionalUser.get().getBookedPlaces().add(optionalPlace.get());

        return this.userRepository.save(optionalUser.get());
    }

    @GraphQLMutation
    public User bookEvent(@GraphQLNonNull String phoneNumber,
                          @GraphQLNonNull String eventId) {
        Optional<User> optionalUser = this.userRepository.findByPhoneNumberAndVerifiedTrue(phoneNumber);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Invalid User Phone Number, Not Found");
        }
        Optional<Event> optionalEvent = this.eventRepository.findByIdAndVerifiedTrue(eventId);
        if (optionalEvent.isEmpty()) {
            throw new RuntimeException("Invalid Event Id, Not Found");
        }
        optionalEvent.get().getBooks().add(optionalUser.get());
        this.eventRepository.save(optionalEvent.get());

        optionalUser.get().getBookedEvents().add(optionalEvent.get());

        return this.userRepository.save(optionalUser.get());
    }

    @GraphQLMutation
    public User inviteToEvent(@GraphQLNonNull String inviterPhoneNumber,
                              @GraphQLNonNull String guestPhoneNumber,
                              @GraphQLNonNull String eventId) {
        Optional<User> optionalInviterUser = this.userRepository.findByPhoneNumberAndVerifiedTrue(inviterPhoneNumber);
        if (optionalInviterUser.isEmpty()) {
            throw new RuntimeException("Invalid User Phone Number, Not Found");
        }
        Optional<User> optionalGuestUser = this.userRepository.findByPhoneNumberAndVerifiedTrue(guestPhoneNumber);
        if (optionalGuestUser.isEmpty()) {
            throw new RuntimeException("Invalid User Phone Number, Not Found");
        }
        Optional<Event> optionalEvent = this.eventRepository.findByIdAndVerifiedTrue(eventId);
        if (optionalEvent.isEmpty()) {
            throw new RuntimeException("Invalid Event Id, Not Found");
        }

        // todo sent push
        optionalGuestUser.get().getInvitedEvents().add(optionalEvent.get());
        return this.userRepository.save(optionalGuestUser.get());
    }

    @GraphQLMutation
    public User inviteToPlace(@GraphQLNonNull String inviterPhoneNumber,
                              @GraphQLNonNull String guestPhoneNumber,
                              @GraphQLNonNull String placeId) {
        Optional<User> optionalInviterUser = this.userRepository.findByPhoneNumberAndVerifiedTrue(inviterPhoneNumber);
        if (optionalInviterUser.isEmpty()) {
            throw new RuntimeException("Invalid User Phone Number, Not Found");
        }
        Optional<User> optionalGuestUser = this.userRepository.findByPhoneNumberAndVerifiedTrue(guestPhoneNumber);
        if (optionalGuestUser.isEmpty()) {
            throw new RuntimeException("Invalid User Phone Number, Not Found");
        }
        Optional<Place> optionalPlace = this.placeRepository.findByIdAndVerifiedTrue(placeId);
        if (optionalPlace.isEmpty()) {
            throw new RuntimeException("Invalid Place Id, Not Found");
        }

        // todo sent push
        optionalGuestUser.get().getInvitedPlaces().add(optionalPlace.get());
        return this.userRepository.save(optionalGuestUser.get());
    }
}
