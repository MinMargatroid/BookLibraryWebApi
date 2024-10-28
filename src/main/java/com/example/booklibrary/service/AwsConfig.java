package com.example.booklibrary.service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

@Configuration
public class AwsConfig {

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.US_EAST_2)
                //.region(Region.of("US-EAST-2"))
                //.endpointOverride(URI.create("http://acs.amazonaws.com/groups/global/AuthenticatedUsers"))
                //.endpointOverride(URI.create("https://s3.us-east-2.amazonaws.com"))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create("", "")))
                .build();
    }
}

