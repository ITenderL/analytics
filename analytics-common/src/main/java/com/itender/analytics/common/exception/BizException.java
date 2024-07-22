package com.itender.analytics.common.exception;

import com.itender.analytics.common.enums.BizExceptionEnum;

/**
 * @author yuanhewei
 * @date 2024/4/25 17:23
 * @description
 */
public class BizException extends RuntimeException{
    /**
     * 编码
     */
    private final Integer code;

    /**
     * 异常信息
     */
    private final String msg;

    public BizException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BizException(BizExceptionEnum bizExceptionEnum) {
        this.code = bizExceptionEnum.getCode();
        this.msg = bizExceptionEnum.getMsg();
    }
}
