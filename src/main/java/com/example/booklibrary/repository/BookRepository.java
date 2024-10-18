package com.example.booklibrary.repository;

import com.example.booklibrary.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
}
