package org.hamsafar.hamsafar.payload.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hamsafar.hamsafar.model.Picture;
import org.hamsafar.hamsafar.payload.services.FileStorageService;
import org.hamsafar.hamsafar.repository.PictureRepository;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@RestController
@AllArgsConstructor
@CrossOrigin
@Slf4j
public class PictureController {
    private final FileStorageService fileStorageService;
    private final PictureRepository pictureRepository;

    @PostMapping({"/uploadPic", "/uploadPic/"})
    public Picture uploadFile(@RequestParam("picture") MultipartFile picture) {
        final String PICTURE_NAME = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "-" + StringUtils.cleanPath(Objects.requireNonNull(picture.getOriginalFilename()));
        final String SERVER_PATH = "/home/sinoed/code/uploads/";

        fileStorageService.storeFile(picture, PICTURE_NAME);

        if (picture.getContentType() != null && (picture.getContentType().equals("image/jpeg") || picture.getContentType().equals("image/png"))) {
            fileStorageService.storeFile(picture, PICTURE_NAME);
            return pictureRepository.save(new Picture(SERVER_PATH + PICTURE_NAME));
        } else
            throw new RuntimeException("Sorry! invalid Picture Type -> " + PICTURE_NAME + ". Provide Picture/jpeg/png Type");
    }
}