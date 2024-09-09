package com.itender.wms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itender.wms.entity.InStore;
import com.itender.wms.vo.InStoreQueryVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 入库单 Mapper 接口
 * </p>
 *
 * @author analytics
 * @since 2024-09-07
 */
public interface InStoreMapper extends BaseMapper<InStore> {

    /**
     * 分页查询入库单列表
     *
     * @param page
     * @param inStore
     * @return
     */
    IPage<InStore> selectInStoreListByPage(IPage<InStore> page, @Param("inStore") InStoreQueryVO inStore);

    /**
     * 根据id修改是否入库状态
     *
     * @param insId
     */
    void updateIsInById(Integer insId);
}
