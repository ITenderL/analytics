package com.itender.wms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itender.wms.entity.OutStore;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itender.wms.vo.OutStoreQueryVO;

/**
 * <p>
 * 出库单 服务类
 * </p>
 *
 * @author analytics
 * @since 2024-09-07
 */
public interface OutStoreService extends IService<OutStore> {

    /**
     * 分页查询出库单
     *
     * @return
     */
    IPage<OutStore> selectOutStoreByPage(OutStoreQueryVO outStore);

    /**
     * 确认出库
     *
     * @param outStore
     */
    void confirm(OutStore outStore);
}
