package com.itender.wms.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 入库单
 * </p>
 *
 * @author analytics
 * @since 2024-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_in_store")
public class InStore implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 进货单id
     */
    @TableId(value = "ins_id", type = IdType.AUTO)
    private Integer insId;

    /**
     * 仓库id
     */
    private Integer storeId;

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * 进货数量
     */
    private Integer inNum;

    /**
     * 创建人id
     */
    private Integer createBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 0 否 1 是,是否入库
     */
    private String isIn;

    /**
     * 关联的采购单id
     */
    @TableField(exist = false)
    private Integer buyId;

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

    /**
     * 进货单价
     */
    @TableField(exist = false)
    private BigDecimal inPrice;
}
