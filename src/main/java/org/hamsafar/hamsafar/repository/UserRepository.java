package org.hamsafar.hamsafar.repository;

import org.hamsafar.hamsafar.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    List<User> findAllByVerifiedTrue();

    Page<User> findAllByVerified(Boolean isVerified, Pageable pageable);
}
