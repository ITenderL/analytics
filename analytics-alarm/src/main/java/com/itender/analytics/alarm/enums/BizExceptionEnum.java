package com.itender.analytics.alarm.enums;

import lombok.Getter;

/**
 * @author yuanhewei
 * @date 2024/4/25 17:26
 * @description
 */
@Getter
public enum BizExceptionEnum {
    PARAM_INVALID(1000, "无效的参数"),
    REQUEST_PARAMETERS_IS_NULL(1001, "请求参数为空！"),
    MAPSTRUCT_CONVERT_ERROR(1002, "mapstruct转换对象出错！"),
    ELASTICSEARCH_EXECUTE_QUERY_ERROR(1003, "Elasticsearch执行查询出错！"),
    ;

    private final Integer code;

    private final String msg;

    BizExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
