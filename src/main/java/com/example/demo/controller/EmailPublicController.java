package com.example.demo.controller;

import com.example.demo.dto.request.SendEmailRequest;
import com.example.demo.dto.response.SendEmailResponse;
import com.example.demo.enums.EmailStatus;
import com.example.demo.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notification-service/public")
@Tag(name = "Email Public API", description = "Public API để gửi email thông báo")
public class EmailPublicController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-register-success")
    @Operation(summary = "Gửi email đăng ký thành công", description = "Gửi email thông báo khi user đăng ký thành công")
    public ResponseEntity<SendEmailResponse> sendRegisterSuccessEmail(@Valid @RequestBody SendEmailRequest request) {
        try {
            emailService.sendRegisterSuccess(request.getEmail(), request.getName());
            return ResponseEntity.ok(new SendEmailResponse(EmailStatus.SUCCESS, "Email đăng ký thành công đã được gửi"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new SendEmailResponse(EmailStatus.FAIL, "Lỗi gửi email: " + e.getMessage()));
        }
    }

    @PostMapping("/send-payment-success")
    @Operation(summary = "Gửi email thanh toán thành công", description = "Gửi email thông báo khi user thanh toán thành công")
    public ResponseEntity<SendEmailResponse> sendPaymentSuccessEmail(@Valid @RequestBody SendEmailRequest request) {
        try {
            emailService.sendPaymentSuccess(request.getEmail(), request.getName());
            return ResponseEntity.ok(new SendEmailResponse(EmailStatus.SUCCESS, "Email thanh toán thành công đã được gửi"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new SendEmailResponse(EmailStatus.FAIL, "Lỗi gửi email: " + e.getMessage()));
        }
    }

    @PostMapping("/send-order-success")
    @Operation(summary = "Gửi email đặt hàng thành công", description = "Gửi email thông báo khi user đặt hàng thành công")
    public ResponseEntity<SendEmailResponse> sendOrderSuccessEmail(@Valid @RequestBody SendEmailRequest request) {
        try {
            emailService.sendOrderSuccess(request.getEmail(), request.getName());
            return ResponseEntity.ok(new SendEmailResponse(EmailStatus.SUCCESS, "Email đặt hàng thành công đã được gửi"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new SendEmailResponse(EmailStatus.FAIL, "Lỗi gửi email: " + e.getMessage()));
        }
    }
}
