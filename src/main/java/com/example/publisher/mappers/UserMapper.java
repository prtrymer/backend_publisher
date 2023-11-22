package com.example.publisher.mappers;

import com.example.publisher.dto.user.UserCreationDto;
import com.example.publisher.dto.user.UserDto;
import com.example.publisher.dto.user.UserUpdateDto;
import com.example.publisher.models.UserEntity;
import org.mapstruct.*;


@Mapper
public interface UserMapper {
    @Mapping(target = "createdAt", expression = "java(user.getCreatedAt().getEpochSecond())")
    UserDto toPayload(UserEntity user);

    UserEntity toEntity(UserCreationDto userDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserEntity partialUpdate(UserUpdateDto userDto, @MappingTarget UserEntity user);
}
