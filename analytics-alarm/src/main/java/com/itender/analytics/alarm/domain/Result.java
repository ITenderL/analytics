package com.itender.analytics.alarm.domain;

import com.itender.analytics.alarm.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.itender.analytics.alarm.enums.StatusEnum.SUCCESS;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 状态描述信息
     */
    private String message;

    /**
     * 返回数据定义为范型
     */
    private T data;

    /**
     * 请求成功，有返回数据
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(T data) {
        return Result.<T>builder()
                .code(SUCCESS.getCode())
                .data(data)
                .build();
    }

    /**
     * 请求成功，无参返回
     *
     * @param <T>
     * @return
     */
    public static <T> Result<T> success() {
        return Result.<T>builder()
                .code(SUCCESS.getCode())
                .build();
    }

    /**
     * 封装error的响应
     *
     * @param statusEnum error响应的状态值
     * @param <T>
     * @return
     */
    public static <T> Result<T> error(StatusEnum statusEnum) {
        return error(statusEnum, statusEnum.getMsg());
    }

    /**
     * 封装error的响应 可自定义错误信息
     *
     * @param statusEnum error响应的状态值
     * @param <T>
     * @return
     */
    public static <T> Result<T> error(StatusEnum statusEnum, String errorMsg) {
        return Result.<T>builder()
                .code(statusEnum.getCode())
                .message(errorMsg).build();
    }

    /**
     * 封装error的响应 可自定义错误信息
     *
     * @param code error响应的状态值
     * @param <T>
     * @return
     */
    public static <T> Result<T> error(Integer code, String errorMsg) {
        return Result.<T>builder()
                .code(code)
                .message(errorMsg).build();
    }

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
