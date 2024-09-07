package com.itender.wms.controller;


import com.itender.wms.entity.ProductType;
import com.itender.wms.entity.Result;
import com.itender.wms.service.ProductTypeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 商品分类表 前端控制器
 * </p>
 *
 * @author analytics
 * @since 2024-09-05
 */
@RestController
@RequestMapping("/api/productType")
public class ProductTypeController {

    @Resource
    private ProductTypeService productTypeService;

    @GetMapping("/list")
    public Result<List<ProductType>> productTypeList() {
        return Result.success(productTypeService.list());
    }

    @GetMapping("/tree")
    public Result<List<ProductType>> productTypeTree() {
        return Result.success(productTypeService.productTypeTree());
    }
}

