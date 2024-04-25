package com.itender.analytics.tag.service.impl;


import com.itender.analytics.tag.domain.TagGroupExpressionRequest;
import com.itender.analytics.tag.enums.SqlTemplateEnum;
import com.itender.analytics.tag.service.TemplateService;
import com.itender.analytics.tag.util.GetSqlUtil;
import com.itender.analytics.tag.util.ParseSqlUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


@Service("NotDoPayTemplate")
public class NotDoPayTemplate extends TemplateService {
    @Resource
    ParseSqlUtil parseSqlUtil;
    @Resource
    GetSqlUtil getSqlUtil;

    @Override
    public String doParseTemplate(TagGroupExpressionRequest expressionRequest) {
        // Map<String, Object> stringObjectMap = parseSqlUtil.routeSqlParse(expressionRequest, SqlTemplateEnum.NOT_DO_PAY.getCode());
        Map<String, Object> stringObjectMap = new HashMap<>();
        return getSqlUtil.getSql(stringObjectMap, SqlTemplateEnum.NOT_DO_PAY.getCode());
    }
}
