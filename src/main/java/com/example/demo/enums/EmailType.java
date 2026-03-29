package com.example.demo.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Loại email", example = "REGISTER_SUCCESS")
public enum EmailType {
    REGISTER_SUCCESS("Đăng ký thành công"),
    PAYMENT_SUCCESS("Thanh toán thành công"),
    ORDER_SUCCESS("Đặt hàng thành công"),
    ADD_NEW_COUPON("Thông báo mã giảm giá mới");
    private final String description;

    EmailType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
