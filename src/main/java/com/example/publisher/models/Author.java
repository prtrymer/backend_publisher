package com.example.publisher.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private int age;
    @Column(name = "characteristic")
    private String characteristic;
    @ManyToMany(mappedBy = "authors", fetch = FetchType.EAGER)
    @JsonBackReference
    private Set<Book> books = new HashSet<>();
    public void addBook(Book book) {
        this.books.add(book);
        book.getAuthors().add(this);
    }
    public void rewriteBooks(List<Book> books) {
        this.books.forEach(book -> book.getAuthors().remove(this));
        this.books.clear();
        books.forEach(this::addBook);
    }
}
