package com.itender.system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author analytics
 * @date 2024/9/4 9:45
 * @description
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RolePermissionDTO {

    /**
     * 角色编号
     */
    private Long roleId;

    /**
     * 权限编号集合
     */
    private List<Long> list;
}
