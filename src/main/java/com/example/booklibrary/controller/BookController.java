package com.example.booklibrary.controller;

import com.example.booklibrary.entities.Book;
import com.example.booklibrary.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable UUID id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }


    @PostMapping
    public Book addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable UUID id, @RequestBody Book book) {
        return bookService.updateBook(id, book);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable UUID id) {
        bookService.deleteBook(id);
    }

    @PostMapping("/{id}/images")
    public ResponseEntity<Book> uploadImage(@PathVariable UUID id, @RequestParam("image") MultipartFile file) throws IOException {
        Book book = bookService.addImageToBook(id, file);
        return ResponseEntity.ok(book);
    }

    @DeleteMapping("/{book_uuid}/images/{image_uuid}")
    public ResponseEntity<String> deleteImage(@PathVariable UUID book_uuid, @PathVariable UUID image_uuid) {
        bookService.deleteBookImage(book_uuid, image_uuid);
        return ResponseEntity.ok("Image deleted successfully");
    }
}
