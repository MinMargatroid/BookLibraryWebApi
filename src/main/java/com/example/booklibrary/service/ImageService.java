package com.example.booklibrary.service;
import com.example.booklibrary.entities.Image;
import com.example.booklibrary.repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.core.sync.RequestBody;


import java.util.UUID;
import java.io.IOException;
@Service
public class ImageService {

    @Autowired
    private S3Client s3Client;

    @Autowired
    private ImageRepository imageRepository;

    public Image uploadImage(MultipartFile file) throws IOException {
        // Generate a UUID for the image
        UUID uuid = UUID.randomUUID();
        //System.out.println(uuid);

        // Define S3 key (file name) for the uploaded image
        String key = uuid.toString();
        //System.out.println("uuid key:" + key);

        // Upload the file to S3
        String bucketName = "booklibraryimages";
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(file.getContentType())
                .build();
        //System.out.println("40:::::::" + key);
        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

        // Build the S3 file URL (you may need to customize based on your S3 setup)
        //String s3Url = "https://" + bucketName + ".s3.amazonaws.com/" + key;
        String s3Url = "https://" + bucketName + ".s3.amazonaws.com/" + key;

        // Save image metadata in the database
        Image image = new Image();
        image.setUuid(key);
        //System.out.println("50::::::" + uuid);
        //image.setUuid_key(key);
        image.setFileName(file.getOriginalFilename());
        image.setFileType(file.getContentType());
        image.setS3Url(s3Url);

        return imageRepository.save(image);
    }

    public Image getImageByUuid(String uuid) {
        //meed to verify book id
        return imageRepository.findByUuid(uuid);
    }
}
