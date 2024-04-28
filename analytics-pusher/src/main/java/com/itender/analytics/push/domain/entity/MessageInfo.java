package com.itender.analytics.push.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yuanhewei
 * @date 2024/4/28 11:33
 * @description
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageInfo {

    /**
     * 消息id
     */
    private String id;

    /**
     * 消息标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;
}
