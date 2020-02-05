package org.hamsafar.hamsafar.repository;

import org.hamsafar.hamsafar.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {
    List<Event> findAllByVerifiedTrue();

    Page<Event> findAllByVerified(Boolean isVerified, Pageable pageable);
}
