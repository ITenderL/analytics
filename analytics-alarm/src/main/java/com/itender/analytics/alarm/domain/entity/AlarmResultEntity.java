package com.itender.analytics.alarm.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
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
    @TableField("rule_id")
    private Integer ruleId;

    /**
     * 监控维度
     */
    private String dimension;

    /**
     * 告警条件
     */
    private String condition;

    /**
     * 触发条件
     */
    @TableField("trigger_condition")
    private String triggerCondition;

    /**
     * 过滤条件
     */
    private Map<String, List<String>> filterMap;

    /**
     * 触发时间
     */
    @TableField("trigger_time")
    private String triggerTime;

    /**
     * 状态 0: 未处理  1：处理中  2：已完成
     */
    private Integer status;

    /**
     * 原因
     */
    private String reason;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建人
     */
    private Integer creator;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 更新人
     */
    private Integer updater;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 是否删除1：已删除 0：未删除
     */
    private Boolean deleted;
}
