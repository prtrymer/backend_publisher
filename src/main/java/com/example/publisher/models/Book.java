package com.example.publisher.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="name")
    private String name;
    @Column(name="pages")
    private int pages;
    @Column(name="description")
    private String description;
    @Column(name="price")
    private float price;
    @Column(name="genre")
    private String genre;
    @Column(name="capacity")
    private int capacity;
    @Column(name = "capacity_sold")
    private int capacitySold;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JsonManagedReference
    @JoinTable(
            name = "books_authors",
            joinColumns = {@JoinColumn(name = "book_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "author_id", referencedColumnName = "id")}
    )
    private List<Author> authors = new ArrayList<>();

    public void addAuthor(Author author){
        this.authors.add(author);
    }

    public void rewriteAuthors(List<Author> authors){
        this.authors.forEach(author -> author.getBooks().remove(this));
        this.authors.clear();
        authors.forEach(this::addAuthor);
    }
    public void removeAuthor(Author author) {
        this.authors.remove(author);
    }

    public void removeAuthors(List<Author> authors) {
        authors.forEach(this::removeAuthor);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Book recipe = (Book) o;
        return getId() != null && Objects.equals(getId(), recipe.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
