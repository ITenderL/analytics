package com.itender.analytics.tag.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author yuanhewei
 * @date 2024/4/25 17:04
 * @description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagGroupSelectVO implements Serializable {

    /**
     * 用于定义渠道相关的信息
     */
    private String dataRange;

    /**
     * 属性id
     */
    private Object attributeId;

    /**
     * 属性值
     */
    private String attributeValue;

    /**
     * 属性值
     */
    private String attributeCode;

    /**
     * gp字段名称
     */
    private String gpColumnName;

    /**
     * gp字段名称
     */
    private String gpTablePart;

    /**
     * sql位置
     */
    private String sqlLocation;

    /**
     * 关系
     */
    private String relation;

    /**
     * 是否行为对象
     */
    private Integer isEventObject;

    /**
     * 模板组成部分
     */
    private String templatePart;

    /**
     * 值
     */
    private Object value;

    /**
     * 店铺值字段（人群使用，暂不存储）
     */
    private Object storeValue;

    /**
     * 类型：tagGroup：标签
     */
    private String type;
}