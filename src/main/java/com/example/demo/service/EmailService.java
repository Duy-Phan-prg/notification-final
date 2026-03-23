package com.example.demo.service;

public interface EmailService {

    void sendRegisterSuccess(String email, String name);

    void sendPaymentSuccess(String email, String name);
}