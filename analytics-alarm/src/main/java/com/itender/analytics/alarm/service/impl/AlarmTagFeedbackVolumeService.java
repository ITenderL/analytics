package com.itender.analytics.alarm.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.itender.analytics.alarm.domain.vo.AlarmResultVO;
import com.itender.analytics.alarm.domain.vo.AlarmRuleVO;
import com.itender.analytics.alarm.exception.BizException;
import com.itender.analytics.alarm.service.AlarmPreviewStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.itender.analytics.alarm.enums.BizExceptionEnum.REQUEST_PARAMETERS_IS_NULL;

/**
 * @author yuanhewei
 * @date 2024/5/20 15:35
 * @description 标签反馈量占比
 */
@Slf4j
@Service("tagFeedbackVolume")
public class AlarmTagFeedbackVolumeService implements AlarmPreviewStrategy {
    @Override
    public List<AlarmResultVO> preview(AlarmRuleVO alarmVO) {
        // 1.参数校验
        if (CollUtil.isEmpty(alarmVO.getFilters()) || CollUtil.isEmpty(alarmVO.getTriggers())) {
            throw new BizException(REQUEST_PARAMETERS_IS_NULL);
        }
        // 2.判断告警过滤条件是否动态全选

        // 3.循环遍历告警触发条件，获取告警结果

        // 4.封装告警结果

        // 5.返回结果集
        return null;
    }
}
