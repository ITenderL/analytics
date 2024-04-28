package com.itender.analytics.search.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_alarm_result")
public class AlarmResultEntity {

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
     * 监控维度
     */
    private String dimension;

    /**
     * 触发条件
     */
    private String condition;

    /**
     * 过滤条件
     */
    private Map<String, List<String>> filterMap;


}
