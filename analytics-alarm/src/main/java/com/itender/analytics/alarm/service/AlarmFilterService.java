package com.itender.analytics.alarm.service;

import com.google.common.collect.Lists;
import com.itender.analytics.alarm.domain.vo.AlarmFilterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import static com.itender.analytics.alarm.enums.AlarmFilterTypeEnum.MODEL;

/**
 * @author yuanhewei
 * @date 2024/4/28 17:13
 * @description
 */
@Service
public class AlarmFilterService {

    private final AlarmFilterQueryService queryService;

    @Autowired
    public AlarmFilterService(AlarmFilterQueryService queryService) {
        this.queryService = queryService;
    }
    public static final Map<String, Function<AlarmFilterVO, List<AlarmFilterVO>>> ALARM_FILTER_TYPE_MAP = new HashMap<>();

    @PostConstruct
    public void init() {
        ALARM_FILTER_TYPE_MAP.put(MODEL.getCode(), queryService::modelList);
    }

    public List<AlarmFilterVO> getAlarmFiltersByType(AlarmFilterVO alarmFilterVO) {
        Function<AlarmFilterVO, List<AlarmFilterVO>> result = ALARM_FILTER_TYPE_MAP.get(alarmFilterVO.getType());
        if (Objects.isNull(result)) {
            return Lists.newArrayList();
        }
        return result.apply(alarmFilterVO);
    }
}
