package com.itender.analytics.push.service.impl;

import com.itender.analytics.push.domain.vo.AlarmResultVO;
import com.itender.analytics.push.domain.vo.AlarmRuleVO;
import com.itender.analytics.push.service.AlarmRuleService;
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
        return null;
    }

    @Override
    public void add(AlarmRuleVO alarmVO) {

    }
}
