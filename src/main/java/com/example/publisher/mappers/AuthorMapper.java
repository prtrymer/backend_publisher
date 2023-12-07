package com.example.publisher.mappers;

import com.example.publisher.dto.author.AuthorCreationDto;
import com.example.publisher.dto.author.AuthorDto;
import com.example.publisher.dto.author.AuthorUpdateDto;
import com.example.publisher.models.Author;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;


@Mapper
public interface AuthorMapper {
    AuthorDto toPayload(Author author);

    Author toEntity(AuthorCreationDto authorDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Author partialUpdate(AuthorUpdateDto authorDto, @MappingTarget Author author);
}