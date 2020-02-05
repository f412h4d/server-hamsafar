package org.hamsafar.hamsafar.graphql.services;

import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.AllArgsConstructor;
import org.hamsafar.hamsafar.model.Admin;
import org.hamsafar.hamsafar.repository.AdminRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

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
    public Admin getAdminById(String adminId) {
        return this.adminRepository.findByIdAndVerifiedTrue(adminId).orElse(null);
    }

    @GraphQLMutation
    public Admin addAdmin(String name,
                          String userName,
                          String password) {
        Optional<Admin> optionalAdmin = this.adminRepository.findAdminByUsernameAndVerifiedTrue(userName);

        if (optionalAdmin.isPresent()) {
            throw new RuntimeException("This Username ( " + userName + " ) is Already Taken");
        }

        return this.adminRepository.save(
                Admin.builder()
                        .name(name)
                        .username(userName)
                        .password(password)
                        .build()
        );
    }

    @GraphQLMutation
    public Admin editAdminById(String adminId,
                               String name,
                               String userName,
                               String password) {
        Optional<Admin> optionalAdmin = this.adminRepository.findByIdAndVerifiedTrue(adminId);
        if (optionalAdmin.isEmpty()) {
            throw new RuntimeException("Admin Not Found. Invalid Id");
        }
        if (name != null) {
            optionalAdmin.get().setName(name);
        }
        if (userName != null) {
            optionalAdmin.get().setUsername(userName);
        }
        if (password != null) {
            optionalAdmin.get().setPassword(password);
        }
        return this.adminRepository.save(optionalAdmin.get());
    }
}
