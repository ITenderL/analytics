package com.itender.wms.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itender.wms.entity.InStore;
import com.itender.wms.entity.Result;
import com.itender.wms.service.InStoreService;
import com.itender.wms.vo.InStoreQueryVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 入库单 前端控制器
 * </p>
 *
 * @author analytics
 * @since 2024-09-07
 */
@RestController
@RequestMapping("/api/inStore")
public class InStoreController {

    @Resource
    private InStoreService inStoreService;

    @GetMapping("/list")
    public Result<List<InStore>> inStoreList() {
        return Result.success(inStoreService.list());
    }

    @PostMapping("/page")
    public Result<IPage<InStore>> selectInStoreListByPage(@RequestBody InStoreQueryVO inStore) {
        return Result.success(inStoreService.selectInStoreListByPage(inStore));
    }

    @PostMapping("/add")
    public Result<String> addInStore(@RequestBody InStore inStore) {
        inStoreService.addInStore(inStore);
        return Result.success("添加成功");
    }

    @PutMapping("/confirm")
    public Result<String> confirm(@RequestBody InStore inStore) {
        inStoreService.confirm(inStore);
        return Result.success();
    }
}

