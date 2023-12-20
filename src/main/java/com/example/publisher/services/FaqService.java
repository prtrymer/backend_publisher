package com.example.publisher.services;

import com.example.publisher.models.Faq;

import java.util.List;
import java.util.Optional;

public interface FaqService {
    Faq create(Faq faq);
    List<Faq> findAll();
    void deleteById(Long faqId);
    Optional<Faq> findById(Long faqId);
}
