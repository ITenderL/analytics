package com.itender.analytics.alarm.service;

import com.itender.analytics.alarm.domain.vo.AlarmResultVO;
import com.itender.analytics.alarm.domain.vo.AlarmRuleVO;

import java.util.List;

/**
 * @author yuanhewei
 * @date 2024/5/20 15:29
 * @description 声量，标签反馈量占比策略类
 */
public interface AlarmPreviewStrategy {

    /**
     * 预览告警结果
     *
     * @param alarmVO
     * @return
     */
    List<AlarmResultVO> preview(AlarmRuleVO alarmVO);
}
