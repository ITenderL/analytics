package com.itender.analytics.push.service.impl;

import com.itender.analytics.push.domain.entity.MessageInfo;
import com.itender.analytics.push.service.SendMessageStrategy;
import org.springframework.stereotype.Service;

/**
 * @author yuanhewei
 * @date 2024/4/28 11:37
 * @description
 */
@Service("mobile")
public class SendMobileImpl implements SendMessageStrategy {
    @Override
    public void sendMessage(MessageInfo message) {

    }
}
