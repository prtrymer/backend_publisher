package com.example.publisher.controllers;

import com.example.publisher.dto.ExceptionResponse;
import com.example.publisher.dto.author.AuthorCreationDto;
import com.example.publisher.dto.author.AuthorDto;
import com.example.publisher.dto.author.AuthorUpdateDto;
import com.example.publisher.dto.book.BookDto;
import com.example.publisher.mappers.AuthorMapper;
import com.example.publisher.mappers.BookMapper;
import com.example.publisher.services.AuthorService;
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
import java.util.stream.Collectors;

@Tag(name = "Author Controller")
@CrossOrigin
@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;
    private final AuthorMapper authorMapper;
    private final BookMapper bookMapper;

    @GetMapping
    @Operation(summary = "Get all authors", responses = @ApiResponse(responseCode = "200",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = AuthorDto.class)))))
    public ResponseEntity<List<AuthorDto>> findAll() {
        return authorService.findAll().stream()
                .map(authorMapper::toPayload)
                .collect(Collectors.collectingAndThen(Collectors.toList(), ResponseEntity::ok));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get ingredient by id", responses = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AuthorDto.class))),
            @ApiResponse(responseCode = "404", content = @Content)
    })
    public ResponseEntity<AuthorDto> findById(@PathVariable Long id) {
        return ResponseEntity.of(authorService.findById(id).map(authorMapper::toPayload));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @SecurityRequirement(name = "bearer_token")
    @Operation(summary = "Create new author", responses = {
            @ApiResponse(responseCode = "201",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AuthorDto.class))),
            @ApiResponse(responseCode = "400",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", content = @Content)
    })
    public ResponseEntity<AuthorDto> create(@RequestBody @Valid
                                            AuthorCreationDto authorDto) {
        var created = authorService.create(authorMapper.toEntity(authorDto));
        return new ResponseEntity<>(authorMapper.toPayload(created), HttpStatus.CREATED);
    }
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @SecurityRequirement(name = "bearer_token")
    @Operation(summary = "Update author by id", responses = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AuthorDto.class))),
            @ApiResponse(responseCode = "400",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", content = @Content),
            @ApiResponse(responseCode = "404", content = @Content)
    })
    public ResponseEntity<AuthorDto> update(@RequestBody @Valid AuthorUpdateDto authorDto,
                                                @PathVariable Long id) {
        return ResponseEntity.of(authorService.findById(id)
                .map(author -> authorMapper.partialUpdate(authorDto, author))
                .map(author -> authorService.update(author, authorDto.getBookIndices()))
                .map(authorMapper::toPayload));
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @SecurityRequirement(name = "bearer_token")
    @Operation(summary = "Delete author by id", responses = {
            @ApiResponse(responseCode = "204", content = @Content),
            @ApiResponse(responseCode = "403", content = @Content)
    })
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        authorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}/books")
    @Operation(summary = "Get books of authors", responses = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = AuthorDto.class)))),
            @ApiResponse(responseCode = "404", content = @Content)
    })
    public ResponseEntity<List<BookDto>> getBooks(@PathVariable Long id) {
        return ResponseEntity.of(authorService.getBooks(id)
                .map(authors -> authors.stream()
                        .map(bookMapper::toPayload).toList()));
    }
}