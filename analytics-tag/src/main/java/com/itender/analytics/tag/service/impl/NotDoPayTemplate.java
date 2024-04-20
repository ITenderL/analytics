package com.itender.analytics.tag.service.impl;


import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author wangyimin
 * @Date 2022/1/17 18:12
 * @Description 用户行为满足-未做过模板解析
 */
@Service("NotDoPayTemplate")
public class NotDoPayTemplate extends TemplateService {
    @Resource
    ParseSqlUtil parseSqlUtil;
    @Resource
    GetSqlUtil getSqlUtil;

    @Override
    public String doParseTemplate(TagGroupExpressionRequest expressionRequest) {
        Map<String, Object> stringObjectMap = parseSqlUtil.routeSqlParse(expressionRequest, SqlTemplateEnum.NOT_DO_PAY.getCode());
        stringObjectMap.putAll(stringObjectMap);
        return getSqlUtil.getSql(stringObjectMap, SqlTemplateEnum.NOT_DO_PAY.getCode());
    }
}
