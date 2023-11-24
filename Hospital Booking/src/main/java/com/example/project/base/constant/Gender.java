package com.example.project.base.constant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gender {
    MALE("Nam"), FEMALE("Nữ"), OTHER("Khác");
    private final String value;
}

