package com.itender.wms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 品牌
 * </p>
 *
 * @author analytics
 * @since 2024-09-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_brand")
public class Brand implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 品牌id
     */
    @TableId(value = "brand_id", type = IdType.AUTO)
    private Integer brandId;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 品牌首字母
     */
    private String brandLeter;

    /**
     * 品牌描述
     */
    private String brandDesc;
}
