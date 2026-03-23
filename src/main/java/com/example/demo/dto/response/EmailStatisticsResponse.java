package com.example.demo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Email Statistics Response")
public class EmailStatisticsResponse {
    
    @Schema(description = "Tổng số email", example = "100")
    private Long totalEmails;
    
    @Schema(description = "Email thành công", example = "95")
    private Long successEmails;
    
    @Schema(description = "Email thất bại", example = "5")
    private Long failedEmails;
    
    @Schema(description = "Tỷ lệ thành công (%)", example = "95.0")
    private Double successRate;
}
