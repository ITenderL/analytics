package com.itender.analytics.tag.service;

import com.github.pagehelper.PageInfo;
import com.itender.analytics.tag.domain.entity.TagGroup;
import com.itender.analytics.tag.domain.vo.ProbablyCountVO;
import com.itender.analytics.tag.domain.vo.TagExpressionVO;
import com.itender.analytics.tag.domain.vo.TagGroupVO;

/**
 * @author yuanhewei
 * @date 2024/4/25 15:15
 * @description
 */
public interface TagGroupService {

    /**
     * 新增标签组
     *
     * @param tagGroup
     */
    void add(TagGroup tagGroup);

    /**
     * 更新标签组
     *
     * @param tagGroup
     */
    void updateById(TagGroup tagGroup);

    /**
     * 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    PageInfo<TagGroupVO> queryByPage(int page, int size);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    void deleteById(Integer id);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    TagGroup getById(Integer id);

    /**
     * 获取预估人数
     *
     * @param tagExpressionVO
     * @return
     */
    ProbablyCountVO probablyCount(TagExpressionVO tagExpressionVO);
}
