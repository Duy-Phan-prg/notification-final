package com.example.demo.dto.request;

import com.example.demo.enums.EmailType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request để tạo/cập nhật email template")
public class EmailTemplateRequest {
    
    @NotNull(message = "Type không được để trống")
    @Schema(description = "Loại email", example = "REGISTER_SUCCESS")
    private EmailType type;
    
    @NotBlank(message = "Subject không được để trống")
    @Schema(description = "Tiêu đề email", example = "Đăng ký thành công")
    private String subject;
    
    @NotBlank(message = "Title không được để trống")
    @Schema(description = "Tiêu đề hiển thị", example = "Chúc mừng!")
    private String title;
    
    @NotBlank(message = "Message không được để trống")
    @Schema(description = "Nội dung email", example = "Cảm ơn bạn đã đăng ký...")
    private String message;
}
