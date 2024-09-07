package com.itender.wms.enums;

import lombok.Getter;

/**
 * @author yuanhewei
 * @date 2024/4/25 17:26
 * @description
 */
@Getter
public enum StatusEnum {
    SUCCESS(200, "请求成功"),
    ERROR(-1, "请求失败"),
    UNAUTHORIZED(401, "用户认证失败"),
    FORBIDDEN(403, "权限不足"),
    SERVICE_ERROR(500, "服务器去旅行了，请稍后重试"),
    ;

    private final Integer code;

    private final String msg;

    StatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
