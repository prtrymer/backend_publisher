package com.example.publisher.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateDto {
    @Pattern(regexp = "^\\w+$", message = "You can use a-z, 0-9 and underscores")
    @Size(min = 4, max = 32, message = "Enter at least 4 and less than 32 characters")
    private String username;

    @Email(message = "Enter correct email")
    private String email;

    @Size(min = 6, max = 32, message = "Enter at least 6 and less than 32 characters")
    private String newPassword;
    private String phone;
}
