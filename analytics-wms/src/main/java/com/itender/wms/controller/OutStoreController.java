package com.itender.wms.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itender.wms.entity.InStore;
import com.itender.wms.entity.OutStore;
import com.itender.wms.entity.Result;
import com.itender.wms.service.OutStoreService;
import com.itender.wms.vo.OutStoreQueryVO;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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

    @GetMapping("/list")
    public Result<List<OutStore>> selectOutStoreList() {
        return Result.success(outStoreService.list());
    }

    @PostMapping("/page")
    public Result<IPage<OutStore>> selectOutStoreByPage(@RequestBody OutStoreQueryVO outStore) {
        return Result.success(outStoreService.selectOutStoreByPage(outStore));
    }

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

    @PutMapping("/confirm")
    public Result<String> confirm(@RequestBody OutStore outStore) {
        outStoreService.confirm(outStore);
        return Result.success();
    }
}

