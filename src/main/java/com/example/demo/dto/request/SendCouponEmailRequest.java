package com.example.demo.dto.request;

import com.example.demo.dto.response.CouponResponse;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SendCouponEmailRequest {
    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    private String email;

    @NotBlank(message = "Tên không được để trống")
    private String name;

    @NotNull(message = "Thông tin coupon không được để trống")
    private CouponResponse coupon;
}