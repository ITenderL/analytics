package com.itender.system.vo.query;


import com.itender.system.entity.Role;
import lombok.Data;

@Data
public class RoleQueryVO extends Role {
    /**
     *  当前页码
     */
    private Long pageNo = 1L;

    /**
     *  每页显示数量
     */
    private Long pageSize = 10L;

    /**
     *  当前登录用户ID
     */
    private Long userId;
}
