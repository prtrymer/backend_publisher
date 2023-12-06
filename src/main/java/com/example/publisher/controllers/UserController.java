package com.example.publisher.controllers;

import com.example.publisher.dto.ExceptionResponse;
import com.example.publisher.dto.user.UserDto;
import com.example.publisher.dto.user.UserUpdateDto;
import com.example.publisher.mappers.UserMapper;
import com.example.publisher.services.UserService;
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

@Tag(name = "User Controller")
@CrossOrigin
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    @Operation(summary = "Get all users", responses = @ApiResponse(responseCode = "200",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = UserDto.class)))))
    public ResponseEntity<List<UserDto>> findAll() {
        return userService.findAll().stream()
                .map(userMapper::toPayload)
                .collect(Collectors.collectingAndThen(Collectors.toList(), ResponseEntity::ok));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by id", responses = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "404", content = @Content)
    })
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        return ResponseEntity.of(userService.findById(id).map(userMapper::toPayload));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("@userChecker.check(#id, #principal.getName())")
    @SecurityRequirement(name = "bearer_token")
    @Operation(summary = "Update user by id", responses = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "400",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", content = @Content),
            @ApiResponse(responseCode = "404", content = @Content)
    })
    public ResponseEntity<UserDto> update(@RequestBody @Valid UserUpdateDto userDto,
                                          @PathVariable Long id, Principal principal) {
        return ResponseEntity.of(userService.findById(id)
                .map(user -> userMapper.partialUpdate(userDto, user))
                .map(user -> userService.update(user, userDto.getNewPassword()))
                .map(userMapper::toPayload));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@userChecker.check(#id, #principal.getName()) or hasRole('ROLE_ADMIN')")
    @SecurityRequirement(name = "bearer_token")
    @Operation(summary = "Delete user by id", responses = {
            @ApiResponse(responseCode = "204", content = @Content),
            @ApiResponse(responseCode = "403", content = @Content)
    })
    public ResponseEntity<Void> deleteById(@PathVariable Long id, Principal principal) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}