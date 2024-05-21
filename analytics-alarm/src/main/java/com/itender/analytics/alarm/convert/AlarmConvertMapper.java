package com.itender.analytics.alarm.convert;


import com.itender.analytics.alarm.domain.entity.AlarmRuleEntity;
import com.itender.analytics.alarm.domain.vo.AlarmRuleVO;
import org.mapstruct.Mapper;

/**
 * @author itender
 * @date 2022/9/22 17:33
 * @desc
 */
@Mapper(componentModel = "spring", uses = DateConvertMapper.class)
public interface AlarmConvertMapper {

    /**
     * vo转换成entity
     *
     * @param alarmRuleEntity
     * @return
     */
    AlarmRuleVO alarmRuleEntityToAlarmRuleVO(AlarmRuleEntity alarmRuleEntity);

    /**
     * entity -> vo
     *
     * @param alarmRuleVO
     * @return
     */
    AlarmRuleEntity alarmRuleVoToAlarmRuleEntity(AlarmRuleVO alarmRuleVO);
}
