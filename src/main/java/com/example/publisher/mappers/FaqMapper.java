package com.example.publisher.mappers;

import com.example.publisher.dto.faq.FaqCreationDto;
import com.example.publisher.dto.faq.FaqDto;
import com.example.publisher.dto.faq.FaqUpdateDto;
import com.example.publisher.models.Faq;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper
public interface FaqMapper {
    FaqDto toPayload(Faq faq);

    Faq toEntity(FaqCreationDto faqDto);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Faq partialUpdate(FaqUpdateDto faqDto, @MappingTarget Faq faq);
}
