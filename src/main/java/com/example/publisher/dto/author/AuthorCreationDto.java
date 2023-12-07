package com.example.publisher.dto.author;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthorCreationDto {
    @NotBlank(message = "Specify name")
    private String name;
    private int age;
    private String characteristic;
}
