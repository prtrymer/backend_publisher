package com.example.publisher.dto.author;

import com.example.publisher.models.Book;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class AuthorCreationDto {
    @NotBlank(message = "Specify name")
    private String name;
    private int age;
    private String description;
    private String imageKey;
    @NotNull
    private List<Book> books;
}
