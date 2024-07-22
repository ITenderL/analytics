package com.itender.analytics.tag.service.impl;


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

/**
 * @Author
 * @Date
 * @Description 用户行为满足-做过-有行为对象-行为对象需全满足（且）模板解析
 */
@Service("doAndTemplate")
public class DoAndTemplate extends TemplateService {

    @Resource
    private ParseSqlUtil parseSqlUtil;
    @Resource
    private GetSqlUtil getSqlUtil;


    @Override
    public String doParseTemplate(TagGroupExpressionRequest expressionRequest) {
        // Map<String, Object> stringObjectMap = parseSqlUtil.routeSqlParse(expressionRequest, SqlTemplateEnum.DO_AND.getCode());
        Map<String, Object> stringObjectMap = new HashMap<>();
        List<TagGroupSelect> selects = expressionRequest.getSelects();
        List<String> tables = selects.stream().map(TagGroupSelect::getGpTablePart).collect(Collectors.toList());
        //处理且或关系
        dealRelation(selects, stringObjectMap);
        //获取表
        Map<String, String> tableMap = matchTable(tables, SqlTemplateEnum.DO_AND.getCode());
        stringObjectMap.putAll(tableMap);
        selects.forEach(s -> {
            if (StrUtil.isNotBlank(s.getGpColumnName()) && s.getGpColumnName().contains("/")) {
                dealUnitPrice(s, stringObjectMap);
            }
        });

        return getSqlUtil.getSql(stringObjectMap, SqlTemplateEnum.DO_AND.getCode());
    }
}
