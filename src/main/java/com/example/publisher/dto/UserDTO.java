package com.example.publisher.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDTO {
    private String username;
    private String name;
    private String surname;
    private String patronymic;
    private String email;
    private String phone;
    private Date birthdate;
    private String password;
}
