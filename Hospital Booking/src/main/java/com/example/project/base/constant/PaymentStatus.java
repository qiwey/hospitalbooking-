package com.example.project.base.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentStatus {
    UNPAID("Chưa thanh toán"),
    PAID("Đã thanh toán"),
    REFUNDED("Đã hoàn tiền");
    private final String value;
}
