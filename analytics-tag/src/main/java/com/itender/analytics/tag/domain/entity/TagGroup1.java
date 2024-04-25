package com.itender.analytics.tag.domain.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TagGroup1 {

    /**
     * 标签组id
     */
    private Integer id;

    /**
     * 标签组名称（渠道下唯一）
     */
    private String tagGroupName;


    /**
     * 是否初始化数据（系统创建标签）
     */
    private Integer isInitData;

    /**
     * 标签组分类id
     */
    private Integer tagGroupCateId;

    /**
     * 标签组来源（0：系统，1：自定义）
     */
    private Integer tagGroupSource;

    /**
     * 创建方式（0：系统创建，1：自定义创建，2：导入创建）
     */
    private Integer createWay;

    /**
     * 自定义类型（0：无，1：自定义标签值，2：事件偏好属性）
     */
    private Integer customType;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 备注
     */
    private String remark;

    /**
     * 表达式
     */
    private String expression;

    /**
     * 覆盖人数（汇总分层数据）
     */
    private Integer layerCount;

    /**
     * 人数更新时间
     */
    private Date countUpdateTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人id
     */
    private Long creator;

    /**
     * 创建人姓名
     */
    private String creatorName;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 配置模板Code
     */
    private Integer attributeModelType;

    /**
     * 更新人id
     */
    private Long updater;

    /**
     * 0计算中 1计算完成 2计算失败
     */
    private Integer dealStatus;

    /**
     * 更新人姓名
     */
    private String updaterName;

    /**
     * 是否删除（0：未删除，1：已删除）
     */
    private Integer isDeleted;

    /**
     * 扩展id（第三方唯一码）
     */
    private String tagGroupExtendId;

    /**
     * 第三方来源下级渠道
     */
    private String channelCode;

    /**
     * 标签详情状态
     */
    private String tagDetailStatus;

    /**
     * 数据范围（业务线或者全渠道）
     */
    private String dataRange;

    /**
     * 是否不限制行为对象
     */
    private Integer isNotLimit;

    /**
     * 入参，行为对象不限时需要
     */
    private String eventParam;

    /**
     * 限制渠道 1：单渠道单店铺
     */
    private Integer limitDataRange;
}
