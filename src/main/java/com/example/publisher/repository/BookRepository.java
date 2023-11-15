package com.example.publisher.repository;

import com.example.publisher.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByKeywordContainingIgnoreCase(String keyword);
}
