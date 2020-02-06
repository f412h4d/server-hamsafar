package org.hamsafar.hamsafar.graphql.services.adjust;

import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hamsafar.hamsafar.model.Admin;
import org.hamsafar.hamsafar.model.City;
import org.hamsafar.hamsafar.model.Place;
import org.hamsafar.hamsafar.model.Tag;
import org.hamsafar.hamsafar.repository.AdminRepository;
import org.hamsafar.hamsafar.repository.CityRepository;
import org.hamsafar.hamsafar.repository.PlaceRepository;
import org.hamsafar.hamsafar.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.LinkedHashSet;
import java.util.Optional;

@Slf4j
@Service
@GraphQLApi
@CrossOrigin
@AllArgsConstructor
public class PlaceAdjust {
    private final TagRepository tagRepository;
    private final CityRepository cityRepository;
    private final AdminRepository adminRepository;
    private final PlaceRepository placeRepository;

    @GraphQLMutation
    public Place addPlaceToCity(@GraphQLNonNull String tagId,
                                @GraphQLNonNull String cityId,
                                @GraphQLNonNull String adminId,
                                @GraphQLNonNull String title,
                                @GraphQLNonNull String header,
                                @GraphQLNonNull String detail,
                                @GraphQLNonNull String rules,
                                @GraphQLNonNull Integer checkInRadius,
                                @GraphQLNonNull Float lat,
                                @GraphQLNonNull Float lng) {
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

        Place place = this.placeRepository.save(Place.builder()
                .admin(optionalAdmin.get())
                .tag(optionalTag.get())
                .city(optionalCity.get())
                .title(title)
                .header(header)
                .detail(detail)
                .rules(rules)
                .lat(lat)
                .lng(lng)
                .checkInRadius(checkInRadius)
                .isAd(false)
                .adViews(0)
                .adLimit(0)
                .checkedIns(new LinkedHashSet<>())
                .feedbacks(new LinkedHashSet<>())
                .events(new LinkedHashSet<>())
                .views(new LinkedHashSet<>())
                // todo set pictures
                .pictures(new LinkedHashSet<>())
                .build());

        optionalCity.get().getPlaces().add(place);
        this.cityRepository.save(optionalCity.get());

        optionalAdmin.get().getPlaces().add(place);
        this.adminRepository.save(optionalAdmin.get());

        return place;
    }
}
