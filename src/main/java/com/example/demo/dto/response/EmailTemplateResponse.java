package com.example.demo.dto.response;

import com.example.demo.enums.EmailType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Email Template Response")
public class EmailTemplateResponse {
    
    @Schema(description = "ID", example = "1")
    private Long id;
    
    @Schema(description = "Loại email", example = "REGISTER_SUCCESS")
    private EmailType type;
    
    @Schema(description = "Tiêu đề email", example = "Đăng ký thành công")
    private String subject;
    
    @Schema(description = "Tiêu đề hiển thị", example = "Chúc mừng!")
    private String title;
    
    @Schema(description = "Nội dung email")
    private String message;
    
    @Schema(description = "Thời gian tạo")
    private LocalDateTime createdAt;
    
    @Schema(description = "Thời gian cập nhật")
    private LocalDateTime updatedAt;
}
