package org.hamsafar.hamsafar.graphql.services.search;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hamsafar.hamsafar.model.Place;
import org.hamsafar.hamsafar.repository.PlaceRepository;
import org.hamsafar.hamsafar.repository.TagRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
@GraphQLApi
@CrossOrigin
@AllArgsConstructor
public class PlaceSearch {
    private final TagRepository tagRepository;
    private final PlaceRepository placeRepository;

    @GraphQLQuery
    public Stream<Place> getAllPlacesBySizeAndOffset(@GraphQLArgument(name = "offset", defaultValue = "0", description = "Offset item from beginning of data") int offset,
                                                     @GraphQLArgument(name = "size", defaultValue = "10", description = "Limit the size of fetched results") int size) {
        log.error("Got Places By Size");
        Pageable pageable = PageRequest.of(offset, size);
        return this.placeRepository.findAllByVerified(true, pageable).get();
    }

    @GraphQLQuery
    public Stream<Place> sortAllPlacesByRate(@GraphQLArgument(name = "offset", defaultValue = "0", description = "Offset item from beginning of data") int offset,
                                             @GraphQLArgument(name = "size", defaultValue = "10", description = "Limit the size of fetched results") int size) {
        Pageable pageable = PageRequest.of(offset, size);
        return this.placeRepository.findAllByVerifiedOrderByRate(true, pageable).get();
    }


    @GraphQLQuery
    List<Place> getAllPlacesByTagId(@GraphQLNonNull String tagId) {
        return this.placeRepository.findAllByVerifiedTrueAndTag_Id(tagId);
    }
}
