package com.itender.wms.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itender.wms.entity.OutStore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itender.wms.vo.OutStoreQueryVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 出库单 Mapper 接口
 * </p>
 *
 * @author analytics
 * @since 2024-09-07
 */
public interface OutStoreMapper extends BaseMapper<OutStore> {

    /**
     * 分页查询出库单
     *
     * @param page
     * @param outStore
     * @return
     */
    IPage<OutStore> selectOutStoreByPage(IPage<OutStore> page, @Param("outStore") OutStoreQueryVO outStore);

    /**
     * 根据出库单id修改是否出库状态
     *
     * @param outsId
     */
    void updateIsOutById(Integer outsId);
}
