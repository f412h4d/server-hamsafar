package org.hamsafar.hamsafar.graphql.services.adjust;

import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hamsafar.hamsafar.model.Admin;
import org.hamsafar.hamsafar.repository.AdminRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;

@Slf4j
@Service
@GraphQLApi
@CrossOrigin
@AllArgsConstructor
public class AdminAdjust {
    private final AdminRepository adminRepository;

    @GraphQLMutation
    public Admin addAdmin(String name,
                          @GraphQLNonNull String userName,
                          @GraphQLNonNull String password) {
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
    public Admin editAdminById(@GraphQLNonNull String adminId,
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
