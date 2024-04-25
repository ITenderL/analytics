package com.itender.analytics.tag.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.itender.analytics.tag.domain.TagGroupExpressionRequest;
import com.itender.analytics.tag.domain.TagGroupSelect;
import com.itender.analytics.tag.service.TemplateService;
import com.itender.analytics.tag.util.GetSqlUtil;
import com.itender.analytics.tag.util.ParseSqlUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.itender.analytics.tag.enums.SqlTemplateEnum.VIP_NOT_DO_COUPON_PRIZE;


/**
 * @author yuanhewei
 * @date 2024/3/26 11:12
 * @description 会员行为满足-未做过 券 奖-无行为对象 模板解析
 */
@Service("vipNotDoCouponPrizeTemplate")
public class VipNotDoCouponPrizeTemplate extends TemplateService {
    @Resource
    ParseSqlUtil parseSqlUtil;
    @Resource
    GetSqlUtil getSqlUtil;

    public static final String PRIZE = "prize";
    public static final String COUPON = "coupon";
    public static final String FORMATTER = "yyyy-MM-dd HH:mm:ss";


    @Override
    public String doParseTemplate(TagGroupExpressionRequest expressionRequest) {
        // Map<String, Object> stringObjectMap = parseSqlUtil.routeSqlParse(expressionRequest, VIP_NOT_DO_COUPON_PRIZE.getCode());
        Map<String, Object> stringObjectMap = new HashMap<String, Object>();
        // 表组成添加渠道为组成部分
        List<TagGroupSelect> selects = expressionRequest.getSelects();
        TagGroupSelect select = new TagGroupSelect();
        select.setGpTablePart(expressionRequest.getDataRange());
        List<TagGroupSelect> newSelects = new ArrayList<>(selects);
        //处理且或关系
        dealRelation(selects, stringObjectMap);
        newSelects.add(select);
        List<String> tables = newSelects.stream().map(TagGroupSelect::getGpTablePart).collect(Collectors.toList());
        // 做过领券，未用券，加上券id关联
        List<String> on1 = JSON.parseArray(JSON.toJSONString(stringObjectMap.get("on_1")), String.class);
        if (CollUtil.isNotEmpty(on1)) {
            on1.add("id");
            stringObjectMap.put("on_1", on1);
        }
        String endTime = StrUtil.EMPTY;
        // 领券时间不做限制，用券时间小于结束时间，而不是取between的时间区间
        List<String> where1 = JSON.parseArray(JSON.toJSONString(stringObjectMap.get("where_1")), String.class);
        if (CollUtil.isNotEmpty(where1)) {
            String where1Str = String.valueOf(where1.get(0));
            if (StrUtil.isNotBlank(where1Str)) {
                endTime = where1Str.substring(where1Str.indexOf("and")).substring(3);
                stringObjectMap.put("where_1", Collections.singletonList("dt <= " + endTime));
            }
        }
        // where2 判断用券，兑奖是否在有效期内
        List<String> where2 = JSON.parseArray(JSON.toJSONString(stringObjectMap.get("where_2")), String.class);
        if (CollUtil.isNotEmpty(where2)) {
            // 是否在有效期内，1是: t1.expire_time >= t1.dt   0否： t1.expire_time < t1.dt  行为日期
            String where2Str = " t1.expire_time >= " + endTime(endTime);
            // 如果是兑奖，在有效期内，expire_time为null（永久有效）的也要统计上
            if (tables.contains(PRIZE)) {
                where2Str = "((t1.expire_time is null) or " + where2Str + ")";
            }
            if (!Objects.equals(where2.get(0), " is_valid =1")) {
                where2Str = "t1.expire_time < " + endTime(endTime);
            }
            stringObjectMap.put("where_2", Collections.singletonList(where2Str));
        }
        // where4 判断距过期还有多少天，过期时间 - 行为时间 > 几天
        List<String> where4 = JSON.parseArray(JSON.toJSONString(stringObjectMap.get("where_4")), String.class);
        if (CollUtil.isNotEmpty(where4)) {
            List<String> whereList = Lists.newArrayList();
            String whereStr = where4.get(0);
            whereStr = whereStr.replace("expire_time", "(to_number(date_trunc('day', t1.expire_time::TIMESTAMP - " + endTime(endTime) + "::TIMESTAMP + INTERVAL '1 day')::text, '9999999999999'))");
            // 如果是兑奖，在距有效期大于或大于等于，expire_time为null（永久有效）的也要统计上
            if (tables.contains(PRIZE) && (whereStr.contains(">") || whereStr.contains(">="))) {
                whereStr = "((t1.expire_time is null) or " + whereStr + ")";
            }
            if (whereStr.contains("<") || whereStr.contains("<=")) {
                whereStr = "(to_number(date_trunc('day', t1.expire_time::TIMESTAMP - " + endTime(endTime) + "::TIMESTAMP + INTERVAL '1 day')::text, '9999999999999') >= 0 and " + whereStr + ")";
            }
            whereList.add(whereStr);
            stringObjectMap.put("where_4", whereList);
        }
        // 判断是用券还是兑奖
        if (tables.contains(COUPON)) {
            // 如果是用券，领过券，没有用券
            stringObjectMap.put("on_2", Collections.singletonList(" t2.event_name = 'use_coupon' and t2.is_used = 1"));
            stringObjectMap.put("where_6", Collections.singletonList(" event_name = 'get_coupon'"));
        }
        if (tables.contains(PRIZE)) {
            // 如果是兑奖，中过奖，没有兑奖
            stringObjectMap.put("on_2", Collections.singletonList(" t2.event_name = 'claim_prize' and t2.is_used = 1"));
            stringObjectMap.put("where_6", Collections.singletonList(" event_name = 'win_prize'"));
        }
        // 合并where2和where4条件
        Object where2Relation = stringObjectMap.get("where_2_relation");
        Object where4Relation = stringObjectMap.get("where_4_relation");
        if (ObjectUtil.isNotEmpty(where4Relation)) {
            List<String> where4Or = JSON.parseArray(JSON.toJSONString(stringObjectMap.get("where_4")), String.class);
            if (ObjectUtil.isNotEmpty(where2Relation)) {
                List<String> where2Or = JSON.parseArray(JSON.toJSONString(stringObjectMap.get("where_2")), String.class);
                where4Or.addAll(where2Or);
            }
            stringObjectMap.put("where_4", where4Or);
        } else {
            if (ObjectUtil.isNotEmpty(where2Relation)) {
                List<String> where2Or = JSON.parseArray(JSON.toJSONString(stringObjectMap.get("where_2")), String.class);
                stringObjectMap.put("where_4", where2Or);
            }
        }
        Map<String, String> map = matchTable(tables, VIP_NOT_DO_COUPON_PRIZE.getCode());
        stringObjectMap.putAll(map);
        return getSqlUtil.getSql(stringObjectMap, VIP_NOT_DO_COUPON_PRIZE.getCode());
    }

    /**
     * 获取昨天的结束时间 yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String endTime(String endTime) {
        // 如果选择的结束时间大于当前时间，则结束时间为昨天的结束时间，否则为当前选择的结束时间
        String endTimeStr = endTime.replace("'", "");
        DateTime endDateTime = DateUtil.parse(endTimeStr, FORMATTER);
        if (!endDateTime.isAfter(dayLastTime(DateUtil.date()))) {
            return endTime;
        }
        DateTime yesterday = DateUtil.yesterday();
        String yesterdayStr = DateUtil.format(yesterday, FORMATTER);
        return "'" + yesterdayStr.substring(0, 10) + " 23:59:59'";
    }

    /**
     * 获取某天的结束时间 yyyy-MM-dd  23:59:59
     *
     * @return
     */
    public static DateTime dayLastTime(DateTime dateTime) {
        String dateTimeStr = DateUtil.format(dateTime, FORMATTER);
        dateTimeStr = dateTimeStr.substring(0, 10) + " 23:59:59";
        return DateUtil.parse(dateTimeStr, FORMATTER);
    }
}
