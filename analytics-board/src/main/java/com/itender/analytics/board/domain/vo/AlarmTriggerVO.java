package com.itender.analytics.board.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author yuanhewei
 * @date 2024/4/25 12:25
 * @description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlarmTriggerVO {

    /**
     * 类型
     * 标签反馈量占比，
     * 声量详情
     */
    private String type;

    /**
     * 指标类型 值，差值，比值，换比率，与平均值插值，较平均值环比，同比
     **/
    private String indicatorType;

    /**
     * 符号 > < =
     */
    private String sign;

    /**
     * 阈值
     */
    private String threshold;

    /**
     * 触发条件关系， 且关系  或关系
     */
    private String conditionRelation;

    /**
     * 时间类型
     * today: 当天
     * yesterday: 昨天
     * nearlySevenDays: 近七天
     * nearlyThirtyDays: 近三十天
     */
    private String dateType;

    /**
     * 子出发条件
     */
    private List<AlarmTriggerVO> children;
}
