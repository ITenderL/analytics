package com.itender.wms.controller;


import com.itender.wms.entity.Place;
import com.itender.wms.entity.Result;
import com.itender.wms.service.PlaceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 产地 前端控制器
 * </p>
 *
 * @author analytics
 * @since 2024-09-05
 */
@RestController
@RequestMapping("/api/place")
public class PlaceController {

    @Resource
    private PlaceService placeService;

    @GetMapping("/list")
    public Result<List<Place>> list() {
        return Result.success(placeService.list());
    }

}

