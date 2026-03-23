package com.example.demo.service;

import com.example.demo.dto.request.EmailTemplateRequest;
import com.example.demo.dto.response.EmailTemplateResponse;
import com.example.demo.enums.EmailType;

import java.util.List;

public interface EmailTemplateService {
    
    EmailTemplateResponse create(EmailTemplateRequest request);
    
    EmailTemplateResponse update(Long id, EmailTemplateRequest request);
    
    EmailTemplateResponse getById(Long id);
    
    EmailTemplateResponse getByType(EmailType type);
    
    List<EmailTemplateResponse> getAll();
    
    void delete(Long id);
}
