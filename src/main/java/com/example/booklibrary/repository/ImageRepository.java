package com.example.booklibrary.repository;

import com.example.booklibrary.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ImageRepository extends JpaRepository<Image, UUID> {
    Image findByUuid(UUID uuid);
}
