package com.itender.analytics.tag.util;


import com.alibaba.fastjson.JSONObject;
import com.itender.analytics.tag.domain.TagGroupExpressionRequest;
import com.itender.analytics.tag.domain.TagGroupSelect;
import com.itender.analytics.tag.domain.dto.AttributeLocation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;




@Component
@Slf4j
public class ParseSqlUtil {
    // @Resource
    // private SqlLocationRoute sqlLocationRoute;
    //
    // @Resource
    // private AttributeConfCustomMapper attributeConfCustomMapper;
    //
    // @Resource
    // private TagRefreshConstants tagRefreshConstants;
    //
    // @Resource
    // private AttributeConfCustomVipMapper attributeConfCustomVipMapper;
    //
    // /**
    //  * 策略路由至不同方法解析sql
    //  *
    //  * @param expressionRequest
    //  * @return
    //  */
    // public Map<String, Object> routeSqlParse(TagGroupExpressionRequest expressionRequest, String templateCode) {
    //     List<TagGroupSelect> selects = expressionRequest.getSelects();
    //     for (TagGroupSelect select : selects) {
    //         select.setGpColumnName(null);
    //     }
    //     Integer globalTagType = expressionRequest.getGlobalTagType();
    //     log.info("routeSqlParse templateCode: {}, globalTagType: {}", templateCode, globalTagType);
    //     //根据模板获取每个属性的sql模板位置
    //     List<AttributeLocation> templateLocation = attributeConfCustomMapper.getTemplateLocation(templateCode);
    //
    //     // 模板集合
    //     Map<Integer, AttributeLocation> attributeLocationMap = new HashMap<>();
    //     for (AttributeLocation location : templateLocation) {
    //         attributeLocationMap.put(location.getAttributeId(), location);
    //     }
    //     // 条件集合
    //     Map<Integer, TagGroupSelect> selectsMap = new HashMap<>();
    //     for (TagGroupSelect select : selects) {
    //         Integer selectId = Integer.parseInt(select.getAttributeId().toString());
    //         selectsMap.put(selectId, select);
    //         if (attributeLocationMap.containsKey(selectId)) {
    //             AttributeLocation location = attributeLocationMap.get(selectId);
    //             select.setSqlLocation(location.getSqlLocation());
    //             //设置模板对应字段
    //             select.setGpColumnName(location.getGpColumnName());
    //         }
    //     }
    //     // 用于处理首末次的问题(最终处理)
    //     try {
    //         dealFirstEndColumnInfo(selectsMap, expressionRequest);
    //     } catch (Exception e) {
    //         log.error("dealFirstEndColumnInfo error:", JSONObject.toJSONString(selects), e);
    //     }
    //
    //     //遍历解析sqlLocation
    //     List<TagGroupSelect> splitLoc = new ArrayList<>();
    //     selects.forEach(t -> {
    //         String sqlLocation = t.getSqlLocation();
    //         if (StrUtil.isNotBlank(sqlLocation)) {
    //             List<String> loc = Arrays.asList(sqlLocation.split("\\|"));
    //             loc.forEach(s -> {
    //                 TagGroupSelect select = new TagGroupSelect();
    //                 select.setDataRange(t.getDataRange());
    //                 select.setAttributeId(t.getAttributeId());
    //                 select.setGpColumnName(t.getGpColumnName());
    //                 select.setGpTablePart(t.getGpTablePart());
    //                 select.setSqlLocation(s);
    //                 select.setTemplatePart(t.getTemplatePart());
    //                 select.setValue(t.getValue());
    //                 if (null != t.getStoreValue()) {
    //                     select.setStoreValue(t.getStoreValue());
    //                 }
    //                 splitLoc.add(select);
    //             });
    //         }
    //     });
    //
    //
    //     //按照模板位置分组
    //     Map<String, Object> mapAll = new HashMap<String, Object>(16);
    //     Map<String, List<TagGroupSelect>> groupLoc = splitLoc.stream().collect(Collectors.groupingBy(TagGroupSelect::getSqlLocation));
    //     for (String key : groupLoc.keySet()) {
    //         String[] split = key.split("_");
    //         String prefix = split[0];
    //         List<TagGroupSelect> tagGroupSelects = groupLoc.get(key);
    //         List<String> sqlStr = sqlLocationRoute.routeSqlLocation(tagGroupSelects, prefix);
    //         mapAll.put(key, sqlStr);
    //     }
    //     return mapAll;
    // }
    //
    // /**
    //  * 处理首末次的信息
    //  *
    //  * @param selectsMap
    //  * @param expressionRequest
    //  */
    // private void dealFirstEndColumnInfo(Map<Integer, TagGroupSelect> selectsMap, TagGroupExpressionRequest expressionRequest) {
    //     // 首先判断是否包含特殊数值
    //     // 首末次id11:支付或访问id1|字段名,支付或访问id2|字段名;首末次id22:支付或访问id1|字段名,支付或访问id2|字段名
    //     String tagAttributeIdsDataFirstendInfo = tagRefreshConstants.getTagAttributeIdsDataFirstendInfo();
    //     String[] infos = tagAttributeIdsDataFirstendInfo.split(";");
    //     if (null != infos && infos.length > 0) {
    //         for (String item : infos) {
    //             // 首末次id11:支付或访问id1|字段名,支付或访问id2|字段名;
    //             String[] idInfos = item.split(":");
    //             if (null != idInfos && idInfos.length == 2) {
    //                 // 首末次id，判断是否包含
    //                 Integer firstEndId = Integer.valueOf(idInfos[0]);
    //                 if (selectsMap.containsKey(firstEndId)) {
    //                     String firstEndInfo = idInfos[1];
    //                     // 支付或访问id1|字段名,支付或访问id2|字段名;
    //                     String[] payPvInfos = firstEndInfo.split(",");
    //                     if (null != payPvInfos && payPvInfos.length > 0) {
    //                         for (String payPvInfo : payPvInfos) {
    //                             // 支付或访问id1|字段名
    //                             String[] columnInfo = payPvInfo.split("\\|");
    //                             if (null != columnInfo && columnInfo.length == 2) {
    //                                 // 支付或访问id1
    //                                 Integer columnId = Integer.valueOf(columnInfo[0]);
    //                                 if (selectsMap.containsKey(columnId)) {
    //                                     // 替换当时的字段名
    //                                     TagGroupSelect tagGroupSelect = selectsMap.get(firstEndId);
    //                                     if (Objects.equals(expressionRequest.getDataRange(), tagRefreshConstants.getAllChannelStr())) {
    //                                         tagGroupSelect.setDataRange(tagRefreshConstants.getAllChannelStr());
    //                                     } else {
    //                                         tagGroupSelect.setDataRange(expressionRequest.getChannelCode());
    //                                     }
    //                                     tagGroupSelect.setGpColumnName(columnInfo[1]);
    //                                     break;
    //                                 }
    //                             }
    //                         }
    //                     }
    //                     break;
    //                 }
    //             }
    //         }
    //     }
    // }
    //
    //
    // /**
    //  * 解析where条件
    //  *
    //  * @param tagGroupSelects
    //  * @return
    //  */
    // public List<String> doWhere(List<TagGroupSelect> tagGroupSelects) {
    //     //判断是否包含relation
    //     List<DateEnum> dates = new ArrayList<>(Arrays.asList(DateEnum.values()));
    //     List<String> dateCodes = dates.stream().map(DateEnum::getCode).collect(Collectors.toList());
    //     // 判断是否包含rangeData 时间问题
    //     Object rangeData = null;
    //     // 获取行为日志标志id
    //     Set<Integer> dataRangeSets = TemplateUtils.initRefreshConstants(tagRefreshConstants.getTagAttributeIdsDataRange());
    //     if (!CollectionUtils.isEmpty(dataRangeSets)) {
    //         for (TagGroupSelect tagGroupSelect : tagGroupSelects) {
    //             Object attributeId = tagGroupSelect.getAttributeId();
    //             if (dataRangeSets.contains(attributeId)) {
    //                 rangeData = tagGroupSelect.getValue();
    //             }
    //         }
    //     }
    //
    //     List<String> sqlPart = new ArrayList<>();
    //     for (TagGroupSelect select : tagGroupSelects) {
    //         try {
    //             // 判断首末次支付翻译
    //             if ((Objects.equals(select.getGpColumnName(), "last_pay_time") ||
    //                     Objects.equals(select.getGpColumnName(), "fisrt_pay_time")) && null != rangeData) {
    //                 String time = null;
    //                 //判断时间
    //                 if (dateCodes.contains((String) rangeData)) {
    //                     time = TagDateUtil.getTime((String) rangeData);
    //                 } else {
    //                     time = (String) rangeData;
    //                 }
    //                 String firstEndPaySql = getFirstEndPaySql(select, time);
    //                 sqlPart.add(firstEndPaySql);
    //                 continue;
    //             } else if (Objects.equals(select.getGpColumnName(), "last_pv_time") && null != rangeData) {
    //                 String time = null;
    //                 //判断时间
    //                 if (dateCodes.contains((String) rangeData)) {
    //                     time = TagDateUtil.getTime((String) rangeData);
    //                 } else {
    //                     time = (String) rangeData;
    //                 }
    //                 String sb = getLastPvSql(select, time);
    //                 sqlPart.add(sb);
    //                 continue;
    //             } else {
    //                 StringBuilder sqlBuilder = new StringBuilder();
    //                 sqlBuilder.append(" ");
    //                 String gpColumnName = select.getGpColumnName();
    //                 sqlBuilder.append(gpColumnName);
    //                 sqlBuilder.append(" ");
    //                 Object value = select.getValue();
    //                 if(hasFilter(gpColumnName)){
    //                     sqlPart.add(sqlBuilder.toString());
    //                     continue;
    //                 }
    //
    //                 if (ObjectUtil.isNotNull(value)) {
    //                     //处理数组类型的传值
    //                     if (value instanceof List) {
    //                         List arrayValue = (List) value;
    //                         if (CollectionUtil.isNotEmpty(arrayValue)) {
    //                             if (arrayValue.size() == 1) {
    //                                 //单个值用等于拼接
    //                                 sqlBuilder.append(" = ");
    //                                 Object o = arrayValue.get(0);
    //                                 if(o instanceof String ){
    //                                     String str = String.valueOf(o);
    //                                     if (str.contains("'")) {
    //                                         //gp转义
    //                                         str = str.replace("'", "''");
    //                                     }
    //                                     //处理值的类型
    //                                     sqlBuilder.append("'");
    //                                     sqlBuilder.append(str);
    //                                     sqlBuilder.append("'");
    //                                 }else {
    //                                     sqlBuilder.append(o);
    //                                 }
    //                             } else {
    //                                 //多个值用in
    //                                 sqlBuilder.append(" in ");
    //                                 sqlBuilder.append(" (");
    //                                 for (Object obj : arrayValue) {
    //                                     //处理值的类型
    //                                     if(obj instanceof String ){
    //                                         String str = String.valueOf(obj);
    //                                         if (str.contains("'")) {
    //                                             //转义
    //                                             str = str.replace("'", "''");
    //                                         }
    //                                         //处理值的类型
    //                                         sqlBuilder.append("'");
    //                                         sqlBuilder.append(str);
    //                                         sqlBuilder.append("'");
    //                                     }else {
    //                                         sqlBuilder.append(obj);
    //                                     }
    //
    //                                     if (arrayValue.indexOf(obj) != arrayValue.size() - 1) {
    //                                         sqlBuilder.append(",");
    //                                     }
    //                                 }
    //                                 sqlBuilder.append(" )");
    //                             }
    //                         }
    //                     } else if (value instanceof String) {
    //                         //处理topN
    //                         if (ObjectUtil.isNotNull(select.getValue()) && select.getValue().toString().contains("top")) {
    //                             String[] inValue = value.toString().split("_");
    //                             String selectValue = inValue[1];
    //                             value = "<= " + selectValue;
    //                         }
    //
    //                         //判断时间
    //                         if (dateCodes.contains(value)||((String) value).toLowerCase().contains(DateEnum.future.getCode())||((String) value).toLowerCase().contains(DateEnum.past.getCode())) {
    //                             value = TagDateUtil.getTime((String) value);
    //                         }
    //
    //                         if (!hasFilter((String) value)) {
    //                             //判断是否需要拼接等于号
    //                             sqlBuilder.append(" = ");
    //                         }
    //
    //                         valueConvert(value, sqlBuilder);
    //                     } else {
    //                         sqlBuilder.append(" = ");
    //                         sqlBuilder.append(value);
    //                     }
    //                     sqlPart.add(sqlBuilder.toString());
    //                 }
    //
    //             }
    //         } catch (Exception e) {
    //             log.error("parse first|lastpay time error:", e);
    //         }
    //     }
    //     return sqlPart;
    // }
    //
    //
    // public List<String> doArrayOrWhere(List<TagGroupSelect> tagGroupSelects) {
    //     List<String> sqlPart = new ArrayList<>();
    //     for (TagGroupSelect select : tagGroupSelects) {
    //         StringBuilder sqlBuilder = new StringBuilder();
    //         sqlBuilder.append(" ");
    //         String gpColumnName = select.getGpColumnName();
    //         sqlBuilder.append(gpColumnName);
    //         sqlBuilder.append(" ");
    //         Object value = select.getValue();
    //         if (ObjectUtil.isNotNull(value)) {
    //             //处理数组类型的传值
    //             if (value instanceof List) {
    //                 List arrayValue = (List) value;
    //                 if (CollectionUtil.isNotEmpty(arrayValue)) {
    //                     sqlBuilder.append(" && array[");
    //                     for (Object obj : arrayValue) {
    //                         //处理值的类型
    //                         valueConvert(obj, sqlBuilder);
    //                         if (arrayValue.indexOf(obj) != arrayValue.size() - 1) {
    //                             sqlBuilder.append(",");
    //                         }
    //                     }
    //                     sqlBuilder.append(" ]");
    //                 }
    //             }
    //             sqlPart.add(sqlBuilder.toString());
    //         }
    //     }
    //     return sqlPart;
    // }
    //
    // /**
    //  * 获取最后访问时间sql
    //  *
    //  * @param select
    //  * @param time
    //  * @return
    //  */
    // private String getLastPvSql(TagGroupSelect select, String time) {
    //     StringBuilder sb = new StringBuilder();
    //     sb.append(" user_acct in ( ");
    //     sb.append("select user_acct from ").append(tagRefreshConstants.getUserTableNameTagOneId()).append(" where ").append(select.getGpColumnName()).append(" ").append(time);
    //     if (!StringUtils.isEmpty(select.getDataRange()) && !Objects.equals(select.getDataRange(), tagRefreshConstants.getAllChannelStr())) {
    //         sb.append(" and prmry_busi_mdl_cd = '").append(select.getDataRange()).append("'");
    //         // 首末次支付增加店铺限制
    //         if (null != select.getStoreValue() && select.getStoreValue() instanceof List) {
    //             List arrayValue = (List) select.getStoreValue();
    //             sb.append(" and chnl_str_cd in (");
    //             for (Object o : arrayValue) {
    //                 sb.append("'").append(o).append("'").append(",");
    //             }
    //             sb.deleteCharAt(sb.length() - 1);
    //             sb.append(")");
    //         }
    //     }
    //     sb.append(")");
    //     return sb.toString();
    // }
    //
    // /**
    //  * 获取首次末次支付相关的sql
    //  *
    //  * @param select
    //  * @return
    //  */
    // private String getFirstEndPaySql(TagGroupSelect select, String time) {
    //     StringBuilder sqlBuilder = new StringBuilder();
    //     sqlBuilder.append(" user_acct in ( ");
    //     sqlBuilder.append(" select user_acct from ").append(tagRefreshConstants.getUserTableNameTagOneId()).append(" where ").append(select.getGpColumnName()).append(" ").append(time);
    //     if (!StringUtils.isEmpty(select.getDataRange()) && !Objects.equals(select.getDataRange(), tagRefreshConstants.getAllChannelStr())) {
    //         sqlBuilder.append(" and prmry_busi_mdl_cd = '").append(select.getDataRange()).append("'");
    //         // 首末次支付增加店铺限制
    //         if (null != select.getStoreValue() && select.getStoreValue() instanceof List) {
    //             List arrayValue = (List) select.getStoreValue();
    //             sqlBuilder.append(" and chnl_str_cd in (");
    //             for (Object o : arrayValue) {
    //                 sqlBuilder.append("'").append(o).append("'").append(",");
    //             }
    //             sqlBuilder.deleteCharAt(sqlBuilder.length() - 1);
    //             sqlBuilder.append(")");
    //         }
    //     }
    //     sqlBuilder.append(")");
    //     return sqlBuilder.toString();
    // }
    //
    //
    // /**
    //  * 解析select 中sum 类型
    //  *
    //  * @param tagGroupSelects
    //  * @return
    //  */
    // public List<String> doSelectSum(List<TagGroupSelect> tagGroupSelects) {
    //     List<String> sqlPart = new ArrayList<>();
    //     for (TagGroupSelect select : tagGroupSelects) {
    //         String gpColumnName = select.getGpColumnName();
    //         if (StrUtil.isNotBlank(gpColumnName) && !select.getGpColumnName().contains("/")) {
    //             //非客单价处理
    //             if (ObjectUtil.isNotNull(select.getValue())) {
    //                 StringBuilder sqlBuilder = new StringBuilder();
    //                 if (select.getValue().toString().contains("top")) {
    //                     //top n 处理
    //                     sqlBuilder.append("ROW_NUMBER() over (PARTITION BY user_acct ORDER BY ");
    //                     sqlBuilder.append("sum(");
    //                     sqlBuilder.append(gpColumnName);
    //                     sqlBuilder.append(") ");
    //                     sqlBuilder.append(" desc) as ");
    //                     sqlBuilder.append(gpColumnName);
    //                 } else {
    //                     sqlBuilder.append("sum(");
    //                     sqlBuilder.append(gpColumnName);
    //                     sqlBuilder.append(") as ");
    //                     sqlBuilder.append(gpColumnName);
    //                 }
    //                 sqlPart.add(sqlBuilder.toString());
    //             }
    //
    //         }
    //     }
    //     return sqlPart;
    // }
    //
    //
    //
    // public List<String> doRbOrCardinality(List<TagGroupSelect> tagGroupSelects) {
    //     List<String> sqlPart = new ArrayList<>();
    //     for (TagGroupSelect select : tagGroupSelects) {
    //         String gpColumnName = select.getGpColumnName();
    //         if (StrUtil.isNotBlank(gpColumnName) && !select.getGpColumnName().contains("/")) {
    //             //非客单价处理
    //             if (ObjectUtil.isNotNull(select.getValue())) {
    //                 StringBuilder sqlBuilder = new StringBuilder();
    //                 if (select.getValue().toString().contains("top")) {
    //                     //top n 处理
    //                     sqlBuilder.append("ROW_NUMBER() over (PARTITION BY user_acct ORDER BY ");
    //                     sqlBuilder.append("rb_or_cardinality_agg(");
    //                     sqlBuilder.append(gpColumnName);
    //                     sqlBuilder.append(") ");
    //                     sqlBuilder.append(" desc) ");
    //                     sqlBuilder.append(gpColumnName);
    //                 } else {
    //                     sqlBuilder.append("rb_or_cardinality_agg(");
    //                     sqlBuilder.append(gpColumnName);
    //                     sqlBuilder.append(") as ");
    //                     sqlBuilder.append(gpColumnName);
    //                 }
    //                 sqlPart.add(sqlBuilder.toString());
    //             }
    //
    //         }
    //     }
    //     return sqlPart;
    // }
    //
    // /**
    //  * 解析select
    //  *
    //  * @param tagGroupSelects
    //  * @return
    //  */
    // public List<String> doSelect(List<TagGroupSelect> tagGroupSelects) {
    //     List<String> sqlPart = new ArrayList<>();
    //     for (TagGroupSelect select : tagGroupSelects) {
    //         StringBuilder sqlBuilder = new StringBuilder();
    //         String gpColumnName = select.getGpColumnName();
    //         sqlBuilder.append(gpColumnName);
    //         sqlPart.add(sqlBuilder.toString());
    //     }
    //     return sqlPart;
    // }
    //
    //
    //
    // public List<String> doOrder(List<TagGroupSelect> tagGroupSelects) {
    //     List<String> sqlPart = new ArrayList<>();
    //     for (TagGroupSelect select : tagGroupSelects) {
    //         StringBuilder sqlBuilder = new StringBuilder();
    //         String gpColumnName = select.getGpColumnName();
    //         sqlBuilder.append(gpColumnName);
    //         sqlPart.add(sqlBuilder.toString());
    //     }
    //     return sqlPart;
    // }

