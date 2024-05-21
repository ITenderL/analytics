package com.itender.analytics.alarm.service.impl;

import com.itender.analytics.alarm.convert.AlarmConvertMapper;
import com.itender.analytics.alarm.domain.entity.AlarmRuleEntity;
import com.itender.analytics.alarm.domain.vo.AlarmRuleVO;
import com.itender.analytics.alarm.exception.BizException;
import com.itender.analytics.alarm.mapper.mysql.AlarmRuleMapper;
import com.itender.analytics.alarm.service.AlarmRuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

import static com.itender.analytics.alarm.enums.StatusEnum.REQUEST_PARAMETERS_IS_NULL;

/**
 * @author yuanhewei
 * @date 2024/4/26 10:56
 * @description
 */
@Slf4j
@Service
public class AlarmRuleServiceImpl implements AlarmRuleService {

    @Resource
    private AlarmRuleMapper alarmRuleMapper;

    @Resource
    private AlarmConvertMapper alarmConvertMapper;

    @Override
    public void add(AlarmRuleVO alarmVO) {
        if (Objects.isNull(alarmVO)) {
            throw new BizException(REQUEST_PARAMETERS_IS_NULL);
        }
        AlarmRuleEntity alarmRuleEntity = alarmConvertMapper.alarmRuleVoToAlarmRuleEntity(alarmVO);
        alarmRuleMapper.insert(alarmRuleEntity);
    }
}
