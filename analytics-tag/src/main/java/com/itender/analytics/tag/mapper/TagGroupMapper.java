package com.itender.analytics.tag.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.PageInfo;
import com.itender.analytics.tag.domain.entity.TagGroup;
import com.itender.analytics.tag.domain.vo.TagGroupVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yuanhewei
 * @date 2024/4/25 15:13
 * @description
 */
@Mapper
@Repository
public interface TagGroupMapper extends BaseMapper<TagGroup> {
    /**
     * 查询集合
     *
     * @return
     */
    List<TagGroupVO> queryList();
}
