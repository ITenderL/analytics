package com.itender.wms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 供货商
 * </p>
 *
 * @author analytics
 * @since 2024-09-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_supply")
public class Supply implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 供应商id
     */
    @TableId(value = "supply_id", type = IdType.AUTO)
    private Integer supplyId;

    /**
     * 供应商代码
     */
    private String supplyNum;

    /**
     * 供应商名称
     */
    private String supplyName;

    /**
     * 供应商介绍
     */
    private String supplyIntroduce;

    /**
     * 供应商联系人
     */
    private String concat;

    /**
     * 供应商联系电话
     */
    private String phone;

    /**
     * 供应商地址
     */
    private String address;

    /**
     * 是否删除状态,0未删除,1删除
     */
    private String isDelete;
}
