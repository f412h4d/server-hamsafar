package org.hamsafar.hamsafar.graphql.services.adjust;

import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hamsafar.hamsafar.model.City;
import org.hamsafar.hamsafar.repository.AdminRepository;
import org.hamsafar.hamsafar.repository.CityRepository;
import org.hamsafar.hamsafar.repository.PlaceRepository;
import org.hamsafar.hamsafar.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@GraphQLApi
@CrossOrigin
@AllArgsConstructor
public class CityAdjust {
    private final TagRepository tagRepository;
    private final CityRepository cityRepository;
    private final AdminRepository adminRepository;
    private final PlaceRepository placeRepository;

    @GraphQLMutation
    public City addCity(@GraphQLNonNull String englishName, @GraphQLNonNull String persianName) {
        List<String> names = new LinkedList<>();
        names.add(englishName);
        names.add(persianName);
        return this.cityRepository.save(City.builder()
                .names(names)
                .places(new LinkedHashSet<>())
                .build());
    }

    @GraphQLMutation
    public City editCity(@GraphQLNonNull String cityId,
                         String englishName,
                         String persianName) {
        Optional<City> optionalCity = this.cityRepository.findByIdAndVerifiedTrue(cityId);
        if (optionalCity.isEmpty()) {
            throw new RuntimeException("Invalid City Id, Not Found");
        }
        String enName = optionalCity.get().getNames().get(0), peName = optionalCity.get().getNames().get(1);

        if (englishName != null && englishName.length() > 0) {
            enName = englishName;
        }
        if (persianName != null && persianName.length() > 0) {
            peName = persianName;
        }
        List<String> names = new LinkedList<>();
        names.add(enName);
        names.add(peName);
        optionalCity.get().setNames(names);

        return this.cityRepository.save(optionalCity.get());
    }
}
