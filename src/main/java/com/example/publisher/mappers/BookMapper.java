package com.example.publisher.mappers;

import com.example.publisher.dto.book.BookCreationDto;
import com.example.publisher.dto.book.BookDto;
import com.example.publisher.dto.book.BookUpdateDto;
import com.example.publisher.models.Book;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper
public interface BookMapper {
    @Mapping(target = "createdById", source = "createdBy.id")
    BookDto toPayload(Book book);

    Book toEntity(BookCreationDto bookDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Book partialUpdate(BookUpdateDto bookDto, @MappingTarget Book book);
}
