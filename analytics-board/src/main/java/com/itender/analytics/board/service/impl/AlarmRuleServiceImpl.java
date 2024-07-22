package com.itender.analytics.board.service.impl;

import com.itender.analytics.board.domain.vo.AlarmResultVO;
import com.itender.analytics.board.domain.vo.AlarmRuleVO;
import com.itender.analytics.board.service.AlarmRuleService;
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
