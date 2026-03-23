package com.example.demo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request để gửi email")
public class SendEmailRequest {
    
    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    @Schema(description = "Địa chỉ email", example = "user@gmail.com")
    private String email;
    
    @NotBlank(message = "Tên không được để trống")
    @Schema(description = "Tên người dùng", example = "Nguyễn Văn A")
    private String name;
}
