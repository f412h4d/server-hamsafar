package org.hamsafar.hamsafar.repository;

import org.hamsafar.hamsafar.model.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends MongoRepository<Admin, String> {
    List<Admin> findAllByVerifiedTrue();

    Optional<Admin> findByIdAndVerifiedTrue(String id);

    Optional<Admin> findAdminByUsernameAndVerifiedTrue(String username);
}
