package com.example.publisher.dto.user;

import com.example.publisher.models.Role;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Date;

@Data
public class UserCreationDto {
    @NotBlank(message = "Specify username")
    @Pattern(regexp = "^\\w+$", message = "You can use a-z, 0-9 and underscores")
    @Size(min = 4, max = 32, message = "Enter at least 4 and less than 32 characters")
    private String username;

    @NotBlank(message = "Specify name")
    @NotNull
    private String name;

    @NotBlank(message = "Specify email")
    @Email(message = "Enter correct email")
    private String email;

    @NotBlank(message = "Specify password")
    @Size(min = 6, max = 32, message = "Enter at least 6 and less than 32 characters")
    private String password;

    @NotNull
    private String phone;

    @NotNull
    private Date birthdate;

    private Role role;
}