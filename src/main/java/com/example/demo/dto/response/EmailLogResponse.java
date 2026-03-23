package com.example.demo.dto.response;

import com.example.demo.enums.EmailStatus;
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
@Schema(description = "Email Log Response")
public class EmailLogResponse {
    
    @Schema(description = "ID", example = "1")
    private Long id;
    
    @Schema(description = "Email", example = "user@gmail.com")
    private String email;
    
    @Schema(description = "Tên người dùng", example = "Nguyễn Văn A")
    private String name;
    
    @Schema(description = "Loại email", example = "REGISTER_SUCCESS")
    private EmailType type;
    
    @Schema(description = "Trạng thái", example = "SUCCESS")
    private EmailStatus status;
    
    @Schema(description = "Thông báo lỗi", example = "")
    private String errorMessage;
    
    @Schema(description = "Thời gian tạo", example = "2024-03-23T10:30:00")
    private LocalDateTime createdAt;
    
    @Schema(description = "Thời gian cập nhật", example = "2024-03-23T10:30:00")
    private LocalDateTime updatedAt;
}
