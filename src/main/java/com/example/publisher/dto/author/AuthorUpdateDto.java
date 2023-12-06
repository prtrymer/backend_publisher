package com.example.publisher.dto.author;

import com.example.publisher.models.Book;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class AuthorUpdateDto {
    private int age;
    private String description;
    @NotNull
    private List<Book> books;
}
