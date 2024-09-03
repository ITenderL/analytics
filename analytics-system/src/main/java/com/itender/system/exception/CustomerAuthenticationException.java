package com.itender.system.exception;

import com.itender.system.enums.BizExceptionEnum;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

import java.text.MessageFormat;

/**
 * 自定义验证异常类
 *
 * @author yuanhewei
 */
@Getter
public class CustomerAuthenticationException extends AuthenticationException {

    public CustomerAuthenticationException(String message){
        super(message);
    }
}
