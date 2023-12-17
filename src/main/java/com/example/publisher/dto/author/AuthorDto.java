package com.example.publisher.dto.author;

import lombok.Data;

import java.util.List;

@Data
public class AuthorDto {
    private Long id;
    private String name;
    private int age;
    private String characteristic;
    private List<Long> bookIndices;
}
