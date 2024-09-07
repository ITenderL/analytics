package com.itender.wms.vo;

import com.itender.wms.entity.Product;
import lombok.*;

/**
 * @author analytics
 * @date 2024/9/5 17:15
 * @description
 */

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class ProductQueryVO extends Product {
    /**
     * 当前页码
     */
    private Long pageNo = 1L;

    /**
     * 每页显示数量
     */
    private Long pageSize = 10L;

    /**
     * 当前登录用户ID
     */
    private Integer userId;
}
