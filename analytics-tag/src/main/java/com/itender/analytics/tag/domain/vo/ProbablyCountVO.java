package com.itender.analytics.tag.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yuanhewei
 * @date 2024/4/25 17:04
 * @description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProbablyCountVO {

    /**
     * 预估人数
     */
    private Integer count;

    /**
     * 预估sql
     */
    private String sql;
}
