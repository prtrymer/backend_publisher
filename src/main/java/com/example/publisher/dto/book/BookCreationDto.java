package com.example.publisher.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.List;

@Data
public class BookCreationDto {
    @NotBlank(message = "Specify name")
    private String name;
    @NotBlank(message = "Specify description")
    private String description;
    @Positive(message = "Amount of pages must be positive")
    private int pages;
    @PositiveOrZero(message = "Price of book must not be negative")
    private float price;
    @NotBlank(message = "Specify name")
    private String genre;
    @PositiveOrZero(message = "Must not be negative")
    private int capacity;
    @PositiveOrZero(message = "Must not be negative")
    private int capacitySold;

    private List<Long> authorIndicies;
}
