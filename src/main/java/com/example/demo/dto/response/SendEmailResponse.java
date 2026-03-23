package com.example.demo.dto.response;

import com.example.demo.enums.EmailStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response từ email API")
public class SendEmailResponse {
    
    @Schema(description = "Trạng thái gửi email", example = "SUCCESS")
    private EmailStatus status;
    
    @Schema(description = "Thông báo", example = "Email đã được gửi thành công")
    private String message;
}
