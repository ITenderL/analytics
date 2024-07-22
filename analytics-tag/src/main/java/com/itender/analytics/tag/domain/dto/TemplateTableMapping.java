package com.itender.analytics.tag.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateTableMapping {

    /**
     * id
     */
    private Integer id;

    /**
     * 模板code
     */
    private String templateCode;

    /**
     * 位置
     */
    private String tableLocation;

    /**
     * 表组成
     */
    private String tablePart;

    /**
     * 实际的表
     */
    private String actualTable;

    /**
     * 优先级
     */
    private Integer priority;
}
