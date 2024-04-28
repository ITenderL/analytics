package com.itender.analytics.push.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author yuanhewei
 * @date 2024/4/25 12:41
 * @description
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlarmResultVO {

    /**
     * id
     */
    private Long  id;

    /**
     * 名称
     */
    private String  name;

    /**
     * 告警规则id
     */
    private Integer ruleId;

    /**
     * 实际触发条件
     */
    private String condition;

    /**
     * 规则设置的触发条件
     */
    private String settingCondition;

    /**
     * 监控维度
     */
    private String dimension;

    /**
     * 过滤条件
     */
    private Map<String, List<String>> filterMap;
}
