package com.itender.wms.controller;


import com.itender.wms.entity.Result;
import com.itender.wms.entity.Supply;
import com.itender.wms.service.SupplyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 供货商 前端控制器
 * </p>
 *
 * @author analytics
 * @since 2024-09-05
 */
@RestController
@RequestMapping("/api/supply")
public class SupplyController {
    @Resource
    private SupplyService supplyService;

    @GetMapping("/list")
    public Result<List<Supply>> list() {
        return Result.success(supplyService.list());
    }
}

