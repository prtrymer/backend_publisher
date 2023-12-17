package com.example.publisher.dto.author;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Data
public class AuthorUpdateDto {
    @NotBlank(message = "Specify name")
    private String name;
    @Positive
    private int age;
    private String characteristic;
    @NotNull
    private List<Long> bookIndices;
}