    /**
     * where 中判断不为空
     * @param tagGroupSelects
     * @return
     */
    // public List<String> doWhereNotNull(List<TagGroupSelect> tagGroupSelects) {
    //     List<String> sqlPart = new ArrayList<>();
    //     for (TagGroupSelect select : tagGroupSelects) {
    //         StringBuilder sqlBuilder = new StringBuilder();
    //         String gpColumnName = select.getGpColumnName();
    //         sqlBuilder.append(gpColumnName);
    //         sqlBuilder.append(" is not null");
    //         sqlPart.add(sqlBuilder.toString());
    //     }
    //     return sqlPart;
    // }
    //
    //
    // /**
    //  * 解析group
    //  *
    //  * @param tagGroupSelects
    //  * @return
    //  */
    // public List<String> doGroup(List<TagGroupSelect> tagGroupSelects) {
    //     List<String> sqlPart = new ArrayList<>();
    //     for (TagGroupSelect select : tagGroupSelects) {
    //         StringBuilder sqlBuilder = new StringBuilder();
    //         String gpColumnName = select.getGpColumnName();
    //         sqlBuilder.append(gpColumnName);
    //         sqlPart.add(sqlBuilder.toString());
    //     }
    //     return sqlPart;
    // }
    //
    //
    // /**
    //  * 解析Having
    //  *
    //  * @param tagGroupSelects
    //  * @return
    //  */
    // public List<String> doHaving(List<TagGroupSelect> tagGroupSelects) {
    //     List<String> sqlPart = new ArrayList<>();
    //     for (TagGroupSelect select : tagGroupSelects) {
    //         Object value = select.getValue();
    //         if (ObjectUtil.isNotNull(value)) {
    //             if (value instanceof List) {
    //                 List list = (List) value;
    //                 if (CollectionUtil.isNotEmpty(list)) {
    //                     StringBuilder sqlBuilder = new StringBuilder();
    //                     sqlBuilder.append("count(");
    //                     String gpColumnName = select.getGpColumnName();
    //                     sqlBuilder.append(gpColumnName);
    //                     sqlBuilder.append(")");
    //                     sqlBuilder.append(" = ");
    //                     sqlBuilder.append(list.size());
    //                     sqlPart.add(sqlBuilder.toString());
    //                 }
    //             }
    //         }
    //     }
    //     return sqlPart;
    // }
    //
    //
    //
    //
    // /**
    //  * 解析Array判断
    //  * @param tagGroupSelects
    //  * @return
    //  */
    // public List<String> doPartOfArrayJudge(List<TagGroupSelect> tagGroupSelects) {
    //     List<String> sqlPart = new ArrayList<>();
    //     for (TagGroupSelect select : tagGroupSelects) {
    //         String value = CastUtils.cast(select.getValue());
    //         StringBuilder sqlBuilder = new StringBuilder();
    //         sqlBuilder.append(select.getGpColumnName());
    //         if ("beContainsAll".equals(value)) {
    //             sqlBuilder.append(" <@ ");
    //         } else if ("eq".equals(value)) {
    //             sqlBuilder.append(" = ");
    //         }
    //         sqlPart.add(sqlBuilder.toString());
    //     }
    //     return sqlPart;
    // }
    //
    // /**
    //  * 解析Array值，跟doPartOfArrayJudge 组合使用
    //  * @param tagGroupSelects
    //  * @return
    //  */
    // public List<String> doPartOfArray(List<TagGroupSelect> tagGroupSelects) {
    //     List<String> sqlPart = new ArrayList<>();
    //     for (TagGroupSelect select : tagGroupSelects) {
    //         StringBuilder sqlBuilder = new StringBuilder();
    //         sqlBuilder.append(" ");
    //         String gpColumnName = select.getGpColumnName();
    //         sqlBuilder.append(gpColumnName);
    //         sqlBuilder.append(" ");
    //         Object value = select.getValue();
    //         if (ObjectUtil.isNotNull(value)) {
    //             //处理数组类型的传值
    //             if (value instanceof List) {
    //                 List arrayValue = (List) value;
    //                 if (CollectionUtil.isNotEmpty(arrayValue)) {
    //                     arrayValue = (List) arrayValue.stream().distinct().collect(Collectors.toList());
    //                     sqlBuilder.append(" array[");
    //                     for (Object obj : arrayValue) {
    //                         //处理值的类型
    //                         valueConvert(obj, sqlBuilder);
    //                         if (arrayValue.indexOf(obj) != arrayValue.size() - 1) {
    //                             sqlBuilder.append(",");
    //                         }
    //                     }
    //                     sqlBuilder.append(" ]");
    //                 }
    //             }
    //             sqlPart.add(sqlBuilder.toString());
    //         }
    //     }
    //     return sqlPart;
    // }
    //
    //
    // /**
    //  * 解析having中sum 判断
    //  * @param tagGroupSelects
    //  * @return
    //  */
    // public List<String> doHavingSum(List<TagGroupSelect> tagGroupSelects) {
    //     List<String> sqlPart = new ArrayList<>();
    //     for (TagGroupSelect select : tagGroupSelects) {
    //         Object value = select.getValue();
    //         if (ObjectUtil.isNotNull(value)) {
    //             StringBuilder sqlBuilder = new StringBuilder();
    //             sqlBuilder.append("sum(");
    //             String gpColumnName = select.getGpColumnName();
    //             sqlBuilder.append(gpColumnName);
    //             sqlBuilder.append(")");
    //             sqlBuilder.append(value);
    //             sqlPart.add(sqlBuilder.toString());
    //         }
    //     }
    //     return sqlPart;
    // }
    //
    //
    // /**
    //  * 解析having中 min
    //  * @param tagGroupSelects
    //  * @return
    //  */
    // public List<String> doHavingMin(List<TagGroupSelect> tagGroupSelects) {
    //     List<String> sqlPart = new ArrayList<>();
    //     List<DateEnum> dates = new ArrayList<>(Arrays.asList(DateEnum.values()));
    //     List<String> dateCodes = dates.stream().map(DateEnum::getCode).collect(Collectors.toList());
    //     for (TagGroupSelect select : tagGroupSelects) {
    //         Object value = select.getValue();
    //         if (ObjectUtil.isNotNull(value)) {
    //             StringBuilder sqlBuilder = new StringBuilder();
    //             sqlBuilder.append("min(");
    //             String gpColumnName = select.getGpColumnName();
    //             sqlBuilder.append(gpColumnName);
    //             sqlBuilder.append(")");
    //             //判断时间
    //             if (dateCodes.contains(value)||((String) value).toLowerCase().contains(DateEnum.future.getCode())||((String) value).toLowerCase().contains(DateEnum.past.getCode())) {
    //                 value = TagDateUtil.getTime((String) value);
    //             }
    //             if (!hasFilter((String) value)) {
    //                 sqlBuilder.append(" = ");
    //             }
    //             valueConvert(value, sqlBuilder);
    //             sqlPart.add(sqlBuilder.toString());
    //         }
    //     }
    //     return sqlPart;
    // }
    //
    // /**
    //  * 解析having 中max
    //  * @param tagGroupSelects
    //  * @return
    //  */
    // public List<String> doHavingMax(List<TagGroupSelect> tagGroupSelects) {
    //     List<String> sqlPart = new ArrayList<>();
    //     List<DateEnum> dates = new ArrayList<>(Arrays.asList(DateEnum.values()));
    //     List<String> dateCodes = dates.stream().map(DateEnum::getCode).collect(Collectors.toList());
    //     for (TagGroupSelect select : tagGroupSelects) {
    //         Object value = select.getValue();
    //         if (ObjectUtil.isNotNull(value)) {
    //             StringBuilder sqlBuilder = new StringBuilder();
    //             sqlBuilder.append("max(");
    //             String gpColumnName = select.getGpColumnName();
    //             sqlBuilder.append(gpColumnName);
    //             sqlBuilder.append(")");
    //             //判断时间
    //             if (dateCodes.contains(value)||((String) value).toLowerCase().contains(DateEnum.future.getCode())||((String) value).toLowerCase().contains(DateEnum.past.getCode())) {
    //                 value = TagDateUtil.getTime((String) value);
    //             }
    //             if (!hasFilter((String) value)) {
    //                 sqlBuilder.append(" = ");
    //             }
    //             valueConvert(value, sqlBuilder);
    //             sqlPart.add(sqlBuilder.toString());
    //         }
    //     }
    //     return sqlPart;
    // }
    //
    // /**
    //  * 解析group
    //  *
    //  * @param tagGroupSelects
    //  * @return
    //  */
    // public List<String> doOn(List<TagGroupSelect> tagGroupSelects) {
    //     List<String> sqlPart = new ArrayList<>();
    //     for (TagGroupSelect select : tagGroupSelects) {
    //         StringBuilder sqlBuilder = new StringBuilder();
    //         String gpColumnName = select.getGpColumnName();
    //         sqlBuilder.append(gpColumnName);
    //         sqlPart.add(sqlBuilder.toString());
    //     }
    //     return sqlPart;
    // }
    //
    // /**
    //  * 是否不加 = 号
    //  *
    //  * @param value
    //  * @return
    //  */
    // private boolean hasFilter(String value) {
    //     List<SqlFilterEnum> sqlFilterEnums = Arrays.asList(SqlFilterEnum.values());
    //     boolean hasFilter = false;
    //     for (SqlFilterEnum filterEnum : sqlFilterEnums) {
    //         //前面加空格
    //         String newValue = " " + value.toLowerCase();
    //         if (newValue.contains(filterEnum.getCode())) {
    //             hasFilter = true;
    //             break;
    //         }
    //     }
    //     return hasFilter;
    // }
    //
    //
    // /**
    //  * 根据值得类型处理，string类型带单引号
    //  *
    //  * @param value
    //  * @param stringBuilder
    //  * @return
    //  */
    // private StringBuilder valueConvert(Object value, StringBuilder stringBuilder) {
    //     if (value instanceof String) {
    //         if (StrUtil.isNotBlank((String) value)) {
    //             if (!hasFilter((String) value)) {
    //                 stringBuilder.append("'");
    //                 stringBuilder.append(value);
    //                 stringBuilder.append("'");
    //             } else {
    //                 stringBuilder.append(value);
    //             }
    //         }
    //     } else {
    //         stringBuilder.append(value);
    //     }
    //     return stringBuilder;
    // }
}
