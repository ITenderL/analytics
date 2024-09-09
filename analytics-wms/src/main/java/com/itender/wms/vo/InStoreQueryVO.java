package com.itender.wms.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.itender.wms.entity.InStore;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author analytics
 * @date 2024/9/8 19:22
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InStoreQueryVO extends InStore {

    /**
     * 当前页码
     */
    private Integer pageNo = 1;

    /**
     * 每页显示数量
     */
    private Integer pageSize = 10;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String endTime;
}
