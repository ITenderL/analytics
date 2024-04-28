package com.itender.analytics.alarm.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.itender.analytics.alarm.domain.vo.AlarmResultVO;
import com.itender.analytics.alarm.domain.vo.AlarmRuleVO;
import com.itender.analytics.alarm.exception.BizException;
import com.itender.analytics.alarm.service.AlarmRuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yuanhewei
 * @date 2024/4/26 10:56
 * @description
 */
@Slf4j
@Service
public class AlarmRuleServiceImpl implements AlarmRuleService {
    @Override
    public List<AlarmResultVO> preview(AlarmRuleVO alarmVO) {
        // 1.参数校验
        if (CollUtil.isEmpty(alarmVO.getFilters()) || CollUtil.isEmpty(alarmVO.getTriggers())) {
            throw new BizException(5000, "");
        }
        // 2.判断告警过滤条件是否动态全选
        // 3.循环遍历告警触发条件，获取告警结果
        // 4.封装告警结果
        return null;
    }

    @Override
    public void add(AlarmRuleVO alarmVO) {

    }
}
