package com.example.publisher.mappers;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.publisher.dto.auth.JwtToken;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface JwtTokenMapper {
    @Mapping(target = "expiresAt", expression = "java(jwt.getExpiresAt().getTime() / 1000)")
    JwtToken toPayload(DecodedJWT jwt);
}