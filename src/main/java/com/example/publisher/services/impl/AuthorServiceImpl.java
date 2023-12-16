package com.example.publisher.services.impl;

import com.example.publisher.models.Author;
import com.example.publisher.models.Book;
import com.example.publisher.repository.AuthorRepository;
import com.example.publisher.repository.BookRepository;
import com.example.publisher.services.AuthorService;
import lombok.RequiredArgsConstructor;
import one.util.streamex.StreamEx;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    private final BookRepository bookRepository;


    @Override
    public Author create(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Author update(Author author, List<Long> bookIndices) {
        author.rewriteBooks(StreamEx.of(bookIndices)
                .mapPartial(bookRepository::findById)
                .toList());
        return authorRepository.save(author);
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Optional<Author> findById(Long ingredientId) {
        return authorRepository.findById(ingredientId);
    }

    @Override
    @Transactional
    public void deleteById(Long authorId) {
        authorRepository.findById(authorId)
                .ifPresent(author -> author.getBooks()
                        .forEach(book -> book.removeAuthor(author)));
        authorRepository.deleteById(authorId);
    }
    }
