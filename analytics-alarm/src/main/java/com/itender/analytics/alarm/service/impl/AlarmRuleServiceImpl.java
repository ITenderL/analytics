package com.itender.analytics.alarm.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.itender.analytics.alarm.convert.AlarmConvertMapper;
import com.itender.analytics.alarm.domain.entity.AlarmRuleEntity;
import com.itender.analytics.alarm.domain.vo.AlarmFilterVO;
import com.itender.analytics.alarm.domain.vo.AlarmResultVO;
import com.itender.analytics.alarm.domain.vo.AlarmRuleVO;
import com.itender.analytics.alarm.exception.BizException;
import com.itender.analytics.alarm.mapper.mysql.AlarmRuleMapper;
import com.itender.analytics.alarm.service.AlarmRuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.itender.analytics.alarm.enums.BizExceptionEnum.PARAM_INVALID;
import static com.itender.analytics.alarm.enums.BizExceptionEnum.REQUEST_PARAMETERS_IS_NULL;

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
    public List<AlarmResultVO> previewAlarmResult(AlarmRuleVO alarmVO) {
        // 1.参数校验
        if (Objects.isNull(alarmVO) || CollUtil.isEmpty(alarmVO.getFilters()) || CollUtil.isEmpty(alarmVO.getTriggers())) {
            throw new BizException(PARAM_INVALID);
        }
        // 2.判断告警过滤条件是否动态全选
        Map<String, List<AlarmFilterVO>> filterGroupMap = alarmVO.getFilters().stream().collect(Collectors.groupingBy(AlarmFilterVO::getType));
        return null;
    }

    @Override
    public void add(AlarmRuleVO alarmVO) {
        if (Objects.isNull(alarmVO)) {
            throw new BizException(REQUEST_PARAMETERS_IS_NULL);
        }
        AlarmRuleEntity alarmRuleEntity = alarmConvertMapper.alarmRuleVoToAlarmRuleEntity(alarmVO);
        alarmRuleMapper.insert(alarmRuleEntity);
    }
}
