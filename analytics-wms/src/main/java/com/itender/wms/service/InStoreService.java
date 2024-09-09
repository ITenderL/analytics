package com.itender.wms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itender.wms.entity.InStore;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itender.wms.vo.InStoreQueryVO;

/**
 * <p>
 * 入库单 服务类
 * </p>
 *
 * @author analytics
 * @since 2024-09-07
 */
public interface InStoreService extends IService<InStore> {

    /**
     * 新增入库单
     *
     * @param inStore
     */
    void addInStore(InStore inStore);

    /**
     * 分页查询入库单列表
     *
     * @param inStore
     * @return
     */
    IPage<InStore> selectInStoreListByPage(InStoreQueryVO inStore);

    /**
     * 入库单确认
     *
     * @param inStore
     */
    void confirm(InStore inStore);
}
