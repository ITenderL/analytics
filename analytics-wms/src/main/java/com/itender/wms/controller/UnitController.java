package com.itender.wms.controller;


import com.itender.wms.entity.Result;
import com.itender.wms.entity.Unit;
import com.itender.wms.service.UnitService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 规格单位表 前端控制器
 * </p>
 *
 * @author analytics
 * @since 2024-09-05
 */
@RestController
@RequestMapping("/api/unit")
public class UnitController {
    @Resource
    private UnitService unitService;

    @GetMapping("/list")
    public Result<List<Unit>> list() {
        return Result.success(unitService.list());
    }
}

