package com.example.publisher.dto.user;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String phoneNumber;
    private String imageKey;
    private Long createdAt;
}
