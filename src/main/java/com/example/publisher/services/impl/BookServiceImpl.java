package com.example.publisher.services.impl;

import com.example.publisher.models.Author;
import com.example.publisher.models.Book;
import com.example.publisher.repository.AuthorRepository;
import com.example.publisher.repository.BookRepository;
import com.example.publisher.repository.UserRepository;
import com.example.publisher.services.BookService;
import lombok.RequiredArgsConstructor;
import one.util.streamex.StreamEx;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    private final UserRepository userRepository;

    private final AuthorRepository authorRepository;

    @Override
    @Transactional
    public Book create(Book book, List<Long> authorIndices, String username) {
        StreamEx.of(authorIndices)
                .mapPartial(authorRepository::findById)
                .forEach(book::addAuthor);
        return bookRepository.save(book);
    }

    @Override
    public Book update(Book book, List<Long> authorIndices) {
        book.rewriteAuthors(StreamEx.of(authorIndices)
                .mapPartial(authorRepository::findById)
                .toList());
        return bookRepository.save(book);
    }

    @Override
    public List<Book> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).getContent();
    }

    @Override
    public Optional<Book> findById(Long bookId) {
        return bookRepository.findById(bookId);
    }

    @Override
    public void deleteById(Long commentId) {
        bookRepository.deleteById(commentId);
    }

    @Override
    public void addAuthors(Long bookId, List<Long> authorIndices) {

    }

    @Override
    public Optional<Set<Author>> getAuthors(Long bookId) {
        return Optional.empty();
    }

    @Override
    public String addImage(Long bookId, MultipartFile file) {
        return null;
    }

    @Override
    public Resource getImage(Long recipeId) {
        return null;
    }

    @Override
    public void deleteImage(Long recipeId) {

    }
}
