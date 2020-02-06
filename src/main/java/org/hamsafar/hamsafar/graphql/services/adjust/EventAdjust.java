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

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Optional;

@Slf4j
@Service
@GraphQLApi
@CrossOrigin
@AllArgsConstructor
public class EventAdjust {
    private final TagRepository tagRepository;
    private final CityRepository cityRepository;
    private final AdminRepository adminRepository;
    private final EventRepository eventRepository;
    private final PlaceRepository placeRepository;

    @GraphQLMutation
    public Event addEventToCity(@GraphQLNonNull String tagId,
                                @GraphQLNonNull String cityId,
                                @GraphQLNonNull String adminId,
                                @GraphQLNonNull String placeId,
                                @GraphQLNonNull String title,
                                @GraphQLNonNull String header,
                                @GraphQLNonNull String detail,
                                @GraphQLNonNull Integer capacity,
                                @GraphQLNonNull Long time) {
        Optional<Tag> optionalTag = this.tagRepository.findByIdAndVerifiedTrue(tagId);
        if (optionalTag.isEmpty()) {
            throw new RuntimeException("Invalid Tag Id, Not Found");
        }
        Optional<City> optionalCity = this.cityRepository.findByIdAndVerifiedTrue(cityId);
        if (optionalCity.isEmpty()) {
            throw new RuntimeException("Invalid City Id, Not Found");
        }
        Optional<Admin> optionalAdmin = this.adminRepository.findByIdAndVerifiedTrue(adminId);
        if (optionalAdmin.isEmpty()) {
            throw new RuntimeException("Invalid Admin Id, Not Found");
        }
        Optional<Place> optionalPlace = this.placeRepository.findByIdAndVerifiedTrue(placeId);
        if (optionalPlace.isEmpty()) {
            throw new RuntimeException("Invalid Place Id, Not Found");
        }

        Event event = this.eventRepository.save(Event.builder()
                .admin(optionalAdmin.get())
                .tag(optionalTag.get())
                .city(optionalCity.get())
                .destination(optionalPlace.get())
                .title(title)
                .header(header)
                .detail(detail)
                .capacity(capacity)
                .date(new Date(new Timestamp(time).getTime()))
                .isAd(false)
                .adViews(0)
                .adLimit(0)
                .checkedIns(new LinkedHashSet<>())
                .feedbacks(new LinkedHashSet<>())
                .views(new LinkedHashSet<>())
                .books(new LinkedHashSet<>())
                // todo set pictures
                .picture(null)
                .build());

        optionalCity.get().getEvents().add(event);
        this.cityRepository.save(optionalCity.get());

        optionalAdmin.get().getEvents().add(event);
        this.adminRepository.save(optionalAdmin.get());

        optionalPlace.get().getEvents().add(event);
        this.placeRepository.save(optionalPlace.get());

        return event;
    }
}
