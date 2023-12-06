package com.example.publisher.dto.user;

import lombok.Data;

import java.util.Date;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String name;
    private String email;
    private String phone;
    private Date birthdate;
    private Long createdAt;
}
