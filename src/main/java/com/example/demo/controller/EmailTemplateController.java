package com.example.demo.controller;

import com.example.demo.dto.request.EmailTemplateRequest;
import com.example.demo.dto.response.EmailTemplateResponse;
import com.example.demo.enums.EmailType;
import com.example.demo.service.EmailTemplateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PermitAll;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notification-service/templates")
@Tag(name = "Email Template API", description = "API để quản lý email templates")
public class EmailTemplateController {

    @Autowired
    private EmailTemplateService emailTemplateService;

    @PostMapping
    @PermitAll
    @Operation(summary = "Tạo email template", description = "Tạo template email mới")
    public ResponseEntity<EmailTemplateResponse> create(@Valid @RequestBody EmailTemplateRequest request) {
        try {
            EmailTemplateResponse response = emailTemplateService.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    @PermitAll
    @Operation(summary = "Cập nhật email template", description = "Cập nhật nội dung email template")
    public ResponseEntity<EmailTemplateResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody EmailTemplateRequest request) {
        try {
            EmailTemplateResponse response = emailTemplateService.update(id, request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}")
    @PermitAll
    @Operation(summary = "Lấy email template theo ID", description = "Lấy chi tiết email template")
    public ResponseEntity<EmailTemplateResponse> getById(@PathVariable Long id) {
        try {
            EmailTemplateResponse response = emailTemplateService.getById(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/type/{type}")
    @PermitAll
    @Operation(summary = "Lấy email template theo loại", description = "Lấy template theo EmailType")
    public ResponseEntity<EmailTemplateResponse> getByType(@PathVariable EmailType type) {
        try {
            EmailTemplateResponse response = emailTemplateService.getByType(type);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    @PermitAll
    @Operation(summary = "Lấy tất cả email templates", description = "Lấy danh sách tất cả templates")
    public ResponseEntity<List<EmailTemplateResponse>> getAll() {
        List<EmailTemplateResponse> responses = emailTemplateService.getAll();
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    @PermitAll
    @Operation(summary = "Xóa email template", description = "Xóa template theo ID")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            emailTemplateService.delete(id);
            return ResponseEntity.ok("Template đã được xóa");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
