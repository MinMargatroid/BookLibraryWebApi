package com.example.booklibrary.controller;

import com.example.booklibrary.entities.Image;
import com.example.booklibrary.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("/books/{book_uuid}/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @GetMapping("/{image_uuid}")
    public ResponseEntity<String> getImageByUuid(@PathVariable String image_uuid) {
        Image image = imageService.getImageByUuid(image_uuid);
        //System.out.println(image_uuid);
        //System.out.println("*************");
        //System.out.println(image_uuid.toString());
        //System.out.println("*************");
        if (image != null) {
            // Return the S3 URL where the image is stored
            return ResponseEntity.ok(image.getS3Url());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

