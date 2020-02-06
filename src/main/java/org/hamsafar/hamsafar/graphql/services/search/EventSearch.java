package org.hamsafar.hamsafar.graphql.services.search;

import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hamsafar.hamsafar.model.Event;
import org.hamsafar.hamsafar.model.Tag;
import org.hamsafar.hamsafar.model.audits.AuditModel;
import org.hamsafar.hamsafar.repository.EventRepository;
import org.hamsafar.hamsafar.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@GraphQLApi
@CrossOrigin
@AllArgsConstructor
public class EventSearch extends AuditModel {
    private final TagRepository tagRepository;
    private final EventRepository eventRepository;

    @GraphQLQuery
    public List<Event> getAllEvents() {
        return this.eventRepository.findAllByVerifiedTrue();
    }

    @GraphQLQuery
    Set<Event> getAllPlacesByTagId(@GraphQLNonNull String tagId) {
        Optional<Tag> optionalTag = this.tagRepository.findByIdAndVerifiedTrue(tagId);
        if (optionalTag.isEmpty()) {
            throw new RuntimeException("Invalid Tag Id, Not Found");
        }
        return optionalTag.get().getEvents();
    }
}
