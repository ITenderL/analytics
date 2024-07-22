package com.itender.analytics.alarm.enums;

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

    public static String getValueByCode(Integer code) {
        for (EmotionTypeEnum emotionTypeEnum : EmotionTypeEnum.values()) {
            if (emotionTypeEnum.getCode().equals(code)) {
                return emotionTypeEnum.getValue();
            }
        }
        return null;
    }

    EmotionTypeEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }
}
