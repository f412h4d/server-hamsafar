package org.hamsafar.hamsafar.repository;

import org.hamsafar.hamsafar.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {
    Optional<Event> findByIdAndVerifiedTrue(String id);

    List<Event> findAllByVerifiedTrue();

    Page<Event> findAllByVerified(Boolean isVerified, Pageable pageable);
}
