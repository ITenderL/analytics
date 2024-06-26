package com.itender.analytics.search.service;

import com.itender.analytics.search.domain.vo.AlarmResultVO;
import com.itender.analytics.search.domain.vo.AlarmRuleVO;

import java.util.List;

/**
 * @author yuanhewei
 * @date 2024/4/26 10:56
 * @description
 */
public interface AlarmRuleService {

    /**
     * 预览告警结果
     *
     * @param alarmVO
     * @return
     */
    List<AlarmResultVO> preview(AlarmRuleVO alarmVO);

    /**
     * 新增告警规则
     *
     * @param alarmVO
     */
    void add(AlarmRuleVO alarmVO);
}
