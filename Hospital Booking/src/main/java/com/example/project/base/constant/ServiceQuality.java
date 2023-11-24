package com.example.project.base.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ServiceQuality {
    FREE("Miễn phí"), STANDARD("Tiêu chuẩn"), PREMIUM("Cao cấp");
    private final String value;
}
