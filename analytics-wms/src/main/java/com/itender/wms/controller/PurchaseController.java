package com.itender.wms.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itender.wms.entity.Purchase;
import com.itender.wms.entity.Result;
import com.itender.wms.service.PurchaseService;
import com.itender.wms.vo.PurchaseQueryVO;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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

    @GetMapping("/list")
    public Result<List<Purchase>> purchaseList() {
        return Result.success(purchaseService.list());
    }

    @PostMapping("/page")
    public Result<IPage<Purchase>> listPurchaseByPage(@RequestBody PurchaseQueryVO purchase) {
        return Result.success(purchaseService.listPurchaseByPage(purchase));
    }

    @PostMapping("/add")
    public Result<String> addPurchase(@RequestBody Purchase purchase) {
        purchase.setBuyTime(new Date());
        purchaseService.addPurchase(purchase);
        return Result.success();
    }

    @Transactional(rollbackFor = Exception.class)
    @PutMapping("/update")
    public Result<String> updatePurchase(@RequestBody Purchase purchase) {
        purchaseService.updateById(purchase);
        return Result.success();
    }

    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("/delete/{buyId}")
    public Result<String> deletePurchase(@PathVariable("buyId") Integer buyId) {
        purchaseService.removeById(buyId);
        return Result.success();
    }

    /**
     * 更新采购单是否入库状态
     *
     * @param buyId
     * @return
     */
    @PutMapping("/updateIsInStatus/{buyId}")
    public Result<String> updateIsInStatus(@PathVariable("buyId") Integer buyId) {
        purchaseService.updateIsInStatus(buyId);
        return Result.success();
    }
}

