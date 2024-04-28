package com.itender.analytics.alarm.service;

import com.itender.analytics.alarm.domain.vo.AlarmFilterVO;
import com.itender.analytics.alarm.mapper.mysql.AlarmFilterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yuanhewei
 * @date 2024/4/28 17:13
 * @description
 */
@Service
public class AlarmFilterQueryService {

    private final AlarmFilterMapper alarmFilterMapper;

    @Autowired
    public AlarmFilterQueryService(AlarmFilterMapper alarmFilterMapper) {
        this.alarmFilterMapper = alarmFilterMapper;
    }

    public List<AlarmFilterVO> modelList(AlarmFilterVO alarmFilterVO) {
        return alarmFilterMapper.modelList(alarmFilterVO);
    }
}
