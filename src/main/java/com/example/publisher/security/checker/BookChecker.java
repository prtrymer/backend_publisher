package com.example.publisher.security.checker;

import com.example.publisher.exception.BookNotFoundException;
import com.example.publisher.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookChecker {
    BookRepository bookRepository;

    public boolean isAuthor(Long id, String username) {
        if (id == null || username == null) {
            return false;
        }
        var book = bookRepository.findById(id).orElseThrow(
                () -> new BookNotFoundException("Book with id %s not found".formatted(id))
        );
        return book.getCreatedBy().getUsername().equals(username);
    }
}
