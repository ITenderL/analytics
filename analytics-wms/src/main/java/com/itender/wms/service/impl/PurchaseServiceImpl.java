package com.itender.wms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itender.wms.mapper.PurchaseMapper;
import com.itender.wms.entity.Purchase;
import com.itender.wms.service.PurchaseService;
import com.itender.wms.vo.PurchaseQueryVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * <p>
 * 采购单 服务实现类
 * </p>
 *
 * @author analytics
 * @since 2024-09-07
 */
@Service
public class PurchaseServiceImpl extends ServiceImpl<PurchaseMapper, Purchase> implements PurchaseService {

    @Resource
    private PurchaseMapper purchaseMapper;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addPurchase(Purchase purchase) {
        purchaseMapper.insert(purchase);
    }

    @Override
    public IPage<Purchase> listPurchaseByPage(PurchaseQueryVO purchase) {
        long pageNo = Objects.isNull(purchase.getPageNo()) ? 1L : purchase.getPageNo();
        long pageSize = Objects.isNull(purchase.getPageSize()) ? 10L : purchase.getPageSize();
        IPage<Purchase> page = new Page<>(pageNo, pageSize);
        page = purchaseMapper.selectPurchaseByPage(page, purchase);
        return page;
    }

    @Override
    public void updateIsInStatus(Integer buyId) {
        purchaseMapper.updateStatus(buyId);
    }
}
