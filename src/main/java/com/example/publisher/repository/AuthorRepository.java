package com.example.publisher.repository;

import com.example.publisher.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long>{
    List<Author> findAllByNameContainingIgnoreCase(String keyword);

    List<Author> findAuthorsByIdIs(List<Long> authorId);
}
