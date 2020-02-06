package org.hamsafar.hamsafar.graphql.services.adjust;

import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hamsafar.hamsafar.model.Admin;
import org.hamsafar.hamsafar.model.City;
import org.hamsafar.hamsafar.model.User;
import org.hamsafar.hamsafar.repository.AdminRepository;
import org.hamsafar.hamsafar.repository.CityRepository;
import org.hamsafar.hamsafar.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.LinkedHashSet;
import java.util.Optional;

@Slf4j
@Service
@GraphQLApi
@CrossOrigin
@AllArgsConstructor
public class UserAdjust {
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final CityRepository cityRepository;

    @GraphQLMutation
    public Admin addAdminOfUser(@GraphQLNonNull String phoneNumber, @GraphQLNonNull String password) {
        Optional<User> optionalUser = this.userRepository.findByPhoneNumberAndVerifiedTrue(phoneNumber);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Invalid User Phone Number, Not Found");
        }
        return this.adminRepository.save(Admin.builder()
                .name(optionalUser.get().getName())
                .username(optionalUser.get().getPhoneNumber())
                .companyName(optionalUser.get().getPhoneNumber())
                .credit(0)
                .password(password)
                .places(new LinkedHashSet<>())
                .build());
    }

    @GraphQLMutation
    public User addUser(@GraphQLNonNull String phoneNumber,
                        @GraphQLNonNull String name,
                        @GraphQLNonNull String cityId) {
        Optional<City> optionalCity = this.cityRepository.findByIdAndVerifiedTrue(cityId);
        if (optionalCity.isEmpty()) {
            throw new RuntimeException("Invalid City Id, Not Found");
        }
        return this.userRepository.save(User.builder()
                .phoneNumber(phoneNumber)
                .name(name)
                .city(optionalCity.get())
                .level(1)
                .badges(new LinkedHashSet<>())
                .booked(new LinkedHashSet<>())
                .visited(new LinkedHashSet<>())
                .build());
    }

    @GraphQLMutation
    public User editUser(
            @GraphQLNonNull String phoneNumber,
            String name,
            String cityId) {
        Optional<User> optionalUser = this.userRepository.findByPhoneNumberAndVerifiedTrue(phoneNumber);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Invalid User Id, Not Found");
        }
        if (cityId != null) {
            Optional<City> optionalCity = this.cityRepository.findByIdAndVerifiedTrue(cityId);
            if (optionalCity.isEmpty()) {
                throw new RuntimeException("Invalid City Id, Not Found");
            }
            optionalUser.get().setCity(optionalCity.get());
        }
        if (name != null) {
            optionalUser.get().setName(name);
        }
        return this.userRepository.save(optionalUser.get());
    }
}
