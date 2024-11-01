package com.example.booklibrary.controller;

import com.example.booklibrary.entities.Book;
import com.example.booklibrary.entities.Image;
import com.example.booklibrary.exceptions.BookNotFoundException;
import com.example.booklibrary.exceptions.ImageNotFoundException;
import com.example.booklibrary.service.BookService;
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

    @Autowired
    private BookService bookService;

    @GetMapping("/{image_uuid}")
    public ResponseEntity<String> getImageByUuid(@PathVariable UUID book_uuid, @PathVariable String image_uuid) {
        Book book = bookService.getBookById(book_uuid);
        if (book != null) {
            List<String> imageUuids = book.getImageUuids();
            if (imageUuids.contains(image_uuid)) {
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
            } else {
                throw new ImageNotFoundException("Image UUID not found: " + image_uuid);
            }
        } else {
            throw new BookNotFoundException("Book UUID not found: " + book_uuid);
        }
    }

    //need update images
    @PutMapping("/{image_uuid}")
    public Image updateImage(@PathVariable UUID book_uuid, @PathVariable String image_uuid, @RequestBody Image updatedImage) {
        Book book = bookService.getBookById(book_uuid);
        if (book != null) {
            List<String> imageUuids = book.getImageUuids();
            if (imageUuids.contains(image_uuid)) {
                return imageService.updateImage(image_uuid, updatedImage);
            } else {
                throw new ImageNotFoundException("Image UUID not found: " + image_uuid);
            }
        } else {
            throw new BookNotFoundException("Book UUID not found: " + book_uuid);
        }

    }
}

