package com.itender.wms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itender.wms.entity.Purchase;

/**
 * <p>
 * 采购单 服务类
 * </p>
 *
 * @author analytics
 * @since 2024-09-07
 */
public interface PurchaseService extends IService<Purchase> {


    /**
     * 添加采购单
     *
     * @param purchase
     */
    void addPurchase(Purchase purchase);
}
