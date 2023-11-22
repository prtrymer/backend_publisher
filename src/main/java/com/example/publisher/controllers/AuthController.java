package com.example.publisher.controllers;

import com.example.publisher.dto.ExceptionResponse;
import com.example.publisher.dto.auth.Credentials;
import com.example.publisher.dto.auth.JwtToken;
import com.example.publisher.dto.user.UserCreationDto;
import com.example.publisher.dto.user.UserDto;
import com.example.publisher.mappers.JwtTokenMapper;
import com.example.publisher.mappers.UserMapper;
import com.example.publisher.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication controller")
@CrossOrigin
@RestController
@RequestMapping(path = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final JwtTokenMapper jwtTokenMapper;

    @PostMapping("/sign-up")
    @Operation(summary = "Sign up new user", responses = {
            @ApiResponse(responseCode = "201",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "400",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<UserDto> signUp(@RequestBody @Valid UserCreationDto userDto) {
        var newUser = userService.signUp(userMapper.toEntity(userDto));
        return new ResponseEntity<>(userMapper.toPayload(newUser), HttpStatus.CREATED);
    }

    @PostMapping("/sign-in")
    @Operation(summary = "Sign in user", responses = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = JwtToken.class))),
            @ApiResponse(responseCode = "400",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<JwtToken> signIn(@RequestBody @Valid Credentials credentials) {
        return ResponseEntity.of(userService
                .signIn(credentials.getUsername(), credentials.getPassword())
                .map(jwtTokenMapper::toPayload));
    }
}
