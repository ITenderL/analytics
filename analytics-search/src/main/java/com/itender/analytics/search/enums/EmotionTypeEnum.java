package com.itender.analytics.search.enums;

import lombok.Getter;

/**
 * @author yuanhewei
 * @date 2024/4/25 12:20
 * @description
 */
@Getter
public enum EmotionTypeEnum {
    POSITIVE(1, "正面"),
    NEUTER(0, "中性"),
    NEGATIVE(-1, "负面"),
    ;

    private final Integer code;

    private final String value;

    EmotionTypeEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }
}
