package org.hamsafar.hamsafar;

import org.hamsafar.hamsafar.payload.models.UploadedFile;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableMongoAuditing
@SpringBootApplication

@EnableConfigurationProperties({
        UploadedFile.class
})
public class HamsafarBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(HamsafarBackendApplication.class, args);
    }
}
