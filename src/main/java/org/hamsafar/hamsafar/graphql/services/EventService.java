package org.hamsafar.hamsafar.graphql.services;

import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hamsafar.hamsafar.model.Event;
import org.hamsafar.hamsafar.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@GraphQLApi
@CrossOrigin
@AllArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    @GraphQLQuery
    public List<Event> getAllEvents() {
        return this.eventRepository.findAllByVerifiedTrue();
    }

    @GraphQLQuery
    public Event getEventById(@GraphQLNonNull String id) {
        Optional<Event> optionalEvent = this.eventRepository.findByIdAndVerifiedTrue(id);
        if (optionalEvent.isEmpty()) {
            throw new RuntimeException("Invalid Event Id, Not Found");
        }
        return optionalEvent.get();
    }
}
