package org.hamsafar.hamsafar.graphql.services;

import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hamsafar.hamsafar.model.Admin;
import org.hamsafar.hamsafar.repository.AdminRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Slf4j
@Service
@GraphQLApi
@CrossOrigin
@AllArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;

    @GraphQLQuery
    public List<Admin> getAllAdmins() {
        return this.adminRepository.findAllByVerifiedTrue();
    }

    @GraphQLQuery
    public Admin getAdminById(@GraphQLNonNull String adminId) {
        return this.adminRepository.findByIdAndVerifiedTrue(adminId).orElse(null);
    }
}
