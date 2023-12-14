package com.example.publisher.dto.author;

import lombok.Data;

import java.util.List;

@Data
public class AuthorUpdateDto {
    private String description;
    private List<Long> bookIndecies;
}
