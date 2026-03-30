package com.example.demo.controller;

import com.example.demo.dto.response.EmailLogResponse;
import com.example.demo.dto.response.EmailStatisticsResponse;
import com.example.demo.dto.response.ErrorResponse;
import com.example.demo.enums.EmailStatus;
import com.example.demo.enums.EmailType;
import com.example.demo.service.EmailLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notification-service/logs")
@Tag(name = "Email Logs API", description = "API để quản lý lịch sử gửi email")
public class EmailLogController {

    @Autowired
    private EmailLogService emailLogService;

    @GetMapping
    @Operation(summary = "Lấy danh sách tất cả email logs", description = "Lấy danh sách email logs với phân trang")
    public ResponseEntity<Page<EmailLogResponse>> getAllLogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<EmailLogResponse> logs = emailLogService.getAllLogs(pageable);
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/by-email")
    @Operation(summary = "Lấy email logs theo email", description = "Lấy danh sách email logs của một địa chỉ email cụ thể")
    public ResponseEntity<Page<EmailLogResponse>> getLogsByEmail(
            @RequestParam String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<EmailLogResponse> logs = emailLogService.getLogsByEmail(email, pageable);
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/by-type")
    @Operation(summary = "Lấy email logs theo loại", description = "Lấy danh sách email logs theo loại (REGISTER_SUCCESS, PAYMENT_SUCCESS, ORDER_SUCCESS, ADD_NEW_COUPON)")
    public ResponseEntity<?> getLogsByType(
            @RequestParam String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            // Validate type
            try {
                EmailType.valueOf(type.toUpperCase());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse("Loại email không hợp lệ. Các loại hợp lệ: REGISTER_SUCCESS, PAYMENT_SUCCESS, ORDER_SUCCESS, ADD_NEW_COUPON"));
            }
            
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
            Page<EmailLogResponse> logs = emailLogService.getLogsByType(type, pageable);
            return ResponseEntity.ok(logs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Lỗi lấy logs: " + e.getMessage()));
        }
    }

    @GetMapping("/by-status")
    @Operation(summary = "Lấy email logs theo trạng thái", description = "Lấy danh sách email logs theo trạng thái (SUCCESS, FAIL)")
    public ResponseEntity<?> getLogsByStatus(
            @RequestParam String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            // Validate status
            try {
                EmailStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse("Trạng thái không hợp lệ. Các trạng thái hợp lệ: SUCCESS, FAIL"));
            }
            
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
            Page<EmailLogResponse> logs = emailLogService.getLogsByStatus(status, pageable);
            return ResponseEntity.ok(logs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Lỗi lấy logs: " + e.getMessage()));
        }
    }

    @GetMapping("/statistics")
    @Operation(summary = "Lấy thống kê email", description = "Lấy thống kê tổng số email, thành công, thất bại và tỷ lệ thành công")
    public ResponseEntity<EmailStatisticsResponse> getStatistics() {
        EmailStatisticsResponse statistics = emailLogService.getStatistics();
        return ResponseEntity.ok(statistics);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa email log", description = "Xóa một email log theo ID")
    public ResponseEntity<String> deleteLog(@PathVariable Long id) {
        try {
            emailLogService.deleteLog(id);
            return ResponseEntity.ok("Email log đã được xóa thành công");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi xóa email log: " + e.getMessage());
        }
    }

    @DeleteMapping("/cleanup/{days}")
    @Operation(summary = "Xóa email logs cũ", description = "Xóa tất cả email logs cũ hơn số ngày được chỉ định")
    public ResponseEntity<String> deleteOldLogs(@PathVariable int days) {
        try {
            emailLogService.deleteOldLogs(days);
            return ResponseEntity.ok("Email logs cũ hơn " + days + " ngày đã được xóa");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi xóa email logs: " + e.getMessage());
        }
    }
}
