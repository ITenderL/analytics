package com.itender.analytics.tag.service;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.itender.analytics.tag.domain.TagGroupExpressionRequest;
import com.itender.analytics.tag.domain.TagGroupSelect;
import com.itender.analytics.tag.domain.dto.TemplateTableMapping;
import com.itender.analytics.tag.exception.BizException;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


public abstract class TemplateService {

    @Resource
    // private TagGroupCustomMapper tagGroupCustomMapper;

    /**
     * 解析模板
     *
     * @param expressionRequest
     * @return
     */

    public abstract String doParseTemplate(TagGroupExpressionRequest expressionRequest);


    /**
     * @param tables
     * @return
     */
    public Map<String, String> matchTable(List<String> tables, String templateCode) {
        //解析表
        List<String> tb = tables.stream().filter(StrUtil::isNotBlank).distinct().collect(Collectors.toList());
        // List<TemplateTableMapping> templateTableMapping = tagGroupCustomMapper.getTemplateTableMapping(templateCode);
        List<TemplateTableMapping> templateTableMapping = new ArrayList<>();
        if (CollUtil.isEmpty(templateTableMapping)) {
            throw new BizException(500, "无法匹配查询表");
        }
        //根据模板位置分组
        Map<String, List<TemplateTableMapping>> tableLocationMap = templateTableMapping.stream().collect(Collectors.groupingBy(TemplateTableMapping::getTableLocation));
        Map<String, String> tableMap = new HashMap<String, String>(16);
        for (String key : tableLocationMap.keySet()) {
            //获取此区位组成
            List<TemplateTableMapping> templateTableMappings = tableLocationMap.get(key);
            //根据优先级排序
            List<TemplateTableMapping> priority = templateTableMappings.stream().sorted(Comparator.comparing(TemplateTableMapping::getPriority)).collect(Collectors.toList());
            //根据优先级匹配
            for (TemplateTableMapping tableMapping : priority) {
                //组成
                List<String> listPart = JSON.parseArray(tableMapping.getTablePart(), String.class);
                //匹配
                if (tb.containsAll(listPart) && tb.size() == listPart.size()) {
                    tableMap.put(key, tableMapping.getActualTable());
                    break;
                }
            }
        }

        //获取此模板有几处表替换处
        if (CollectionUtil.isEmpty(tableMap) && tableLocationMap.size() != tableMap.size()) {
            throw new BizException(500, "匹配查询表出错");
        }
        return tableMap;
    }


    /**
     * 单独处理客单价
     */
    public void dealUnitPrice(TagGroupSelect select, Map<String, Object> map) {
        String gpColumnName = select.getGpColumnName();
        String[] unitPriceCol = gpColumnName.split("/");
        //查询
        if (ArrayUtil.isNotEmpty(unitPriceCol)) {
            for (String key : map.keySet()) {
                if ("selectsum_1".equals(key)) {
                    Object o = map.get(key);
                    List<String> sums = (List<String>) o;
                    String sumSql = "(CASE WHEN rb_or_cardinality_agg(" + unitPriceCol[1] + ")=0 THEN 0 ELSE sum(" + unitPriceCol[0] + ")/rb_or_cardinality_agg(" + unitPriceCol[1] + ") END) as " + select.getAttributeCode();
                    sums.add(sumSql);
                }
            }
        }

        for (String key : map.keySet()) {
            if ("where_2".equals(key)) {
                List<String> list = (List<String>) map.get(key);
                List<String> newList = new ArrayList<>();
                for (String value : list) {
                    if (value.contains("/")) {
                        String unitPrice = value.replace(unitPriceCol[0] + "/" + unitPriceCol[1], select.getAttributeCode());
                        newList.add(unitPrice);
                    } else {
                        newList.add(value);
                    }
                }
                map.put("where_2", newList);
            }
        }
    }

    // /**
    //  * 处理topN
    //  *
    //  * @param selects
    //  * @param stringObjectMap
    //  */
    // public void doTop(List<TagGroupSelect> selects, Map<String, Object> stringObjectMap) {
    //     for (TagGroupSelect select : selects) {
    //         if (select.getValue() != null && select.getValue() instanceof String && select.getValue().toString().contains("top")) {
    //             Object group = stringObjectMap.get("group_1");
    //             if (Objects.isNull(group)) {
    //                 stringObjectMap.put("group_1", new ArrayList<>(Arrays.asList(select.getGpColumnName())));
    //             } else {
    //                 List<String> list = (List) group;
    //                 list.add(select.getGpColumnName());
    //             }
    //         }
    //     }
    // }


    public void dealRelation(List<TagGroupSelect> selects, Map<String, Object> stringObjectMap) {
        List<TagGroupSelect> relations = selects.stream().filter(s -> StrUtil.isNotBlank(s.getRelation())).collect(Collectors.toList());
        if (CollUtil.isEmpty(relations)) {
            return;
        }
        for (TagGroupSelect relation : relations) {
            String relationField = relation.getRelation();
            String sqlLocation = relation.getSqlLocation();
            if (CharSequenceUtil.isBlank(sqlLocation)) {
                continue;
            }
            List<String> locations = Arrays.asList(sqlLocation.split("\\|"));
            locations.forEach(l -> {
                if (l.contains("where")) {
                    stringObjectMap.put(l + "_relation", relationField);
                }
            });
        }
    }
}
