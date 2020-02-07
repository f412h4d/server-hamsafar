package org.hamsafar.hamsafar.repository;

import org.hamsafar.hamsafar.model.Video;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends MongoRepository<Video, String> {
}
