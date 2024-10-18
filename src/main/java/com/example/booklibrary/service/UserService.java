package com.example.booklibrary.service;

import com.example.booklibrary.entities.User;
import com.example.booklibrary.exceptions.BookNotFoundException;
import com.example.booklibrary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public User getUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("No book found with ID: " + id));
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}
