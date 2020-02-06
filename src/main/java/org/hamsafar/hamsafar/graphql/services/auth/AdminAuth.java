package org.hamsafar.hamsafar.graphql.services.auth;

import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hamsafar.hamsafar.graphql.services.adjust.AdminAdjust;
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
public class AdminAuth {
    private final AdminAdjust adminAdjust;
    private final AdminRepository adminRepository;

    @GraphQLMutation
    public Admin signUpAdmin(String name,
                             @GraphQLNonNull String username,
                             @GraphQLNonNull String password) {
        return adminAdjust.addAdmin(name, username, password);
    }

    @GraphQLMutation
    public Admin loginAdmin(@GraphQLNonNull String username, @GraphQLNonNull String password) {
        Optional<Admin> optionalAdmin = this.adminRepository.findAdminByUsernameAndVerifiedTrue(username);
        if (optionalAdmin.isEmpty())
            throw new RuntimeException("Username Not Found !");

        if (!optionalAdmin.get().getPassword().equals(password))
            throw new RuntimeException("Password is Not Correct !");

        log.error("Logged In");
        return optionalAdmin.get();
    }
}
