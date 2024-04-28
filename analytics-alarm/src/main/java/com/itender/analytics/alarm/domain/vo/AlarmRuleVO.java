package com.itender.analytics.alarm.domain.vo;

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
public class AlarmRuleVO {

    /**
     * id of the alarm
     */
    private Long id;

    /**
     * name of the alarm
     */
    private String name;

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
    private Integer pushType;

    /**
     * 责任人
     */
    private Integer responsible;

    /**
     * 推送人
     */
    private List<Integer> pusher;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 创建人
     */
    private Integer creator;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private Integer updater;

    /**
     * 更新时间
     */
    private Date updateTime;
}
