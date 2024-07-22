package com.itender.analytics.tag.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yuanhewei
 * @date 2024/4/25 15:08
 * @description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagGroupVO {

    /**
     * 标签id
     */
    private Integer id;

    /**
     * 标签组名
     */
    private String name;
}
