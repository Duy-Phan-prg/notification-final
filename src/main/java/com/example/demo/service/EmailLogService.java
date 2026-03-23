package com.example.demo.service;

import com.example.demo.dto.response.EmailLogResponse;
import com.example.demo.dto.response.EmailStatisticsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmailLogService {
    
    Page<EmailLogResponse> getAllLogs(Pageable pageable);
    
    Page<EmailLogResponse> getLogsByEmail(String email, Pageable pageable);
    
    Page<EmailLogResponse> getLogsByType(String type, Pageable pageable);
    
    Page<EmailLogResponse> getLogsByStatus(String status, Pageable pageable);
    
    EmailStatisticsResponse getStatistics();
    
    void deleteLog(Long id);
    
    void deleteOldLogs(int days);
}
