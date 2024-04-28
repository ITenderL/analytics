package com.itender.analytics.board.controller;

import com.itender.analytics.board.domain.vo.AlarmResultVO;
import com.itender.analytics.board.domain.vo.AlarmRuleVO;
import com.itender.analytics.board.service.AlarmRuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yuanhewei
 * @date 2024/4/25 12:34
 * @description
 */
@Slf4j
@RestController
@RequestMapping("/alarm/rule")
public class AlarmRuleController {

    private final AlarmRuleService alarmRuleService;

    @Autowired
    public AlarmRuleController(AlarmRuleService alarmRuleService) {
        this.alarmRuleService = alarmRuleService;
    }

    /**
     * 新增告警规则
     *
     * @param alarmVO
     */
    @PostMapping
    public void add(@RequestBody AlarmRuleVO alarmVO) {
        alarmRuleService.add(alarmVO);
    }

    /**
     * 查看详情
     *
     * @param id
     * @return
     */
    @GetMapping("/detail/{id}")
    public AlarmRuleVO detail(@PathVariable("id") Long id) {
        return null;
    }

    /**
     * 预览告警结果
     *
     * @param alarmVO
     */
    @PostMapping("/preview")
    public List<AlarmResultVO> preview(@RequestBody AlarmRuleVO alarmVO) {
        return alarmRuleService.preview(alarmVO);
    }
}
