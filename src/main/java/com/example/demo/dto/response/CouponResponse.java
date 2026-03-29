package com.example.demo.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CouponResponse {
    private Long id;
    private Long promotionId;
    private String code;
    private String discountType; // Khuyên dùng String khi nhận từ service khác
    private Double discountValue;
    private Double minOrderValue;
    private Double maxDiscount;
    private Integer usageLimit;
    private Integer userLimit;
    private Integer usedCount;
    private String minTier; // Khuyên dùng String (VD: "GOLD", "SILVER")
    private Boolean isPublic;
    private LocalDateTime createdAt;
    private LocalDateTime startAt;
    private LocalDateTime expiredAt;
}