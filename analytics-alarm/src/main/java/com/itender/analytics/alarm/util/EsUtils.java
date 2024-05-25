package com.itender.analytics.alarm.util;

import cn.hutool.core.util.ArrayUtil;
import com.itender.analytics.alarm.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.Objects;

import static com.itender.analytics.alarm.enums.BizExceptionEnum.ELASTICSEARCH_EXECUTE_QUERY_ERROR;

/**
 * @author yuanhewei
 * @date 2024/5/11 15:48
 * @description
 */
@Slf4j
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
        SearchRequest searchRequest = new SearchRequest(indexName);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 过滤条件
        sourceBuilder.query(queryBuilder);
        // 分页参数-文档开始位置
        if (Objects.nonNull(from)) {
            sourceBuilder.from(from);
        }
        // 分页参数-每页的文档数
        if (Objects.nonNull(size)) {
            sourceBuilder.size(size);
        }
        // 聚合
        if (Objects.nonNull(aggregationBuilder)) {
            sourceBuilder.aggregation(aggregationBuilder);
        }
        // 返回字段，如果为空则返回全部字段
        if (ArrayUtil.isNotEmpty(includes)) {
            sourceBuilder.fetchSource(includes, Strings.EMPTY_ARRAY);
        }
        // 设置查询条件
        searchRequest.source(sourceBuilder);
        return searchRequest;
    }

    /**
     * Elasticsearch执行查询，获取SearchResponse
     *
     * @param searchRequest
     * @param restHighLevelClient
     * @return
     */
    public static SearchResponse getSearchResponse(SearchRequest searchRequest, RestHighLevelClient restHighLevelClient) {
        try {
            return restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("getSearchResponse error! stackTrace: {}, searchRequest: {}", ExceptionUtils.getStackTrace(e), searchRequest.toString());
            throw new BizException(ELASTICSEARCH_EXECUTE_QUERY_ERROR);
        }
    }


    /**
     * 切换索引别名
     *
     * @param restHighLevelClient
     * @param currentIndex
     * @param newIndex
     * @param indexAlia
     */
    public static void switchIndexAlias(RestHighLevelClient restHighLevelClient, String currentIndex, String newIndex, String indexAlia) {
        try {
            // 1. 添加新索引的别名
            IndicesAliasesRequest aliasRequest = new IndicesAliasesRequest();
            aliasRequest.addAliasAction(IndicesAliasesRequest.AliasActions.add().index(newIndex).alias(indexAlia));
            // 2. 移除旧索引的别名（如果需要）
            aliasRequest.addAliasAction(IndicesAliasesRequest.AliasActions.remove().index(currentIndex).alias(indexAlia));
            // 3. 执行别名切换
            AcknowledgedResponse acknowledgedResponse = restHighLevelClient.indices().updateAliases(aliasRequest, RequestOptions.DEFAULT);
            log.info("currentIndex: {}, newIndex: {}, indexAlia: {} switchIndexAlias is {}",
                    currentIndex, newIndex, indexAlia, acknowledgedResponse.isAcknowledged());
        } catch (Exception e) {
            log.error("switchIndexAlias error!, currentIndex: {}, newIndex: {}, indexAlia: {}, error: {}",
                    currentIndex, newIndex, indexAlia, ExceptionUtils.getStackTrace(e));
        }
    }

    private EsUtils() {
    }
}
