package com.itender.analytics.tag.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagExpressionRequest {

    /**
     * 全局标签类型：0或者null：oneId标签 1：会员标签  2：粉丝标签
     * 历史的可能存在null的，也归为原有的标签
     */
    private Integer globalTagType;

    private List<TagGroupExpressionRequest> expression;
    /**
     * 数据范围
     */
    private String dataRange;

    /**
     * 标签组类型，自定义类型（0：无，1：自定义标签值，2：事件偏好属性）
     */
    private Integer customType;
}
