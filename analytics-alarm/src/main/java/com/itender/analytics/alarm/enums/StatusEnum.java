package com.itender.analytics.alarm.enums;

import lombok.Getter;

/**
 * @author yuanhewei
 * @date 2024/4/25 17:26
 * @description
 */
@Getter
public enum StatusEnum {
    SUCCESS(2000, "请求成功"),
    UNAUTHORIZED(4001, "用户认证失败"),
    FORBIDDEN(4003, "权限不足"),
    SERVICE_ERROR(5000, "服务器去旅行了，请稍后重试"),
    ;

    private final Integer code;

    private final String msg;

    StatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
