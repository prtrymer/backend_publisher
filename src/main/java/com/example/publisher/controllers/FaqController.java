package com.example.publisher.controllers;

import com.example.publisher.dto.ExceptionResponse;
import com.example.publisher.dto.faq.FaqCreationDto;
import com.example.publisher.dto.faq.FaqDto;
import com.example.publisher.dto.faq.FaqUpdateDto;
import com.example.publisher.mappers.FaqMapper;
import com.example.publisher.services.FaqService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Faq Controller")
@CrossOrigin
@RestController
@RequestMapping("/faq")
@RequiredArgsConstructor
public class FaqController {
    private final FaqMapper faqMapper;
    private final FaqService faqService;
    @GetMapping
    @Operation(summary = "Get all faqs", responses = @ApiResponse(responseCode = "200",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = FaqDto.class)))))
    public ResponseEntity<List<FaqDto>> findAll(
    ) {

        List<FaqDto> faqs = faqService.findAll().stream()
                .map(faqMapper::toPayload)
                .toList();

        return ResponseEntity.ok(faqs);
    }

    @PostMapping
    @SecurityRequirement(name = "bearer_token")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Create new faq", responses = {
            @ApiResponse(responseCode = "201",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FaqDto.class))),
            @ApiResponse(responseCode = "400",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<FaqDto> create(@RequestBody @Valid FaqCreationDto faqDto
    ) {
        var created = faqService.create(faqMapper.toEntity(faqDto));
        return new ResponseEntity<>(faqMapper.toPayload(created), HttpStatus.CREATED);
    }


    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @SecurityRequirement(name = "bearer_token")
    @Operation(summary = "Update faq by id", responses = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FaqDto.class))),
            @ApiResponse(responseCode = "400",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", content = @Content),
            @ApiResponse(responseCode = "404", content = @Content)
    })
    public ResponseEntity<FaqDto> update(@RequestBody @Valid FaqUpdateDto faqDto,
                                          @PathVariable Long id) {
        return ResponseEntity.of(faqService.findById(id)
                .map(book -> faqMapper.partialUpdate(faqDto, book))
                .map(faqMapper::toPayload));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @SecurityRequirement(name = "bearer_token")
    @Operation(summary = "Delete faq by id", responses = {
            @ApiResponse(responseCode = "204", content = @Content),
            @ApiResponse(responseCode = "403", content = @Content)
    })
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        faqService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
