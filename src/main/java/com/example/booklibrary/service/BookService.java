package com.example.booklibrary.service;

import com.example.booklibrary.entities.Book;
import com.example.booklibrary.entities.Image;
import com.example.booklibrary.exceptions.BookNotFoundException;
import com.example.booklibrary.exceptions.ImageNotFoundException;
import com.example.booklibrary.repository.BookRepository;
import com.example.booklibrary.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private S3Client s3Client;

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
                .orElseThrow(() -> new BookNotFoundException("No book found with ID: " + id));
    }

    public void deleteBook(UUID id) {
        bookRepository.deleteById(id);
    }

    public Book addImageToBook(UUID bookId, MultipartFile file) throws IOException {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("No book found with ID: " + bookId));

        // Upload the image to S3 and get the metadata
        Image image = imageService.uploadImage(file);

        // Associate the image with the book
        book.getImageUuids().add(image.getUuid());
        return bookRepository.save(book);
    }

    public void deleteBookImage(UUID bookId, String imageUuid) {
        //System.out.println("**************");
        // Retrieve the book from the repository
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("No book found with ID: " + bookId));

        List<String> imageUuids = book.getImageUuids();
        //System.out.println("**************");
        //System.out.println(imageUuid);

        String bucketName = "booklibraryimages";
        if (imageUuids.contains(imageUuid)) {
            imageUuids.remove(imageUuid);
            book.setImageUuids(imageUuids);
            bookRepository.save(book);

            //need to add delete action in db
            imageRepository.deleteById(imageUuid);

            // Delete the image from the S3 bucket, try use s3 url
            s3Client.deleteObject(b -> b.bucket(bucketName).key(imageUuid));
            //System.out.println(imageUuid.toString());

            System.out.println("Image deleted successfully: " + imageUuid);
        } else {
            throw new ImageNotFoundException("Image UUID not found: " + imageUuid);
        }
    }
}
