package com.example.booklibrary.entities;

import jakarta.persistence.*;

import java.util.UUID;
import lombok.Data;

@Entity
@Data
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID uuid;

    private String uuid_key;  //change this to uuid
    private String fileName;  // Original file name
    private String fileType;  // MIME type (e.g., image/png)
    private String s3Url;     // S3 URL where the image is stored
}