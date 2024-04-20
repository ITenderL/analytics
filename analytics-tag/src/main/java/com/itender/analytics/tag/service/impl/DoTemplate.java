package com.itender.analytics.tag.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.sigo.idata.platform.tag.api.model.request.TagGroupExpressionRequest;
import cn.sigo.idata.platform.tag.api.model.request.TagGroupSelect;
import cn.sigo.idata.platform.tag.model.core.SqlTemplateEnum;
import cn.sigo.idata.platform.tag.service.TemplateService;
import cn.sigo.idata.platform.tag.util.GetSqlUtil;
import cn.sigo.idata.platform.tag.util.ParseSqlUtil;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author wangyimin
 * @Date 2022/1/17 18:12
 * @Description 用户行为满足-做过-无行为对象 模板解析
 */
@Service("DoTemplate")
public class DoTemplate extends TemplateService {

    @Resource
    ParseSqlUtil parseSqlUtil;

    @Resource
    GetSqlUtil getSqlUtil;

    @Override
    public String doParseTemplate(TagGroupExpressionRequest expressionRequest) {
        List<TagGroupSelect> selects = expressionRequest.getSelects();
        //单独处理获取gpTablePart不为空，并且value为空的部分，说明是做过的行为
        Map<String, Object> stringObjectMap = parseSqlUtil.routeSqlParse(expressionRequest, SqlTemplateEnum.DO.getCode());
        //处理且或关系
        dealRelation(selects, stringObjectMap);

        selects.forEach(s->{
            if (StringUtils.isNotBlank(s.getGpColumnName())&&s.getGpColumnName().contains("/")) {
                dealUnitPrice(s,stringObjectMap);
            }
        });

        List<String> whereList = JSON.parseArray(JSON.toJSONString(stringObjectMap.get("where_2")), String.class);
        List<String> sumList = JSON.parseArray(JSON.toJSONString(stringObjectMap.get("selectsum_1")), String.class);
        //判断where_2区域中的是否为空，如果为空，在selectsum_1|where_2中默认添加动作大于>0
        if (CollectionUtil.isEmpty(whereList)&&CollectionUtil.isEmpty(sumList)) {
            List<TagGroupSelect> doActions = selects.stream().filter(s -> StrUtil.isBlank(s.getTemplatePart()) && ObjectUtil.isNull(s.getValue())).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(doActions)) {
                List<String> where = new ArrayList<>();
                List<String> sum = new ArrayList<>();
                TagGroupSelect action = null;
                for (TagGroupSelect doAction : doActions) {
                    if (null != doAction) {
                        action = doAction;
                        if (!Objects.equals(doAction.getGpColumnName(),"last_pay_time")
                                && !Objects.equals(doAction.getGpColumnName(),"fisrt_pay_time")
                                && !Objects.equals(doAction.getGpColumnName(),"last_pv_time")) {
                            break;
                        }
                    }
                }
                String whereCondition = action.getGpColumnName() + ">0";
                String sumCondition = "sum("+action.getGpColumnName()+") as " + action.getGpColumnName();
                where.add(whereCondition);
                sum.add(sumCondition);
                stringObjectMap.put("where_2",where);
                stringObjectMap.put("selectsum_1",sum);
            }
        }


        return getSqlUtil.getSql(stringObjectMap, SqlTemplateEnum.DO.getCode());
    }
}
