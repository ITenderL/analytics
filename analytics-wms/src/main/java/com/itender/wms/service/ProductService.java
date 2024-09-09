package com.itender.wms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itender.wms.entity.Product;
import com.itender.wms.vo.ProductQueryVO;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author analytics
 * @since 2024-09-05
 */
public interface ProductService extends IService<Product> {

    /**
     * 分页查询商品
     *
     * @param queryVO
     */
    IPage<Product> getProductByPage(ProductQueryVO queryVO);

    /**
     * 更新上下架状态
     *
     * @param product
     */
    void updateUpDownStateById(Product product);

    /**
     * 更新商品信息
     *
     * @param product
     */
    void updateProduct(Product product);

    /**
     * 更新库存
     * 
     * @param productId
     * @param inventory
     */
    void updateProductInvent(Integer productId, Integer inventory);
}
