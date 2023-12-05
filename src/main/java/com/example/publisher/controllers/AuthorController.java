package com.example.publisher.controllers;

import com.example.publisher.dto.author.AuthorDto;
import com.example.publisher.dto.book.BookDto;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Author Controller")
@CrossOrigin
@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class AuthorController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    @GetMapping
    @Operation(summary = "Get all comments", responses = @ApiResponse(responseCode = "200",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = BookDto.class)))))
    public ResponseEntity<List<AuthorDto>> findAll() {
        return bookService.findAll().stream()
                .map(bookMapper::toPayload)
                .collect(Collectors.collectingAndThen(Collectors.toList(), ResponseEntity::ok));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get comment by id", responses = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BookDto.class))),
            @ApiResponse(responseCode = "404", content = @Content)
    })
    public ResponseEntity<BookDto> findById(@PathVariable Long id) {
        return ResponseEntity.of(bookService.findById(id).map(bookMapper::toPayload));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("@bookChecker.isAuthor(#id, #principal.getName())")
    @SecurityRequirement(name = "bearer_token")
    @Operation(summary = "Update comment by id", responses = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CommentDto.class))),
            @ApiResponse(responseCode = "400",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", content = @Content),
            @ApiResponse(responseCode = "404", content = @Content)
    })
    public ResponseEntity<CommentDto> update(@PathVariable Long id,
                                             @RequestBody @Valid CommentUpdateDto commentDto,
                                             Principal principal) {
        return ResponseEntity.of(commentService.findById(id)
                .map(menu -> commentMapper.partialUpdate(commentDto, menu))
                .map(commentService::update)
                .map(commentMapper::toPayload));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@commentChecker.isCommentOrRecipeAuthor(#id, #principal.getName()) "
            + "or hasRole('ROLE_ADMIN')")
    @SecurityRequirement(name = "bearer_token")
    @Operation(summary = "Delete comment by id", responses = {
            @ApiResponse(responseCode = "204", content = @Content),
            @ApiResponse(responseCode = "403", content = @Content)
    })
    public ResponseEntity<Void> deleteById(@PathVariable Long id,
                                           Principal principal) {
        commentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}