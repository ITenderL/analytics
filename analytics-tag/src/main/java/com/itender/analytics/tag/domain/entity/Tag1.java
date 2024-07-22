package com.itender.analytics.tag.domain.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Tag1 {
    /**
     * 标签id
     */
    private Integer id;

    /**
     * 标签名称
     */
    private String tagName;

    private String tagGroupName;
    private Integer tagGroupSource;


    /**
     * 标签描述
     */
    private String describe;

    /**
     * 标签组id
     */
    private Integer tagGroupId;

    /**
     * 计算表达式
     */
    private String expression;

    /**
     * 0计算中 1计算完成 2计算失败 3排队状态（等待计算）
     */
    private Integer dealStatus;

    /**
     * sql查询语句
     */
    private String sqlQuery;

    private String channelCode;

    private Integer channelLevel;

    /**
     * 覆盖人数
     */
    private Integer layerCount;

    /**
     * 人数更新时间
     */
    private Date countUpdateTime;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人id
     */
    private Long creater;

    /**
     * 创建人姓名
     */
    private String createrName;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新人id
     */
    private Long updater;

    /**
     * 更新人姓名
     */
    private String updaterName;

    /**
     * 是否删除（0：未删除，1：已删除）
     */
    private int isDeleted;

    /**
     * 扩展id（第三方唯一码）
     */
    private String tagExtendId;

    private Integer tagSource;
    /**
     * 时间偏好属性对象id
     */
    private String tagObjId;
    /**
     * 时间偏好属性对象类型 0：商品 1：品牌 2：品类 3：抛弃
     */
    private Integer tagObjType;

    /**
     * 标签id
     */
    private Integer tagId;

    /**
     * 销售平台编码
     */
    private String saleStoreCode;

    /**
     * 店铺编码
     */
    private String channelStoreCode;
}
