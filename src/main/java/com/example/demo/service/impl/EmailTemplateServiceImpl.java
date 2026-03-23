package com.example.demo.service.impl;

import com.example.demo.dto.request.EmailTemplateRequest;
import com.example.demo.dto.response.EmailTemplateResponse;
import com.example.demo.entity.EmailTemplate;
import com.example.demo.enums.EmailType;
import com.example.demo.repository.EmailTemplateRepository;
import com.example.demo.service.EmailTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EmailTemplateServiceImpl implements EmailTemplateService {

    @Autowired
    private EmailTemplateRepository emailTemplateRepository;

    @Override
    public EmailTemplateResponse create(EmailTemplateRequest request) {
        EmailTemplate template = EmailTemplate.builder()
                .type(request.getType())
                .subject(request.getSubject())
                .title(request.getTitle())
                .message(request.getMessage())
                .build();
        
        EmailTemplate saved = emailTemplateRepository.save(template);
        log.info("Created email template for type: " + request.getType());
        return convertToDTO(saved);
    }

    @Override
    public EmailTemplateResponse update(Long id, EmailTemplateRequest request) {
        EmailTemplate template = emailTemplateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Template not found"));
        
        template.setSubject(request.getSubject());
        template.setTitle(request.getTitle());
        template.setMessage(request.getMessage());
        
        EmailTemplate updated = emailTemplateRepository.save(template);
        log.info("Updated email template with id: " + id);
        return convertToDTO(updated);
    }

    @Override
    public EmailTemplateResponse getById(Long id) {
        EmailTemplate template = emailTemplateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Template not found"));
        return convertToDTO(template);
    }

    @Override
    public EmailTemplateResponse getByType(EmailType type) {
        EmailTemplate template = emailTemplateRepository.findByType(type)
                .orElseThrow(() -> new RuntimeException("Template not found for type: " + type));
        return convertToDTO(template);
    }

    @Override
    public List<EmailTemplateResponse> getAll() {
        return emailTemplateRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        emailTemplateRepository.deleteById(id);
        log.info("Deleted email template with id: " + id);
    }

    private EmailTemplateResponse convertToDTO(EmailTemplate template) {
        return EmailTemplateResponse.builder()
                .id(template.getId())
                .type(template.getType())
                .subject(template.getSubject())
                .title(template.getTitle())
                .message(template.getMessage())
                .createdAt(template.getCreatedAt())
                .updatedAt(template.getUpdatedAt())
                .build();
    }
}
