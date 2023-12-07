package com.example.publisher.dto.book;

import lombok.Data;

@Data
public class BookDto {
    private Long id;
    private String name;
    private int pages;
    private String description;
    private float price;
    private String genre;
    private int capacity;
    private int capacitySold;
    private Long createdById;
}
