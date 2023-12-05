package com.example.publisher.dto.book;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.List;

@Data
public class BookUpdateDto {
    private String name;
    private String description;
    @Positive(message = "Must be positive")
    private int pages;
    @PositiveOrZero(message = "Must not be negative")
    private float price;
    @PositiveOrZero(message = "Must not be negative")
    private int capacity;
    @PositiveOrZero(message = "Must not be negative")
    private int capacitySold;

    private List<Long> authorIndices;
}
