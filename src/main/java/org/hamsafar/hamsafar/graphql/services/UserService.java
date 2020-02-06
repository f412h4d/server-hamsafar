package org.hamsafar.hamsafar.graphql.services;

import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hamsafar.hamsafar.model.User;
import org.hamsafar.hamsafar.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@GraphQLApi
@CrossOrigin
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @GraphQLQuery
    public List<User> getAllUsers() {
        return this.userRepository.findAllByVerifiedTrue();
    }

    @GraphQLQuery
    public User getUserById(@GraphQLNonNull String userId) {
        Optional<User> optionalUser = this.userRepository.findByIdAndVerifiedTrue(userId);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Invalid User Id, Not Found");
        }
        return optionalUser.get();
    }

    @GraphQLQuery
    public User getUserByPhoneNumber(@GraphQLNonNull String phoneNumber) {
        Optional<User> optionalUser = this.userRepository.findByPhoneNumberAndVerifiedTrue(phoneNumber);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Invalid User Phone Number, Not Found");
        }
        return optionalUser.get();
    }
}
