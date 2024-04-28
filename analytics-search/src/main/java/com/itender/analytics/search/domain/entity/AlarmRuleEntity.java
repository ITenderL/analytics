package com.itender.analytics.search.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.itender.analytics.search.domain.vo.AlarmFilterVO;
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
     * 监控维度
     */
    private Integer dimension;

    /**
     * 过滤条件
     */
    private List<AlarmFilterVO> filters;

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
