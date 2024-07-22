package com.itender.analytics.board.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author yuanhewei
 * @date 2024/4/25 11:31
 * @description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlarmRuleVO {

    /**
     * id of the alarm
     */
    private Long id;

    /**
     * name of the alarm
     */
    private String name;

    /**
     * 过滤条件
     */
    private List<AlarmFilterVO> filters;
}
