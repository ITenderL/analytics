package com.itender.analytics.search.util;

import cn.hutool.core.util.ArrayUtil;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.common.Strings;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.Objects;

/**
 * @author yuanhewei
 * @date 2024/5/11 15:48
 * @description
 */
public class EsUtils {

    /**
     * 获取并设置ES请求参数SearchRequest
     *
     * @param indexName          索引名称
     * @param queryBuilder       查询条件
     * @param aggregationBuilder 聚合条件
     * @param includes           返回字段数组
     * @param from               分页参数，从多少开始
     * @param size               分页参数，查询条数
     * @return
     */
    public static SearchRequest getSearchRequest(String indexName,
                                                 BoolQueryBuilder queryBuilder,
                                                 AggregationBuilder aggregationBuilder,
                                                 String[] includes,
                                                 Integer from,
                                                 Integer size) {
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(queryBuilder);
        if (Objects.nonNull(from)) {
            sourceBuilder.from(from);
        }
        if (Objects.nonNull(size)) {
            sourceBuilder.size(size);
        }
        if (Objects.nonNull(aggregationBuilder)) {
            sourceBuilder.aggregation(aggregationBuilder);
        }
        if (ArrayUtil.isNotEmpty(includes)) {
            sourceBuilder.fetchSource(includes, Strings.EMPTY_ARRAY);
        }
        searchRequest
                .indices(indexName)
                .source(sourceBuilder);
        return searchRequest;
    }

    private EsUtils() {
    }
}
