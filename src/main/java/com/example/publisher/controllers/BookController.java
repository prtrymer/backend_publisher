package com.example.publisher.controllers;

import com.example.publisher.dto.ExceptionResponse;
import com.example.publisher.dto.author.AuthorDto;
import com.example.publisher.dto.book.BookCreationDto;
import com.example.publisher.dto.book.BookDto;
import com.example.publisher.dto.book.BookUpdateDto;
import com.example.publisher.mappers.AuthorMapper;
import com.example.publisher.mappers.BookMapper;
import com.example.publisher.services.BookService;
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

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Book Controller")
@CrossOrigin
@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final AuthorMapper authorMapper;
    private final BookMapper bookMapper;

    @GetMapping
    @Operation(summary = "Get all books", responses = @ApiResponse(responseCode = "200",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = BookDto.class)))))
    public ResponseEntity<List<BookDto>> findAll() {
        return bookService.findAll().stream()
                .map(bookMapper::toPayload)
                .collect(Collectors.collectingAndThen(Collectors.toList(), ResponseEntity::ok));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get book by id", responses = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BookDto.class))),
            @ApiResponse(responseCode = "404", content = @Content)
    })
    public ResponseEntity<BookDto> findById(@PathVariable Long id) {
        return ResponseEntity.of(bookService.findById(id).map(bookMapper::toPayload));
    }

    @GetMapping("/{id}/authors")
    @Operation(summary = "Get menus of recipe", responses = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = AuthorDto.class)))),
            @ApiResponse(responseCode = "404", content = @Content)
    })
    public ResponseEntity<List<AuthorDto>> getAuthors(@PathVariable Long id) {
        return ResponseEntity.of(bookService.getAuthors(id)
                .map(authors -> authors.stream()
                        .map(authorMapper::toPayload).toList()));
    }


    @PostMapping
    @SecurityRequirement(name = "bearer_token")
    @Operation(summary = "Create new book", responses = {
            @ApiResponse(responseCode = "201",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BookDto.class))),
            @ApiResponse(responseCode = "400",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<BookDto> create(@RequestBody @Valid BookCreationDto bookDto,
                                            Principal principal) {
        var created = bookService.create(bookMapper.toEntity(bookDto),
                bookDto.getAuthorIndicies(), principal.getName());
        return new ResponseEntity<>(bookMapper.toPayload(created), HttpStatus.CREATED);
    }


    @PatchMapping("/{id}")
    @PreAuthorize("@bookChecker.isAuthor(#id, #principal.getName())")
    @SecurityRequirement(name = "bearer_token")
    @Operation(summary = "Update recipe by id", responses = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BookDto.class))),
            @ApiResponse(responseCode = "400",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", content = @Content),
            @ApiResponse(responseCode = "404", content = @Content)
    })
    public ResponseEntity<BookDto> update(@RequestBody @Valid BookUpdateDto bookDto,
                                            @PathVariable Long id, Principal principal) {
        return ResponseEntity.of(bookService.findById(id)
                .map(book -> bookMapper.partialUpdate(bookDto, book))
                .map(book -> bookService.update(book, bookDto.getAuthorIndices()))
                .map(bookMapper::toPayload));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@bookChecker.isAuthor(#id, #principal.getName()) or hasRole('ROLE_ADMIN')")
    @SecurityRequirement(name = "bearer_token")
    @Operation(summary = "Delete recipe by id", responses = {
            @ApiResponse(responseCode = "204", content = @Content),
            @ApiResponse(responseCode = "403", content = @Content)
    })
    public ResponseEntity<Void> deleteById(@PathVariable Long id, Principal principal) {
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}