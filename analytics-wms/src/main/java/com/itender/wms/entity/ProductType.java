package com.itender.wms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 商品分类表
 * </p>
 *
 * @author analytics
 * @since 2024-09-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_product_type")
public class ProductType implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 分类id
     */
    @TableId(value = "type_id", type = IdType.AUTO)
    private Integer typeId;

    /**
     * 上级分类id
     */
    private Integer parentId;

    /**
     * 分类代码
     */
    private String typeCode;

    /**
     * 分类名称
     */
    private String typeName;

    /**
     * 分类描述
     */
    private String typeDesc;

    /**
     * 自定义List<ProductType>集合属性,用于存储当前分类的所有子级分类
     */
    @TableField(exist = false)
    private List<ProductType> childProductCategory;
}
