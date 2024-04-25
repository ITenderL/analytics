package com.itender.analytics.tag.service.impl;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.itender.analytics.tag.domain.entity.TagGroup;
import com.itender.analytics.tag.domain.vo.ProbablyCountVO;
import com.itender.analytics.tag.domain.vo.TagExpressionVO;
import com.itender.analytics.tag.domain.vo.TagGroupVO;
import com.itender.analytics.tag.mapper.TagGroupMapper;
import com.itender.analytics.tag.service.TagGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yuanhewei
 * @date 2024/4/25 15:15
 * @description
 */
@Slf4j
@Service
public class TagGroupServiceImpl implements TagGroupService {

    private final TagGroupMapper tagGroupMapper;

    @Autowired
    public TagGroupServiceImpl(TagGroupMapper tagGroupMapper) {
        this.tagGroupMapper = tagGroupMapper;
    }

    @Override
    public void add(TagGroup tagGroupVO) {
        TagGroup tagGroup = TagGroup.builder().build();
        tagGroupMapper.insert(tagGroup);
    }

    @Override
    public void updateById(TagGroup tagGroupVO) {
        TagGroup tagGroup = TagGroup.builder().build();
        tagGroupMapper.updateById(tagGroup);
    }

    @Override
    public PageInfo<TagGroupVO> queryByPage(int page, int size) {
        PageMethod.startPage(page, size);
        List<TagGroupVO> list = tagGroupMapper.queryList();
        return new PageInfo<>(list);
    }

    @Override
    public void deleteById(Integer id) {
        tagGroupMapper.deleteById(id);
    }

    @Override
    public TagGroup getById(Integer id) {
        return tagGroupMapper.selectById(id);
    }

    /**
     * 获取预估人数
     *
     * @param tagExpressionVO
     * @return
     */
    @Override
    public ProbablyCountVO probablyCount(TagExpressionVO tagExpressionVO) {
        return null;
    }
}
