package com.example.publisher.services;

import com.example.publisher.models.Author;
import com.example.publisher.models.Book;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BookService {
    Book create(Book book, List<Long> authorIndices, String username);

    Book update(Book book, List<Long> authorIndices);

    List<Book> findAll();

    Optional<Book> findById(Long bookId);

    void deleteById(Long bookId);

    void addAuthors(Long bookId, List<Long> authorIndices);
    Optional<Set<Author>> getAuthors(Long bookId);

    String addImage(Long bookId, MultipartFile file);

    Resource getImage(Long recipeId);

    void deleteImage(Long recipeId);
}
