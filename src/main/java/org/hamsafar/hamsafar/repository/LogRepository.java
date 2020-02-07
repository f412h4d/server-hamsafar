package org.hamsafar.hamsafar.repository;

import org.hamsafar.hamsafar.model.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LogRepository extends MongoRepository<Log, String> {
    Optional<Log> findByIdAndVerifiedTrue(String id);

    List<Log> findAllByUserIdAndPlaceIdAndActionOrderByDateDesc(String userId, String placeId, String action);

    List<Log> findAllByVerifiedTrue();

    Page<Log> findAllByVerified(Boolean isVerified, Pageable pageable);
}
