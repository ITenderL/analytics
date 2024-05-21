package com.itender.analytics.alarm.enums;

import lombok.Getter;

/**
 * @author yuanhewei
 * @date 2024/4/25 17:26
 * @description
 */
@Getter
public enum BizExceptionEnum {
    REQUEST_PARAMETERS_IS_NULL(5001, "请求参数为空！"),
    MAPSTRUCT_CONVERT_ERROR(5002, "mapstruct转换对象出错！"),
    ;

    private final Integer code;

    private final String msg;

    BizExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
