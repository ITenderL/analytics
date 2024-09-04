package com.itender.system.vo.query;


import com.itender.system.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryVO extends User {
    /**
     * 当前页码
     */
    private Long pageNo = 1L;

    /**
     * 每页显示数量，默认10
     */
    private Long pageSize = 10L;
}
