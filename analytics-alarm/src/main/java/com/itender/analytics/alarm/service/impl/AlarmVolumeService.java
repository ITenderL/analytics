package com.itender.analytics.alarm.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Lists;
import com.itender.analytics.alarm.domain.vo.AlarmFilterVO;
import com.itender.analytics.alarm.domain.vo.AlarmResultVO;
import com.itender.analytics.alarm.domain.vo.AlarmRuleVO;
import com.itender.analytics.alarm.exception.BizException;
import com.itender.analytics.alarm.service.AlarmPreviewStrategy;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.itender.analytics.alarm.enums.BizExceptionEnum.REQUEST_PARAMETERS_IS_NULL;

/**
 * @author yuanhewei
 * @date 2024/5/20 15:35
 * @description 声量
 */
@Slf4j
@Service("volume")
public class AlarmVolumeService implements AlarmPreviewStrategy {
    @Override
    public List<AlarmResultVO> preview(AlarmRuleVO alarmVO) {
        // 1.参数校验
        if (CollUtil.isEmpty(alarmVO.getFilters()) || CollUtil.isEmpty(alarmVO.getTriggers())) {
            throw new BizException(REQUEST_PARAMETERS_IS_NULL);
        }
        // 2.判断告警过滤条件是否动态全选
        Map<String, List<AlarmFilterVO>> filterGroupMap = alarmVO.getFilters().stream().collect(Collectors.groupingBy(AlarmFilterVO::getType));
        // 3.构建boolQueryBuilder
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // 4.循环遍历告警触发条件，获取告警结果

        // 5.封装告警结果

        // 6.返回结果集
        return Lists.newArrayList();
    }
}
