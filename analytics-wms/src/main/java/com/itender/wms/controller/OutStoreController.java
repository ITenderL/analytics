package com.itender.wms.controller;


import com.itender.wms.entity.OutStore;
import com.itender.wms.entity.Result;
import com.itender.wms.service.OutStoreService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 出库单 前端控制器
 * </p>
 *
 * @author analytics
 * @since 2024-09-07
 */
@RestController
@RequestMapping("/api/outStore")
public class OutStoreController {

    @Resource
    private OutStoreService outStoreService;

    /**
     * 新增出库单
     *
     * @param outStore
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/add")
    public Result<String> addOutStore(@RequestBody OutStore outStore) {
        outStore.setOutPrice(outStore.getSalePrice());
        outStoreService.save(outStore);
        return Result.success();
    }
}

