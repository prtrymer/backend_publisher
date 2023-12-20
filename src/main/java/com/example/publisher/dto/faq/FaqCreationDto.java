package com.example.publisher.dto.faq;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FaqCreationDto {
    @NotNull
    private String title;
    @NotNull
    private String description;
}
