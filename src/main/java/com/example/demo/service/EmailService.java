package com.example.demo.service;

import com.example.demo.dto.response.CouponResponse;

public interface EmailService {

    void sendRegisterSuccess(String email, String name);

    void sendPaymentSuccess(String email, String name);

    void sendOrderSuccess(String email, String name);

    void sendAddNewCoupon(String email, String name, CouponResponse coupon);
}