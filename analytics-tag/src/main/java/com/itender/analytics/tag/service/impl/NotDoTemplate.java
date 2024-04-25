package com.itender.analytics.tag.service.impl;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
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

@Service("notDoTemplate")
public class NotDoTemplate extends TemplateService {
    @Resource
    ParseSqlUtil parseSqlUtil;
    @Resource
    GetSqlUtil getSqlUtil;

    @Override
    public String doParseTemplate(TagGroupExpressionRequest expressionRequest) {
        // Map<String, Object> stringObjectMap = parseSqlUtil.routeSqlParse(expressionRequest, SqlTemplateEnum.NOT_DO.getCode());
        Map<String, Object> stringObjectMap = new HashMap<>();
        //表组成添加渠道为组成部分
        List<TagGroupSelect> selects = expressionRequest.getSelects();
        TagGroupSelect select = new TagGroupSelect();
        select.setGpTablePart(expressionRequest.getDataRange());
        List<TagGroupSelect> newSelects = new ArrayList<>();
        newSelects.addAll(selects);
        newSelects.add(select);
        List<String> tables = newSelects.stream().map(TagGroupSelect::getGpTablePart).collect(Collectors.toList());

        List<String> whereList = JSON.parseArray(JSON.toJSONString(stringObjectMap.get("where_2")), String.class);
        //判断where_2区域中的是否为空，如果为空，where_2中默认添加动作大于>0
        if (CollUtil.isEmpty(whereList)) {
            List<TagGroupSelect> doActions = selects.stream().filter(s -> StrUtil.isBlank(s.getTemplatePart()) && Objects.isNull(s.getValue())).collect(Collectors.toList());
            if (CollUtil.isNotEmpty(doActions)) {
                List<String> where = new ArrayList<>();
                TagGroupSelect action = doActions.get(0);
                String whereCondition = action.getGpColumnName() + ">0";
                where.add(whereCondition);
                stringObjectMap.put("where_2", where);
            }
        }
        Map<String, String> map = matchTable(tables, SqlTemplateEnum.NOT_DO.getCode());
        stringObjectMap.putAll(map);
        return getSqlUtil.getSql(stringObjectMap, SqlTemplateEnum.NOT_DO.getCode());
    }
}
