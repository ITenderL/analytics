package com.itender.analytics.push.enums;

import lombok.Getter;

/**
 * @author yuanhewei
 * @date 2024/4/25 11:35
 * @description
 */
@Getter
public enum AlarmFilterTypeEnum {
    MODEL("model", "机型"),
    TAG("tag", "标签"),
    TAG_TREE("tagTree", "标签树"),
    THEATER("theater", "战区"),
    BRAND("brand", "品牌"),
    KEYWORDS("keywords", "关键词"),
    FEEDBACK_ATTRIBUTES("feedbackAttributes", "反馈属性"),
    SCENARIO_KEYWORDS("scenarioKeywords", "场景关键词"),
    EMOTION("emotion", "情感"),
    ;

    /**
     * 过滤条件编码
     */
    private final String code;

    /**
     * 过滤条件名称
     */
    private final String value;

    AlarmFilterTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }
}
