package com.itender.analytics.alarm.enums;

import lombok.Getter;

/**
 * @author yuanhewei
 * @date 2024/5/16 16:37
 * @description 告警类型枚举
 */
@Getter
public enum AlarmTypeEnum {
    VOLUME("volume", "声量"),
    TAG_FEEDBACK_VOLUME("tagFeedbackVolume", "标签反馈量"),
    ;

    private final String code;

    private final String value;

    AlarmTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }
}
