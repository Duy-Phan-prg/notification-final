package com.example.demo.repository;

import com.example.demo.entity.EmailLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmailLogRepository extends JpaRepository<EmailLog, Long> {
    
    Page<EmailLog> findByEmail(String email, Pageable pageable);
    
    Page<EmailLog> findByType(String type, Pageable pageable);
    
    Page<EmailLog> findByStatus(String status, Pageable pageable);
    
    @Query("SELECT COUNT(e) FROM EmailLog e WHERE e.status = 'SUCCESS'")
    Long countSuccessEmails();
    
    @Query("SELECT COUNT(e) FROM EmailLog e WHERE e.status = 'FAIL'")
    Long countFailedEmails();
    
    @Query("SELECT COUNT(e) FROM EmailLog e")
    Long countTotalEmails();
}