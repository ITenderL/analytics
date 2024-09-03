package com.itender.system.handler;

import com.itender.system.entity.Result;
import com.itender.system.enums.StatusEnum;
import com.itender.system.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yuanhewei
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = BizException.class)
    public Result<Void> handleBizException(HttpServletRequest request, BizException bizException) {
        log.warn("request {} throw BizException \n", request, bizException);
        return Result.error(bizException.getCode(), bizException.getMessage());
    }

    /**
     * 其他异常拦截
     *
     * @param e       异常
     * @param request 请求参数
     * @return 接口响应
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(HttpServletRequest request, Exception e) {
        log.error("request {} throw unExpectException \n", request, e);
        return Result.error(StatusEnum.SERVICE_ERROR);
    }
}