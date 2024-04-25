package com.itender.analytics.tag.enums;

public enum ExpressionTypeEnum {
    /**
     * 公式
     */
    SYMBOL("symbol"),
    /**
     * 标签
     */
    TAG_GROUP("tagGroup"),
    ;

    private String type;

    public String getType() {
        return type;
    }

    ExpressionTypeEnum(String type) {
        this.type = type;
    }
}
