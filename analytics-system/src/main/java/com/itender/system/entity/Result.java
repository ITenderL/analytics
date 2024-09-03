package com.itender.system.entity;

import com.itender.system.constants.ResultCode;
import com.itender.system.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.itender.system.enums.StatusEnum.SUCCESS;

/**
 * @author yuanhewei
 */
@Data
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
     * 是否成功
     */
    private Boolean success;

    /**
     * 返回数据定义为范型
     */
    private T data;

    /**
     * 设置状态码
     *
     * @param code
     * @return
     */
    public Result<T> code(Integer code) {
        this.setCode(code);
        return this;
    }

    /**
     * 请求成功，有返回数据
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(SUCCESS.getCode());
        result.setSuccess(true);
        result.setMessage("操作成功！");
        result.setData(data);
        return result;
    }

    /**
     * 请求成功，有返回数据
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(Integer code, String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setSuccess(true);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    /**
     * 请求成功，无参返回
     *
     * @param <T>
     * @return
     */
    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.setCode(SUCCESS.getCode());
        result.setMessage("操作成功！");
        result.setSuccess(true);
        return result;
    }

    /**
     * 失败
     *
     * @return
     */
    public static <T> Result<T> error() {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.ERROR);
        result.setSuccess(false);
        result.setMessage("操作失败！");
        return result;
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
        Result<T> result = new Result<>();
        result.setCode(statusEnum.getCode());
        result.setMessage(errorMsg);
        result.setSuccess(false);
        result.setMessage("操作失败！");
        return result;
    }

    /**
     * 封装error的响应 可自定义错误信息
     *
     * @param code error响应的状态值
     * @param <T>
     * @return
     */
    public static <T> Result<T> error(Integer code, String errorMsg) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(errorMsg);
        result.setSuccess(false);
        return result;
    }

    /**
     * 设置返回消息
     *
     * @param message
     * @return
     */
    public Result<T> message(String message) {
        this.setMessage(message);
        return this;
    }

    /**
     * 是否存在
     * @return
     */
    public static<T> Result<T> exist(){
        Result<T> result = new Result<>();
        // 存在该数据
        result.setSuccess(false);
        // 由于vue-element-admin模板在响应时验证状态码是否是200，如果不是200，则报错,
        // 执行成功，但存在该数据
        result.setCode(SUCCESS.getCode());
        result.setMessage("该数据已存在！");
        return result;
    }

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
