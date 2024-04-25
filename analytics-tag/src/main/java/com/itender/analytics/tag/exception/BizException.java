package com.itender.analytics.tag.exception;


import com.itender.analytics.tag.enums.BizExceptionEnum;

import java.text.MessageFormat;

/**
 * @author yuanhewei
 * @date 2024/4/25 17:23
 * @description
 */
public class BizException extends RuntimeException{

    /**
     * 编码
     */
    private final int code;

    /**
     * 异常信息
     */
    private final String msg;

    public BizException(BizExceptionEnum bizExceptionEnum) {
        this.code = bizExceptionEnum.getCode();
        this.msg = bizExceptionEnum.getMsg();
    }

    public BizException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BizException(Throwable cause, int code, String msg) {
        super(cause);
        this.code = code;
        this.msg = msg;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

    @Override
    public String toString() {
        return MessageFormat.format("程序运行异常！错误码：{0}，异常信息：{1}", this.code, this.msg);
    }
}
