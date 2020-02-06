package org.hamsafar.hamsafar.graphql.services;

import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hamsafar.hamsafar.model.City;
import org.hamsafar.hamsafar.repository.CityRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Slf4j
@Service
@GraphQLApi
@CrossOrigin
@AllArgsConstructor
public class CityService {
    private final CityRepository cityRepository;

    @GraphQLQuery
    public City getCityById(@GraphQLNonNull String cityId) {
        log.error("Got Cities By ID");
        return cityRepository.findById(cityId).orElse(null);
    }

    @GraphQLQuery
    public List<City> getAllCities() {
        return cityRepository.findAllByVerifiedTrue();
    }
}
