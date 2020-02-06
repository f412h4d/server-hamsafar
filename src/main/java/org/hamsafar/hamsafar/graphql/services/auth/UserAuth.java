package org.hamsafar.hamsafar.graphql.services.auth;

import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hamsafar.hamsafar.graphql.services.UserService;
import org.hamsafar.hamsafar.graphql.services.adjust.UserAdjust;
import org.hamsafar.hamsafar.model.User;
import org.hamsafar.hamsafar.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;

@Slf4j
@Service
@GraphQLApi
@CrossOrigin
@AllArgsConstructor
public class UserAuth {
    private final UserAdjust userAdjust;
    private final UserService userService;
    private final UserRepository userRepository;

    @GraphQLQuery
    public User hadSignUp(@GraphQLNonNull String phoneNumber) {
        return this.userRepository.findByPhoneNumberAndVerifiedTrue(phoneNumber).orElse(null);
    }

    @GraphQLMutation
    public User signUpUser(String name,
                           @GraphQLNonNull String cityId,
                           @GraphQLNonNull String phoneNumber) {
        Optional<User> optionalUser = this.userRepository.findByPhoneNumberAndVerifiedTrue(phoneNumber);
        if (optionalUser.isPresent()) {
            throw new RuntimeException("Phone Number Already Exist");
        }
        return this.userAdjust.addUser(phoneNumber, name, cityId);
    }

    @GraphQLMutation
    public User loginUser(@GraphQLNonNull String phoneNumber) {
        return this.userService.getUserByPhoneNumber(phoneNumber);
    }
}
