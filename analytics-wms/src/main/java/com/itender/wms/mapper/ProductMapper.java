package com.itender.wms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itender.wms.entity.Product;
import com.itender.wms.vo.ProductQueryVO;
import io.lettuce.core.dynamic.annotation.Param;

/**
 * <p>
 * 商品表 Mapper 接口
 * </p>
 *
 * @author analytics
 * @since 2024-09-05
 */
public interface ProductMapper extends BaseMapper<Product> {

    /**
     * 分页查询商品信息
     *
     * @param page
     * @param product
     * @return
     */
    IPage<Product> selectProductPage(@Param("page") IPage<Product> page,
                                     @Param("product") ProductQueryVO product);

    /**
     * 查询商品总数
     *
     * @param page
     * @param product
     * @return
     */
    Integer selectProductCount(@Param("page") IPage<Product> page,
                               @Param("product") ProductQueryVO product);

    /**
     * 更新上下架状态
     *
     * @param product
     */
    void updateUpDownStateById(@Param("product") Product product);

    /**
     * 更新商品信息
     *
     * @param product
     */
    void updateProduct(Product product);

    /**
     * 根据商品编号查询
     *
     * @param num
     * @return
     */
    Product selectByNum(String num);

    /**
     * 更新库存
     *
     * @param productId
     * @param inventory
     */
    void updateProductInvent(@Param("productId") Integer productId, @Param("inventory") Integer inventory);
}
