package com.itender.analytics.alarm.enums;

import lombok.Getter;

/**
 * @author yuanhewei
 * @date 2024/4/25 17:26
 * @description
 */
@Getter
public enum StatusEnum {
    SUCCESS(200, "请求处理成功"),
    UNAUTHORIZED(401, "用户认证失败"),
    FORBIDDEN(403, "权限不足"),
    SERVICE_ERROR(500, "服务器去旅行了，请稍后重试"),
    PARAM_INVALID(1000, "无效的参数"),
    REQUEST_PARAMETERS_IS_NULL(1001, "请求参数为空！"),
    MAPSTRUCT_CONVERT_ERROR(1002, "mapstruct转换对象出错！"),
    ELASTICSEARCH_QUERY_ERROR(1003, "Elasticsearch执行查询出错！"),
    ;

    private final Integer code;

    private final String msg;

    StatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
