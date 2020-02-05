package org.hamsafar.hamsafar.repository;

import org.hamsafar.hamsafar.model.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends MongoRepository<City, String> {
    Optional<City> findByNamesContains(List<String> names);

    List<City> findAllByVerifiedTrue();

    Page<City> findAllByVerified(Boolean isVerified, Pageable pageable);
}
