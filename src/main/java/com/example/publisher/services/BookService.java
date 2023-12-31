package com.example.publisher.services;

import com.example.publisher.models.Author;
import com.example.publisher.models.Book;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BookService {
    Book create(Book book, List<Long> authorIndices);

    Book update(Book book, List<Long> authorIndices);

    List<Book> findAll(Pageable pageable);

    Optional<Book> findById(Long bookId);

    void deleteById(Long bookId);

    Optional<Set<Author>> getAuthors(Long bookId);

}
