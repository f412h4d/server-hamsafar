package org.hamsafar.hamsafar.graphql.services;

import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hamsafar.hamsafar.model.Place;
import org.hamsafar.hamsafar.repository.PlaceRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@GraphQLApi
@CrossOrigin
@AllArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;

    @GraphQLQuery
    public List<Place> getAllPlaces() {
        return this.placeRepository.findAllByVerifiedTrue();
    }

    @GraphQLQuery
    public Place getPlaceById(@GraphQLNonNull String placeId) {
        Optional<Place> optionalPlace = this.placeRepository.findByIdAndVerifiedTrue(placeId);
        if (optionalPlace.isEmpty()) {
            throw new RuntimeException("Invalid Place Id, Not Found");
        }
        return optionalPlace.get();
    }
}
