package com.itender.wms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 仓库表
 * </p>
 *
 * @author analytics
 * @since 2024-09-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_store")
public class Store implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "store_id", type = IdType.AUTO)
    private Integer storeId;

    private String storeName;

    private String storeNum;

    private String storeAddress;

    private String concat;

    private String phone;


}
