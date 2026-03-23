package com.example.demo.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Trạng thái email", example = "SUCCESS")
public enum EmailStatus {
    SUCCESS("Thành công"),
    FAIL("Thất bại");

    private final String description;

    EmailStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
