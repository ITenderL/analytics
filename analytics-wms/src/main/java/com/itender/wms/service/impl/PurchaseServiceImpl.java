package com.itender.wms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itender.wms.mapper.PurchaseMapper;
import com.itender.wms.entity.Purchase;
import com.itender.wms.service.PurchaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

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
}
