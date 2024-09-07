package com.itender.wms.controller;


import com.itender.wms.entity.Brand;
import com.itender.wms.entity.Result;
import com.itender.wms.service.BrandService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 品牌 前端控制器
 * </p>
 *
 * @author analytics
 * @since 2024-09-05
 */
@RestController
@RequestMapping("/api/brand")
public class BrandController {
    @Resource
    private BrandService brandService;

    @GetMapping("/list")
    public Result<List<Brand>> list() {
        return Result.success(brandService.list());
    }

}

