package org.hamsafar.hamsafar.repository;

import org.hamsafar.hamsafar.model.Badge;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BadgeRepository extends MongoRepository<Badge, String> {
    Optional<Badge> findByIdAndVerifiedTrue(String id);

    List<Badge> findAllByVerifiedTrue();
}
