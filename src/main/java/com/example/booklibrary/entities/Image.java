package com.example.booklibrary.entities;

import jakarta.persistence.*;

import lombok.Data;

@Entity
@Data
public class Image {
    @Id
    @Column(unique = true)
    private String uuid;

    private String fileName;  // Original file name
    private String fileType;  // MIME type (e.g., image/png)
    private String s3Url;     // S3 URL where the image is stored
}