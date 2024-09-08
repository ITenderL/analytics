package com.itender.wms.controller;


import com.itender.wms.entity.Purchase;
import com.itender.wms.entity.Result;
import com.itender.wms.service.PurchaseService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 采购单 前端控制器
 * </p>
 *
 * @author analytics
 * @since 2024-09-07
 */
@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {

    @Resource
    private PurchaseService purchaseService;

    @PostMapping("/add")
    public Result<String> addPurchase(@RequestBody Purchase purchase) {
        purchaseService.addPurchase(purchase);
        return Result.success();
    }
}

