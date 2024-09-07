package com.itender.wms.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author analytics
 * @since 2024-09-05
 */
@Data
@TableName("t_product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品ID
     */
    @TableId(value = "product_id", type = IdType.AUTO)
    private Integer productId;

    /**
     * 商品所在仓库ID
     */
    private Integer storeId;

    /**
     * 商品所在仓库的名称（非数据库字段，通过某种逻辑获取）
     */
    @TableField(exist = false)
    private String storeName;

    /**
     * 商品所属品牌ID
     */
    private Integer brandId;

    /**
     * 商品所属品牌的名称（非数据库字段，通过某种逻辑获取）
     */
    @TableField(exist = false)
    private String brandName;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品编码
     */
    private String productNum;

    /**
     * 商品库存数量
     */
    private Integer productInvent;

    /**
     * 商品所属分类ID
     */
    private Integer typeId;

    /**
     * 商品所属分类的名称（非数据库字段，通过某种逻辑获取）
     */
    @TableField(exist = false)
    private String typeName;

    /**
     * 商品供应商ID
     */
    private Integer supplyId;

    /**
     * 商品供应商的名称（非数据库字段，通过某种逻辑获取）
     */
    @TableField(exist = false)
    private String supplyName;

    /**
     * 商品产地ID
     */
    private Integer placeId;

    /**
     * 商品产地的名称（非数据库字段，通过某种逻辑获取）
     */
    @TableField(exist = false)
    private String placeName;

    /**
     * 商品单位ID
     */
    private Integer unitId;

    /**
     * 商品单位的名称（非数据库字段，通过某种逻辑获取）
     */
    @TableField(exist = false)
    private String unitName;

    /**
     * 商品介绍
     */
    private String introduce;

    /**
     * 商品上下架状态，1表示上架，0表示下架
     */
    private String upDownState;

    /**
     * 商品进价
     */
    private BigDecimal inPrice;

    /**
     * 商品售价
     */
    private BigDecimal salePrice;

    /**
     * 商品会员价
     */
    private BigDecimal memPrice;

    /**
     * 商品的创建时间，格式为yyyy-MM-dd HH:mm:ss
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 商品的最后修改时间，格式为yyyy-MM-dd HH:mm:ss
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 创建商品的用户ID
     */
    private Integer createBy;

    /**
     * 最后修改商品的用户ID
     */
    private Integer updateBy;

    /**
     * 商品的图片地址（可能包含多个图片，以某种格式分隔）
     */
    private String imgs;

    /**
     * 商品的生产日期，格式为yyyy-MM-dd
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date productDate;

    /**
     * 商品的保质期，格式为yyyy-MM-dd
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date suppDate;

    /**
     * 商品是否过期，0表示未过期，1表示已过期（非数据库字段，可能需要通过比较生产日期和保质期来计算）
     */
    @TableField(exist = false)
    private String isOverDate;

    /**
     * 商品所属用户ID（非数据库字段，通过某种逻辑获取）
     */
    @TableField(exist = false)
    private Integer userId;
}
