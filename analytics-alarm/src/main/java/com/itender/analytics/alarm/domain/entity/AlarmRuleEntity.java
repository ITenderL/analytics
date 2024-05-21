package com.itender.analytics.alarm.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.itender.analytics.alarm.domain.vo.AlarmFilterVO;
import com.itender.analytics.alarm.domain.vo.AlarmTriggerVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author yuanhewei
 * @date 2024/4/25 11:31
 * @description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_alarm_rule")
public class AlarmRuleEntity {

    /**
     * id
     */
    private Long id;

    /**
     * 告警规则名称
     */
    private String name;

    /**
     * 告警类型 volume: 声量  tagFeedbackVolume
     */
    private String type;

    /**
     * 监控维度
     */
    private Integer dimension;

    /**
     * 过滤条件
     */
    private List<AlarmFilterVO> filters;

    /**
     * 告警触发条件
     */
    private List<AlarmTriggerVO> triggers;

    /**
     * 告警级别
     */
    private Integer level;

    /**
     * 推送方式
     */
    @TableField("push_type")
    private Integer pushType;

    /**
     * 责任人
     */
    private Integer responsible;

    /**
     * 告警接收人
     */
    private List<Integer> receivers;

    /**
     * 告警周期：cron表达式
     */
    private String cron;

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
