package com.example.publisher.mappers;

import com.example.publisher.dto.author.AuthorCreationDto;
import com.example.publisher.dto.author.AuthorDto;
import com.example.publisher.models.Author;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.MappingTarget;

@Mapper
public interface AuthorMapper {@Mapping(target = "createdAt", expression = "java(author.getCreatedAt().getEpochSecond())")
AuthorDto toPayload(Author author);

    Author toEntity(AuthorCreationDto ingredientDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Author partialUpdate(AuthorUpdateDto ingredientDto, @MappingTarget Author author);
}