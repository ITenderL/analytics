package com.itender.wms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itender.wms.entity.Purchase;
import com.itender.wms.vo.PurchaseQueryVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 采购单 Mapper 接口
 * </p>
 *
 * @author analytics
 * @since 2024-09-07
 */
public interface PurchaseMapper extends BaseMapper<Purchase> {

    /**
     * 分页查询采购单
     *
     * @param page
     * @param purchase
     * @return
     */
    IPage<Purchase> selectPurchaseByPage(IPage<Purchase> page, @Param("purchase") PurchaseQueryVO purchase);

    /**
     * 更新采购单是否入库状态
     *
     * @param buyId
     */
    void updateStatus(Integer buyId);
}
