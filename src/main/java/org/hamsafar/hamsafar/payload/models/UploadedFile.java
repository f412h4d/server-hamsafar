package org.hamsafar.hamsafar.payload.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
@Setter
@Getter
public class UploadedFile {
    private String uploadDir;
}
