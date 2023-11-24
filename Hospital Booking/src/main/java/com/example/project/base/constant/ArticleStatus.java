package com.example.project.base.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ArticleStatus {
    DRAFT("Bản thô"), PUBLISHED("Xuất bản"), HIDDEN("Ẩn");
    private final String value;
}
