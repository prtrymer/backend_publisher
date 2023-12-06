package com.example.publisher.mappers;

import com.example.publisher.dto.author.AuthorCreationDto;
import com.example.publisher.dto.author.AuthorDto;
import com.example.publisher.dto.author.AuthorUpdateDto;
import com.example.publisher.models.Author;
import org.mapstruct.*;

@Mapper
public interface AuthorMapper {
    AuthorDto toPayload(Author author);

    Author toEntity(AuthorCreationDto authorDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Author partialUpdate(AuthorUpdateDto authorDto, @MappingTarget Author author);
}