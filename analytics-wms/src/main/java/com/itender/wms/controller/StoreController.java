package com.itender.wms.controller;


import com.itender.wms.entity.Result;
import com.itender.wms.entity.Store;
import com.itender.wms.service.StoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 仓库表 前端控制器
 * </p>
 *
 * @author analytics
 * @since 2024-09-05
 */
@RestController
@RequestMapping("/api/store")
public class StoreController {

    @Resource
    private StoreService storeService;

    @GetMapping("/list")
    public Result<List<Store>> list() {
        return Result.success(storeService.list());
    }
}

