package com.example.publisher.services.impl;

import com.example.publisher.models.Author;
import com.example.publisher.models.Book;
import com.example.publisher.repository.AuthorRepository;
import com.example.publisher.repository.BookRepository;
import com.example.publisher.services.BookService;
import lombok.RequiredArgsConstructor;
import one.util.streamex.StreamEx;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

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
    public void deleteById(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    @Override
    public Optional<Set<Author>> getAuthors(Long bookId) {
        return bookRepository.findById(bookId).map(Book::getAuthors).map(Set::copyOf);
    }
}
