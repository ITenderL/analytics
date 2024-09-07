package com.itender.wms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 产地
 * </p>
 *
 * @author analytics
 * @since 2024-09-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_place")
public class Place implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 产地id
     */
    @TableId(value = "place_id", type = IdType.AUTO)
    private Integer placeId;

    /**
     * 产地名称
     */
    private String placeName;

    /**
     * 产地代码
     */
    private String placeNum;

    /**
     * 产地介绍
     */
    private String introduce;

    /**
     * 是否删除状态,0未删除,1删除
     */
    private String isDelete;
}
