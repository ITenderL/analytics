package com.itender.system.dto;

import lombok.Data;

import java.util.List;

/**
 * 用于给用户分配角色时保存选中的角色数据
 */
@Data
public class UserRoleDTO {

    /**
     * 用户id
     **/
    private Long userId;

    /**
     * 角色id列表
     **/
    private List<Long> roleIds;
}