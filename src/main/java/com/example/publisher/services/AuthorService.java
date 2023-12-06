package com.example.publisher.services;

import com.example.publisher.models.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    Author create(Author author);

    Author update(Author author);

    List<Author> findAll();

    Optional<Author> findById(Long authorId);

    void deleteById(Long authorId);

}
