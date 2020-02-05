package org.hamsafar.hamsafar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableMongoAuditing
@SpringBootApplication
public class HamsafarBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(HamsafarBackendApplication.class, args);
    }
}
