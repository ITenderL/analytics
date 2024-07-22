package com.itender.analytics.tag.service.impl;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
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


@Service("sysAggMaxUserTemplate")
public class SysAggMaxUserTemplate extends TemplateService {

    @Resource
    ParseSqlUtil parseSqlUtil;

    @Resource
    GetSqlUtil getSqlUtil;

    @Override
    public String doParseTemplate(TagGroupExpressionRequest expressionRequest) {
        List<TagGroupSelect> selects = expressionRequest.getSelects();
        // Map<String, Object> stringObjectMap = parseSqlUtil.routeSqlParse(expressionRequest, SqlTemplateEnum.SYS_AGG_MAX.getCode());
        Map<String, Object> stringObjectMap = new HashMap<>();
        // 表组成添加渠道为组成部分
        List<String> tables = selects.stream().map(TagGroupSelect::getGpTablePart).collect(Collectors.toList());
        Map<String, String> map = matchTable(tables, SqlTemplateEnum.SYS_AGG_MAX.getCode());
        stringObjectMap.putAll(map);
        // 解析选择的渠道
        Object storeValue = expressionRequest.getSelects().get(0).getStoreValue();
        if (Objects.nonNull(storeValue)) {
            stringObjectMap.put("channelLevel", 3);
        } else {
            stringObjectMap.put("channelLevel", 1);
        }
        String channel = "prmry_busi_mdl_cd = '" + expressionRequest.getChannelCode() + "'";
        Object whereObj = stringObjectMap.get("where_1");
        List<String> array;
        if (Objects.isNull(whereObj)) {
            array = new ArrayList<>();
            array.add(channel);
            stringObjectMap.put("where_1", array);
        } else {
            array = (List<String>) whereObj;
            if (whereObj instanceof List) {
                array.add(channel);
                stringObjectMap.put("where_1", array);
            }
        }
        List<String> select = (List<String>) stringObjectMap.get("select_1");
        if (CollUtil.isNotEmpty(select)) {
            String col = select.get(0);
            if (StrUtil.isNotBlank(col)) {
                if ("cmn_cnee_city_180".equals(col)) {
                    stringObjectMap.put("selectsum_1", Arrays.asList("sum(cmn_cnee_city_180_cnt) as sumSort"));
                    stringObjectMap.put("selectmax_1", Arrays.asList("max(cmn_cnee_city_180_time) as maxSort"));
                } else if ("cmn_cnee_city".equals(col)) {
                    stringObjectMap.put("selectsum_1", Arrays.asList("sum(cmn_cnee_city_cnt) as sumSort"));
                    stringObjectMap.put("selectmax_1", Arrays.asList("max(cmn_cnee_city_time) as maxSort"));
                }

            }
        }
        return getSqlUtil.getSql(stringObjectMap, SqlTemplateEnum.SYS_AGG_MAX.getCode());
    }
}
