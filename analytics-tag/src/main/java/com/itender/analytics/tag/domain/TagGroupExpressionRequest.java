package com.itender.analytics.tag.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


@Data
public class TagGroupExpressionRequest implements Serializable {
    private Integer channelLevel;
    private String channelCode;
    private String type;
    private String symbol;
    private Integer tagTypeId;
    private Integer tagGroupId;
    private Integer tagGroupSource;
    private List<TagGroupSelect> selects;
    private Map<String, Object> calLogicObj;
    /**
     * 数据范围（业务线或者全渠道）
     */
    private String dataRange;

    /**
     * 标签组的数据范围-tagGroupDataRange，优先级高于dataRange
     */
    private String tagGroupDataRange;

    private String dataRangeCrowdFlagInfo;

    /**
     * 标签数据范围条件信息
     */
    private String dataRangeTagFlagInfo;

    /**
     * 平台编码
     */
    private String saleStoreCode;

    /**
     * 店铺编码
     */
    private String channelStoreCode;

    /**
     * 是否是标签维度查询 true: 是 false：否
     */
    private Boolean tagDimensionFlag;

    /**
     * 全局标签类型：0或者null：oneId标签 1：会员标签  2：粉丝标签
     * 历史的可能存在null的，也归为原有的标签
     */
    private Integer globalTagType;
}
