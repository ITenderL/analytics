package com.itender.analytics.tag.service.impl;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import com.itender.analytics.tag.domain.TagGroupExpressionRequest;
import com.itender.analytics.tag.domain.TagGroupSelect;
import com.itender.analytics.tag.enums.SqlTemplateEnum;
import com.itender.analytics.tag.service.TemplateService;
import com.itender.analytics.tag.util.GetSqlUtil;
import com.itender.analytics.tag.util.ParseSqlUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("doMaxMinTemplate")
public class DoMaxMinTemplate extends TemplateService {

    @Resource
    private ParseSqlUtil parseSqlUtil;
    @Resource
    private GetSqlUtil getSqlUtil;

    @Override
    public String doParseTemplate(TagGroupExpressionRequest expressionRequest) {
        // Map<String, Object> stringObjectMap = parseSqlUtil.routeSqlParse(expressionRequest, SqlTemplateEnum.Do_Max_Min.getCode());
        Map<String, Object> stringObjectMap = new HashMap<String, Object>();
        List<TagGroupSelect> selects = expressionRequest.getSelects();
        List<String> tables = selects.stream().map(TagGroupSelect::getGpTablePart).collect(Collectors.toList());
        List<TagGroupSelect> relations = selects.stream().filter(s -> CharSequenceUtil.isNotBlank(s.getRelation())).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(relations)) {
            String relation = relations.get(0).getRelation();
            stringObjectMap.put("relation", relation);
        }
        //获取表
        Map<String, String> tableMap = matchTable(tables, SqlTemplateEnum.DO_AND.getCode());
        stringObjectMap.putAll(tableMap);
        selects.forEach(s -> {
            if (CharSequenceUtil.isNotBlank(s.getGpColumnName()) && s.getGpColumnName().contains("/")) {
                dealUnitPrice(s, stringObjectMap);
            }
        });
        return getSqlUtil.getSql(stringObjectMap, SqlTemplateEnum.DO_AND.getCode());
    }
}
