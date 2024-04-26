package com.itender.analytics.tag.controller;

import com.github.pagehelper.PageInfo;
import com.itender.analytics.tag.domain.entity.TagGroup;
import com.itender.analytics.tag.domain.vo.ProbablyCountVO;
import com.itender.analytics.tag.domain.vo.TagExpressionVO;
import com.itender.analytics.tag.domain.vo.TagGroupVO;
import com.itender.analytics.tag.service.TagGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author yuanhewei
 * @date 2024/4/25 15:17
 * @description
 */
@Slf4j
@RestController
@RequestMapping("/tagGroup")
public class TagGroupController {

    private final TagGroupService tagGroupService;

    @Autowired
    public TagGroupController(TagGroupService tagGroupService) {
        this.tagGroupService = tagGroupService;
    }

    @GetMapping("/page")
    public PageInfo<TagGroupVO> getTagGroups(@RequestParam(value = "page", defaultValue = "0") int page,
                                             @RequestParam(value = "size", defaultValue = "10") int size) {
        return tagGroupService.queryByPage(page, size);
    }

    @PostMapping
    public void add(@RequestBody TagGroup tagGroup) {
        tagGroupService.add(tagGroup);
    }

    @PutMapping
    public void update(@RequestBody TagGroup tagGroup) {
        tagGroupService.updateById(tagGroup);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        tagGroupService.deleteById(id);
    }

    @GetMapping("/{id}")
    public TagGroup detail(@PathVariable Integer id) {
        return tagGroupService.getById(id);
    }

    @PostMapping("/probablyCount")
    public ProbablyCountVO probablyCount(TagExpressionVO tagExpressionVO) {
        return tagGroupService.probablyCount(tagExpressionVO);
    }
}
