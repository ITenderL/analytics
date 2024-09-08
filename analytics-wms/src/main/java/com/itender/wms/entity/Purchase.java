package com.itender.wms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 采购单
 * </p>
 *
 * @author analytics
 * @since 2024-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_buy_list")
public class Purchase implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 采购单id
     */
    @TableId(value = "buy_id", type = IdType.AUTO)
    private Integer buyId;

    /**
     * 采购单采购的商品id
     */
    private Integer productId;

    /**
     * 采购单采购的商品所在仓库id
     */
    private Integer storeId;

    /**
     * 预计采购的商品数量
     */
    private Integer buyNum;


    /**
     * 实际采购的商品数量
     */
    private Integer factBuyNum;

    /**
     * 采购时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date buyTime;

    /**
     * 采购单采购的商品的供应商id
     */
    private Integer supplyId;

    /**
     * 采购单采购的商品的产地id
     */
    private Integer placeId;

    /**
     * 采购单采购的商品的规格id
     */
    private Integer specId;

    /**
     * 采购人
     */
    private String buyUser;

    /**
     * 采购人联系电话
     */
    private String phone;

    /**
     * 是否生成入库单,1.是,0.否
     */
    private String isIn;
}
