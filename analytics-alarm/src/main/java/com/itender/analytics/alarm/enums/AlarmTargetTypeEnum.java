package com.itender.analytics.alarm.enums;

import lombok.Getter;

/**
 * @author yuanhewei
 * @date 2024/5/16 16:21
 * @description 告警结果值计算器类型枚举
 */
@Getter
public enum AlarmTargetTypeEnum {
    VALUE("value", "值"),
    DIFFERENCE("difference", "差值"),
    AVERAGE("average", "平均值"),
    DIFFERENCE_FROM_AVERAGE("differenceFromAverage", "与平均值差值"),
    COMPARED_TO_AVERAGE("comparedToAverage", "较平均值环比"),
    MONTH_ON_MONTH("monthOnMonth", "环比率"),
    YEAR_ON_YEAR("yearOnYear", "同比"),
    PERCENTAGE("percentage", "百分比"),
    ;

    private final String code;

    private final String value;

    AlarmTargetTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }
}
