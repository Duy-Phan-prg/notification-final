package com.example.demo.repository;

import com.example.demo.entity.EmailTemplate;
import com.example.demo.enums.EmailType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, Long> {
    Optional<EmailTemplate> findByType(EmailType type);
}
