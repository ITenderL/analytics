package com.itender.analytics.tag.service.impl;


import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**

 * @Description 用户行为满足-做过-有行为对象-行为对象需全满足（且）模板解析
 */
@Service("EventDoAndTemplate")
public class EventDoAndTemplate extends TemplateService {

    @Resource
    private ParseSqlUtil parseSqlUtil;
    @Resource
    private GetSqlUtil getSqlUtil;

    @Override
    public String doParseTemplate(TagGroupExpressionRequest expressionRequest) {

        Map<String, Object> stringObjectMap = parseSqlUtil.routeSqlParse(expressionRequest, SqlTemplateEnum.EVENT_DO_AND.getCode());

        List<TagGroupSelect> selects = expressionRequest.getSelects();

        List<String> tables = selects.stream().map(TagGroupSelect::getGpTablePart).collect(Collectors.toList());

        //处理且或关系
        dealRelation(selects, stringObjectMap);

        //根据模板位置分组
        Map<String, String> tableMap = matchTable(tables,SqlTemplateEnum.EVENT_DO_AND.getCode());

        stringObjectMap.putAll(tableMap);

        selects.forEach(s->{
            if (StringUtils.isNotBlank(s.getGpColumnName())&&s.getGpColumnName().contains("/")) {
                dealUnitPrice(s,stringObjectMap);
            }
        });

        return getSqlUtil.getSql(stringObjectMap, SqlTemplateEnum.EVENT_DO_AND.getCode());
    }















}
