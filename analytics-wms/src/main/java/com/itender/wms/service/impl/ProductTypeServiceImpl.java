package com.itender.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itender.wms.entity.ProductType;
import com.itender.wms.mapper.ProductTypeMapper;
import com.itender.wms.service.ProductTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itender.wms.utils.TreeUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 商品分类表 服务实现类
 * </p>
 *
 * @author analytics
 * @since 2024-09-05
 */
@Service
public class ProductTypeServiceImpl extends ServiceImpl<ProductTypeMapper, ProductType> implements ProductTypeService {

    @Resource
    private ProductTypeMapper productTypeMapper;
    @Override
    public List<ProductType> productTypeTree() {
        List<ProductType> typeList = productTypeMapper.selectList(new QueryWrapper<>());
        return TreeUtils.buildProductTypeTree(typeList, 0);
    }
}
