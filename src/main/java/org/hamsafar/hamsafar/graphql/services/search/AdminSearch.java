package org.hamsafar.hamsafar.graphql.services.search;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hamsafar.hamsafar.model.Place;
import org.hamsafar.hamsafar.repository.PlaceRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.stream.Stream;

@Slf4j
@Service
@GraphQLApi
@CrossOrigin
@AllArgsConstructor
public class AdminSearch {
    private final PlaceRepository placeRepository;

    @GraphQLQuery
    public Stream<Place> getAllAdminsPlacesBySizeAndOffset(@GraphQLNonNull String adminId,
                                                           @GraphQLArgument(name = "offset", defaultValue = "0", description = "Offset item from beginning of data") int offset,
                                                           @GraphQLArgument(name = "size", defaultValue = "10", description = "Limit the size of fetched results") int size) {
        log.error("Got Places By Size And Admin");
        Pageable pageable = PageRequest.of(offset, size);
        return placeRepository.findAllByVerifiedAndAdmin_Id(true, adminId, pageable).get();
    }
}
