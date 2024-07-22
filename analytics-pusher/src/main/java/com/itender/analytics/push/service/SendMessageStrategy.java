package com.itender.analytics.push.service;

import com.itender.analytics.push.domain.entity.MessageInfo;

/**
 * @author yuanhewei
 * @date 2024/4/28 11:32
 * @description
 */
public interface SendMessageStrategy {

    void sendMessage(MessageInfo message);
}
