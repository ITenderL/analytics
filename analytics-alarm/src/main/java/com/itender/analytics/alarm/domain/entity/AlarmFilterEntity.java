package com.itender.analytics.alarm.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yuanhewei
 * @date 2024/4/25 11:31
 * @description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_alarm_filter")
public class AlarmFilterEntity {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 规则id
     */
    @TableField("rule_id")
    private Integer ruleId;

    /**
     * 过滤条件编码
     */
    private String type;

    /**
     * 过滤条件id
     */
    private Integer filterId;

    /**
     * 过滤条件值
     */
    private String name;

    /**
     * 过滤条件层级
     */
    private Integer level;

    /**
     * 使用场景，适用于关键词，1：must或者0：must not
     */
    private Integer scenarios;

    /**
     * 是否分别监控
     */
    @TableField("is_monitor")
    private Boolean isMonitor;

    /**
     * 是否全选
     */
    @TableField("is_selected_all")
    private Boolean isSelectedAll;
}
