package com.example.booklibrary.entities;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.Data;
import java.util.List;


@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    private String name;
    private String author;
    private Double price;
    private String owner;

    @ElementCollection // Specifies that this is a collection of simple types
    @CollectionTable(name = "book_images", joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "image_uuid") // Column name for the UUIDs
    private List<UUID> imageUuids;
}
