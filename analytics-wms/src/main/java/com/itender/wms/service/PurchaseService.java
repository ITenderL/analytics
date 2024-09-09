package com.itender.wms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itender.wms.entity.Purchase;
import com.itender.wms.vo.PurchaseQueryVO;

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

    /**
     * 分页查询采购单
     *
     * @param purchase
     * @return
     */
    IPage<Purchase> listPurchaseByPage(PurchaseQueryVO purchase);

    /**
     * 修改采购单是否入库状态
     *
     * @param buyId
     */
    void updateIsInStatus(Integer buyId);
}
