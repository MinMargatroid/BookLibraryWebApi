package com.example.booklibrary.service;

import com.example.booklibrary.entities.Book;
import com.example.booklibrary.exceptions.BookNotFoundException;
import com.example.booklibrary.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(UUID id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("No book found with ID: " + id));
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(UUID id, Book updatedBook) {
        return bookRepository.findById(id)
                .map(book -> {
                    book.setName(updatedBook.getName());
                    book.setAuthor(updatedBook.getAuthor());
                    book.setOwner(updatedBook.getOwner());
                    book.setPrice(updatedBook.getPrice());
                    return bookRepository.save(book);
                })
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public void deleteBook(UUID id) {
        bookRepository.deleteById(id);
    }
}
