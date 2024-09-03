package com.itender.system.enums;

import lombok.Getter;

/**
 * @author yuanhewei
 * @date 2024/4/25 17:26
 * @description
 */
@Getter
public enum BizExceptionEnum {
    TOKEN_IS_NULL(401, "token为空!"),
    TOKEN_IS_EXPIRED(401, "token已过期!"),
    TOKEN_CHECK_FAILED(401, "token验证失败!"),
    ;

    private final Integer code;

    private final String msg;

    BizExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
