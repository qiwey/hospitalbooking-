package com.example.project.base.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ServiceType {
    CONSULTATION("Tư vấn"), TREATMENT("Điều trị");
    private final String value;
}
