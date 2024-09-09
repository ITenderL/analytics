package com.itender.wms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itender.wms.entity.InStore;
import com.itender.wms.mapper.InStoreMapper;
import com.itender.wms.service.InStoreService;
import com.itender.wms.service.ProductService;
import com.itender.wms.service.PurchaseService;
import com.itender.wms.vo.InStoreQueryVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * <p>
 * 入库单 服务实现类
 * </p>
 *
 * @author analytics
 * @since 2024-09-07
 */
@Service
public class InStoreServiceImpl extends ServiceImpl<InStoreMapper, InStore> implements InStoreService {

    @Resource
    private InStoreMapper inStoreMapper;

    @Resource
    private PurchaseService purchaseService;

    @Resource
    private ProductService productService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addInStore(InStore inStore) {
        purchaseService.updateIsInStatus(inStore.getBuyId());
        inStoreMapper.insert(inStore);
    }

    @Override
    public IPage<InStore> selectInStoreListByPage(InStoreQueryVO inStore) {
        long pageNo = Objects.isNull(inStore.getPageNo()) ? 1L : inStore.getPageNo();
        long pageSize = Objects.isNull(inStore.getPageSize()) ? 10L : inStore.getPageSize();
        IPage<InStore> page = new Page<>(pageNo, pageSize);
        return inStoreMapper.selectInStoreListByPage(page, inStore);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void confirm(InStore inStore) {
        // 1.修改状态
        inStoreMapper.updateIsInById(inStore.getInsId());
        // 2.修改商品库存
        productService.updateProductInvent(inStore.getProductId(), inStore.getInNum());
    }
}
