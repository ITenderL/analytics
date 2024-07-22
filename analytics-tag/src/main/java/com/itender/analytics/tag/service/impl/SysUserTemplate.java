package com.itender.analytics.tag.service.impl;


import com.itender.analytics.tag.domain.TagGroupExpressionRequest;
import com.itender.analytics.tag.domain.TagGroupSelect;
import com.itender.analytics.tag.enums.SqlTemplateEnum;
import com.itender.analytics.tag.service.TemplateService;
import com.itender.analytics.tag.util.GetSqlUtil;
import com.itender.analytics.tag.util.ParseSqlUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


@Service("sysUserTemplate")
public class SysUserTemplate extends TemplateService {

    @Resource
    ParseSqlUtil parseSqlUtil;

    @Resource
    GetSqlUtil getSqlUtil;


    @Override
    public String doParseTemplate(TagGroupExpressionRequest expressionRequest) {
        List<TagGroupSelect> selects = expressionRequest.getSelects();
        // Map<String, Object> stringObjectMap = parseSqlUtil.routeSqlParse(expressionRequest, SqlTemplateEnum.SYS_USER.getCode());
        Map<String, Object> stringObjectMap = new HashMap<>();
        // 表组成添加渠道为组成部分
        List<TagGroupSelect> newSelects = new ArrayList<>();
        boolean allChannel = "AllChannel".equals(expressionRequest.getDataRange());
        // 数据方位不是全渠道，且不是走新添加的查询逻辑一级业务渠道，平台，店铺维度层级标签的时候，才会走下面的逻辑，
        // 如果是新添加的查询维度标签的逻辑tagDimensionFlag:true，不会走下面的逻辑
        if (!allChannel) {
            TagGroupSelect select = new TagGroupSelect();
            select.setGpTablePart(expressionRequest.getChannelCode());
            newSelects.add(select);
        }
        newSelects.addAll(selects);
        //特殊处理渠道
        // 判断是否包含数据范围信息
        boolean dataRangeFlag = false;
        for (TagGroupSelect tagGroupSelect : selects) {
            // if (null != tagGroupSelect
            //         && (Objects.equals(tagGroupSelect.getAttributeCode(), TagConstants.ATTRIBUTE_CHANNEL_CODE)
            //         || Objects.equals(tagGroupSelect.getAttributeCode(), TagConstants.CHANNEL_CODE)
            //         || Objects.equals(tagGroupSelect.getAttributeCode(), TagConstants.ATTRIBUTE_CHANNEL_STORE_CODE)
            //         || Objects.equals(tagGroupSelect.getAttributeCode(), TagConstants.ATTRIBUTE_SALE_STORE_CODE))) {
            //     dataRangeFlag = true;
            //     break;
            // }
        }
        if (!dataRangeFlag) {
            //标签管理处理
            List<String> tables = newSelects.stream().map(TagGroupSelect::getGpTablePart).collect(Collectors.toList());
            Map<String, String> map = matchTable(tables, SqlTemplateEnum.SYS_USER.getCode());
            stringObjectMap.putAll(map);
            if (!allChannel) {
                String channel = "prmry_busi_mdl_cd = '" + expressionRequest.getChannelCode() + "'";
                Object whereObj = stringObjectMap.get("where_1");
                if (whereObj == null) {
                    stringObjectMap.put("where_1", Arrays.asList(channel));
                } else {
                    if (whereObj instanceof List) {
                        List<String> array = (List<String>) whereObj;
                        array.add(channel);
                        stringObjectMap.put("where_1", array);
                    }
                }
            }
        } else {
            //人群处使用
            for (TagGroupSelect tagGroupSelect : selects) {
                // if (null != tagGroupSelect && Objects.equals(tagGroupSelect.getAttributeCode(), TagConstants.ATTRIBUTE_CHANNEL_CODE)) {
                //     tagGroupSelect.setGpTablePart("prmry");
                // }
                // if (null != tagGroupSelect && Objects.equals(tagGroupSelect.getAttributeCode(), TagConstants.ATTRIBUTE_CHANNEL_STORE_CODE)) {
                //     tagGroupSelect.setGpTablePart("store");
                // }
            }
            List<String> tables = newSelects.stream().map(TagGroupSelect::getGpTablePart).collect(Collectors.toList());
            Map<String, String> map = matchTable(tables, SqlTemplateEnum.SYS_USER.getCode());
            stringObjectMap.putAll(map);
        }
        return getSqlUtil.getSql(stringObjectMap, SqlTemplateEnum.SYS_USER.getCode());
    }
}
