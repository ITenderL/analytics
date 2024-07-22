package com.itender.analytics.tag.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author yuanhewei
 * @date 2024/4/25 17:04
 * @description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagExpressionVO {

    /**
     * 全局标签类型：0或者null：oneId标签 1：会员标签  2：粉丝标签
     * 历史的可能存在null的，也归为原有的标签
     */
    private Integer globalTagType;

    /**
     * 表达式集合
     */
    private List<TagGroupExpressionVO> expression;

    /**
     * 数据范围
     */
    private String dataRange;

    /**
     * 标签组类型，自定义类型（0：无，1：自定义标签值，2：事件偏好属性）
     */
    private Integer customType;
}