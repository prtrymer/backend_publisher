package com.example.publisher.mappers;

import com.example.publisher.dto.author.AuthorCreationDto;
import com.example.publisher.dto.author.AuthorDto;
import com.example.publisher.models.Author;
import org.mapstruct.Mapper;


@Mapper
public interface AuthorMapper {
    AuthorDto toPayload(Author author);

    Author toEntity(AuthorCreationDto authorDto);
}