package com.itender.wms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itender.wms.entity.Product;
import com.itender.wms.exception.BizException;
import com.itender.wms.mapper.ProductMapper;
import com.itender.wms.service.ProductService;
import com.itender.wms.vo.ProductQueryVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

import static com.itender.wms.enums.BizExceptionEnum.THE_PRODUCT_NUM_PRODUCT_ALREADY_EXIST;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author analytics
 * @since 2024-09-05
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Resource
    private ProductMapper productMapper;

    @Value("${file.upload-path}")
    private String uploadFilePath;

    @Override
    public IPage<Product> getProductByPage(ProductQueryVO queryVO) {
        long pageNo = Objects.isNull(queryVO.getPageNo()) ? 1L : queryVO.getPageNo();
        long pageSize = Objects.isNull(queryVO.getPageSize()) ? 10L : queryVO.getPageSize();
        // 创建分页对象
        IPage<Product> page = new Page<>
                (pageNo, pageSize);
        page = productMapper.selectProductPage(page, queryVO);
        return page;
    }

    @Override
    public void updateUpDownStateById(Product product) {
        productMapper.updateUpDownStateById(product);
    }

    @Override
    public void updateProduct(Product product) {
        // 1.判断更新后的商品的编号是否存在，如果有该型号的商品，
        Product pro = productMapper.selectByNum(product.getProductNum());
        if (Objects.nonNull(pro) && !Objects.equals(product.getProductId(), pro.getProductId())) {
            throw new BizException(THE_PRODUCT_NUM_PRODUCT_ALREADY_EXIST);
        }
        // 2. 判断图片路径是否需要拼接，如果需要，则拼接
        if (!product.getImgs().startsWith(uploadFilePath)) {
            product.setImgs(uploadFilePath + product.getImgs());
        }
        productMapper.updateProduct(product);
    }
}
