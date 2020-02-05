package org.hamsafar.hamsafar.repository;

import org.hamsafar.hamsafar.model.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends MongoRepository<Feedback, String> {
    List<Feedback> findAllByVerifiedTrue();

    Page<Feedback> findAllByVerified(Boolean isVerified, Pageable pageable);
}
