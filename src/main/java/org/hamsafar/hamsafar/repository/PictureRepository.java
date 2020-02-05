package org.hamsafar.hamsafar.repository;

import org.hamsafar.hamsafar.model.Picture;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PictureRepository extends MongoRepository<Picture, String> {
    List<Picture> findAllByVerifiedTrue();
}
