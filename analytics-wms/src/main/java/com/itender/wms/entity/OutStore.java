package com.itender.wms.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 出库单
 * </p>
 *
 * @author analytics
 * @since 2024-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_out_store")
public class OutStore implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 出库单id
     */
    @TableId(value = "outs_id", type = IdType.AUTO)
    private Integer outsId;

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * 仓库id
     */
    private Integer storeId;

    /**
     * 理货员id
     */
    private Integer tallyId;

    /**
     * 出库价格
     */
    private BigDecimal outPrice;

    /**
     * 出库数量
     */
    private Integer outNum;

    /**
     * 创建人
     */
    private Integer createBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 0 否 1 是
     */
    private String isOut;

    /**
     * 商品的售价，作为出库单的出库价格outPrice
     */
    @TableField(exist = false)
    private BigDecimal salePrice;

    /**
     * 仓库名称
     */
    @TableField(exist = false)
    private String storeName;

    /**
     * 商品名称
     */
    @TableField(exist = false)
    private String productName;
}
