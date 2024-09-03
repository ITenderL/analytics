package com.itender.system.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author analytics
 * @date 2024/9/2 10:14
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenVO {
    //过期时间
    private Long expireTime;
    //token
    private String token;
}
