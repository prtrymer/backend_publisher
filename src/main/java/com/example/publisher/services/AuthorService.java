package com.example.publisher.services;

import com.example.publisher.models.Author;
import com.example.publisher.models.Book;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AuthorService {
    Author create(Author author);

    List<Author> findAll();

    Optional<Author> findById(Long authorId);

    void deleteById(Long authorId);

    Optional<Set<Book>> getBooks(Long authorId);
}
