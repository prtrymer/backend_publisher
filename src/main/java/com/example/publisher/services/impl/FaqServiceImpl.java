package com.example.publisher.services.impl;

import com.example.publisher.models.Faq;
import com.example.publisher.repository.FaqRepository;
import com.example.publisher.services.FaqService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FaqServiceImpl implements FaqService {
    FaqRepository faqRepository;

    @Override
    public Faq create(Faq faq) {
        return faqRepository.save(faq);
    }

    @Override
    public List<Faq> findAll() {
        return faqRepository.findAll();
    }

    @Override
    public void deleteById(Long faqId) {
    faqRepository.deleteById(faqId);
    }

    @Override
    public Optional<Faq> findById(Long faqId) {
        return faqRepository.findById(faqId);
    }
}
