package org.hamsafar.hamsafar.repository;

import org.hamsafar.hamsafar.model.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedbackRepository extends MongoRepository<Feedback, String> {
    Optional<Feedback> findByIdAndVerifiedTrue(String id);

    List<Feedback> findAllByVerifiedTrue();

    Page<Feedback> findAllByVerified(Boolean isVerified, Pageable pageable);
}
