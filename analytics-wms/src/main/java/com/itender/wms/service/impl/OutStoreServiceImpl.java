package com.itender.wms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itender.wms.entity.OutStore;
import com.itender.wms.entity.Product;
import com.itender.wms.exception.BizException;
import com.itender.wms.mapper.OutStoreMapper;
import com.itender.wms.service.OutStoreService;
import com.itender.wms.service.ProductService;
import com.itender.wms.vo.OutStoreQueryVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;

import static com.itender.wms.enums.BizExceptionEnum.PRODUCT_INSUFFICIENT_INVENTORY;

/**
 * <p>
 * 出库单 服务实现类
 * </p>
 *
 * @author analytics
 * @since 2024-09-07
 */
@Service
public class OutStoreServiceImpl extends ServiceImpl<OutStoreMapper, OutStore> implements OutStoreService {

    @Resource
    private OutStoreMapper outStoreMapper;

    @Resource
    private ProductService productService;

    @Override
    public IPage<OutStore> selectOutStoreByPage(OutStoreQueryVO outStore) {
        long pageNo = Objects.isNull(outStore.getPageNo()) ? 1L : outStore.getPageNo();
        long pageSize = Objects.isNull(outStore.getPageSize()) ? 10L : outStore.getPageSize();
        IPage<OutStore> page = new Page<>(pageNo, pageSize);
        return outStoreMapper.selectOutStoreByPage(page, outStore);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void confirm(OutStore outStore) {
        // 1.根据商品id查询商品
        Product product = productService.getById(outStore.getProductId());
        // 2.判断库存是否充足
        if (product.getProductInvent() < outStore.getOutNum()) {
            throw new BizException(PRODUCT_INSUFFICIENT_INVENTORY);
        }
        // 3.修改出库单状态
        outStoreMapper.updateIsOutById(outStore.getOutsId());
        // 4.修改商品库存
        productService.updateProductInvent(outStore.getProductId(), outStore.getOutNum());
    }
}
