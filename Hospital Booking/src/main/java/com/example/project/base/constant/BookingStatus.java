package com.example.project.base.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BookingStatus {
    PENDING("Đang chờ xác nhận"),
    CONFIRMED("Đã xác nhận"),
    REJECTED("Đã hủy"),
    DONE("Đã hoàn thành");
    private final String value;
}
