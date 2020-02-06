package org.hamsafar.hamsafar.repository;

import org.hamsafar.hamsafar.model.Tag;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends MongoRepository<Tag, String> {
    List<Tag> findAllByVerifiedTrue();

    Optional<Tag> findTagByTitleAndVerifiedTrue(String title);
}
