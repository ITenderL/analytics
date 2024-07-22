package com.itender.analytics.board.mapper.mysql;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itender.analytics.board.domain.entity.AlarmRuleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author yuanhewei
 * @date 2024/4/26 10:57
 * @description
 */
@Mapper
@Repository
public interface AlarmRuleMapper extends BaseMapper<AlarmRuleEntity> {
}