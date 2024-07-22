package com.itender.analytics.tag.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttributeLocation {

    /**
     * id
     */
    private Integer id;

    /**
     * 模板code
     */
    private String templateCode;

    /**
     * sql位置
     */
    private String sqlLocation;

    /**
     * 属性id
     */
    private Integer attributeId;

    /**
     * gp字段
     */
    private String gpColumnName;
}
