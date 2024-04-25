package com.itender.analytics.alarm.controller;

import com.itender.analytics.alarm.vo.AlarmRuleVO;
import org.springframework.web.bind.annotation.*;

/**
 * @author yuanhewei
 * @date 2024/4/25 12:34
 * @description
 */
@RequestMapping("/alarm/rule")
public class AlarmRuleController {

    @PostMapping
    public void addRule(@RequestBody AlarmRuleVO alarm) {

    }

    @GetMapping("/detail/{id}")
    public AlarmRuleVO detail(@PathVariable("id") Long id) {
        return null;
    }
}
