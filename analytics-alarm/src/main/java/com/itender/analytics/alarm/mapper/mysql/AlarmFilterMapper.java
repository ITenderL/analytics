package com.itender.analytics.alarm.mapper.mysql;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itender.analytics.alarm.domain.vo.AlarmFilterVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yuanhewei
 * @date 2024/4/28 17:26
 * @description
 */
@Mapper
@Repository
public interface AlarmFilterMapper extends BaseMapper<AlarmFilterVO> {

    /**
     * 查询机型集合
     *
     * @param alarmFilterVO
     * @return
     */
    List<AlarmFilterVO> modelList(AlarmFilterVO alarmFilterVO);
}
