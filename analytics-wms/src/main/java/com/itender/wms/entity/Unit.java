package com.itender.wms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 规格单位表
 * </p>
 *
 * @author analytics
 * @since 2024-09-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_unit")
public class Unit implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "unit_id", type = IdType.AUTO)
    private Integer unitId;

    /**
     * 单位名称
     */
    private String unitName;

    /**
     * 单位描述
     */
    private String unitDesc;
}
