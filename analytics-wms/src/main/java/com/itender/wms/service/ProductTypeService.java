package com.itender.wms.service;

import com.itender.wms.entity.ProductType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品分类表 服务类
 * </p>
 *
 * @author analytics
 * @since 2024-09-05
 */
public interface ProductTypeService extends IService<ProductType> {

    /**
     * 查询商品分类树
     *
     * @return
     */
    List<ProductType> productTypeTree();

    /**
     * 检查编码是否存在
     *
     * @param typeCode
     * @return
     */
    Boolean checkTypeCode(String typeCode);
}
