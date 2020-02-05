package org.hamsafar.hamsafar.dbseeder;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hamsafar.hamsafar.model.Admin;
import org.hamsafar.hamsafar.model.City;
import org.hamsafar.hamsafar.model.Place;
import org.hamsafar.hamsafar.repository.AdminRepository;
import org.hamsafar.hamsafar.repository.CityRepository;
import org.hamsafar.hamsafar.repository.PlaceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class Seeder implements CommandLineRunner {
    private final AdminRepository adminRepository;
    private final CityRepository cityRepository;
    private final PlaceRepository placeRepository;

    @Override
    public void run(String... args) {
//        init();
    }

    private void init() {
        log.info("Initializing The DB");
        List<String> names = new LinkedList<>();
        names.add("Kish");
        names.add("کیش");
        City city = City.builder()
                .names(names)
                .places(new LinkedHashSet<>())
                .data("RANDOM DATA")
                .build();
        this.cityRepository.save(city);

        Admin admin = adminRepository.save(
                Admin.builder()
                        .username("hayyaun")
                        .password("hayyaun")
                        .token("123456")
                        .places(new LinkedHashSet<>())
                        .credit(20)
                        .companyName("اکبر جوجه")
                        .build());
        this.adminRepository.save(admin);


        Place place = Place.builder()
                .admin(admin)
                .city(city)
                .detail("غذای خوش مزه داریم")
                .feedbacks(new LinkedHashSet<>())
                .pictures(new LinkedHashSet<>())
                .views(new LinkedHashSet<>())
                .lat(Float.parseFloat("35.5"))
                .lng(Float.parseFloat("45.5"))
                .rate(Float.parseFloat("3.5"))
                .rules("تست قوانین تست قوانین تست قوانین تست قوانین تست قوانین تست قوانین ")
                .title("اکبر جوجه شعبه صدف")
                .build();
        this.placeRepository.save(place);

        city.getPlaces().add(place);
        this.cityRepository.save(city);

        admin.getPlaces().add(place);
        this.adminRepository.save(admin);
    }
}
