package com.example.demo.service.impl;

import com.example.demo.dto.response.EmailLogResponse;
import com.example.demo.dto.response.EmailStatisticsResponse;
import com.example.demo.entity.EmailLog;
import com.example.demo.enums.EmailStatus;
import com.example.demo.enums.EmailType;
import com.example.demo.repository.EmailLogRepository;
import com.example.demo.service.EmailLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class EmailLogServiceImpl implements EmailLogService {

    @Autowired
    private EmailLogRepository emailLogRepository;

    @Override
    public Page<EmailLogResponse> getAllLogs(Pageable pageable) {
        return emailLogRepository.findAll(pageable)
                .map(this::convertToDTO);
    }

    @Override
    public Page<EmailLogResponse> getLogsByEmail(String email, Pageable pageable) {
        return emailLogRepository.findByEmail(email, pageable)
                .map(this::convertToDTO);
    }

    @Override
    public Page<EmailLogResponse> getLogsByType(String type, Pageable pageable) {
        try {
            EmailType emailType = EmailType.valueOf(type.toUpperCase());
            return emailLogRepository.findByType(emailType, pageable)
                    .map(this::convertToDTO);
        } catch (IllegalArgumentException e) {
            log.error("Invalid email type: " + type);
            return Page.empty(pageable);
        }
    }

    @Override
    public Page<EmailLogResponse> getLogsByStatus(String status, Pageable pageable) {
        try {
            EmailStatus emailStatus = EmailStatus.valueOf(status.toUpperCase());
            return emailLogRepository.findByStatus(emailStatus, pageable)
                    .map(this::convertToDTO);
        } catch (IllegalArgumentException e) {
            log.error("Invalid email status: " + status);
            return Page.empty(pageable);
        }
    }

    @Override
    public EmailStatisticsResponse getStatistics() {
        Long totalEmails = emailLogRepository.countTotalEmails();
        Long successEmails = emailLogRepository.countSuccessEmails();
        Long failedEmails = emailLogRepository.countFailedEmails();
        
        Double successRate = totalEmails > 0 ? (successEmails * 100.0) / totalEmails : 0.0;
        
        return EmailStatisticsResponse.builder()
                .totalEmails(totalEmails)
                .successEmails(successEmails)
                .failedEmails(failedEmails)
                .successRate(successRate)
                .build();
    }

    @Override
    public void deleteLog(Long id) {
        emailLogRepository.deleteById(id);
        log.info("Deleted email log with id: " + id);
    }

    @Override
    public void deleteOldLogs(int days) {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(days);
        // Lấy tất cả logs cũ hơn cutoffDate và xóa
        emailLogRepository.findAll().stream()
                .filter(log -> log.getCreatedAt().isBefore(cutoffDate))
                .forEach(log -> emailLogRepository.delete(log));
        log.info("Deleted email logs older than " + days + " days");
    }

    private EmailLogResponse convertToDTO(EmailLog emailLog) {
        return EmailLogResponse.builder()
                .id(emailLog.getId())
                .email(emailLog.getEmail())
                .name(emailLog.getName())
                .type(emailLog.getType())
                .status(emailLog.getStatus())
                .errorMessage(emailLog.getErrorMessage())
                .createdAt(emailLog.getCreatedAt())
                .updatedAt(emailLog.getUpdatedAt())
                .build();
    }
}
