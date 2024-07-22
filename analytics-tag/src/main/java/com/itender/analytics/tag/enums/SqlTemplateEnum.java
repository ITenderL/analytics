package com.itender.analytics.tag.enums;

import java.util.HashMap;
import java.util.Map;


public enum SqlTemplateEnum {

    /**
     * 会员行为-做过  券  奖
     */
    VIP_DO_COUPON_PRIZE("vipParseDoCouponPrizeTemplate", "vipDoCouponPrizeTemplate"),

    /**
     * 会员行为-做过  券  奖  带条件
     */
    VIP_DO_AND_COUPON_PRIZE("vipParseDoAndCouponPrizeTemplate", "vipDoAndCouponPrizeTemplate"),

    /**
     * 会员行为-未做过  券  奖
     */
    VIP_NOT_DO_COUPON_PRIZE("vipParseNotDoCouponPrizeTemplate", "vipNotDoCouponPrizeTemplate"),

    /**
     * 会员行为-做过
     */
    VIP_DO("vipParseDoTemplate", "vipDoTemplate"),

    /**
     * 会员行为
     */
    VIP_DO_AND("vipParseDoAndTemplate", "vipDoAndTemplate"),

    /**
     * 会员行为-未做过
     */
    VIP_NOT_DO("vipParseNotDoTemplate", "vipNotDoTemplate"),

    /**
     * 会员行为
     */
    VIP_NOT_DO_PAY("vipParseNotDoPayTemplate", "vipNotDoPayTemplate"),

    /**
     * 会员行为
     */
    VIP_EVENT_DO_AND("vipParseEventDoAndTemplate", "vipEventDoAndTemplate"),

    /**
     * 首次或者末次计算时间
     */
    VIP_DO_MAX_MIN("vipParseDoMaxMinTemplate", "vipDoMaxMinTemplate"),

    /**
     * 会员行为
     */
    VIP_SYS_AGG_MAX("vipParseAggMaxSceneTemplate", "vipSysAggMaxUserTemplate"),

    /**
     * 会员行为
     */
    VIP_SYS_USER("vipParseSysUserTemplate", "vipSysUserTemplate"),

    /**
     * 用户行为
     */
    DO("parseDoTemplate", "DoTemplate"),

    /**
     * 用户行为
     */
    NOT_DO("parseNotDoTemplate", "NotDoTemplate"),
    /**
     * 用户行为
     */
    NOT_DO_PAY("parseNotDoPayTemplate", "NotDoPayTemplate"),
    /**
     * 用户行为
     */
    DO_AND("parseDoAndTemplate", "DoAndTemplate"),

    /**
     * 用户行为
     */
    SYS_USER("parseSysUserTemplate", "SysUserTemplate"),

    EVENT_DO_AND("parseEventDoAndTemplate", "EventDoAndTemplate"),
    /**
     * 首次或者末次计算时间
     */
    Do_Max_Min("parseDoMaxMinTemplate", "DoMaxMinTemplate"),


    SYS_AGG_MAX("parseAggMaxSceneTemplate", "SysAggMaxUserTemplate");

    private String code;
    public String implement;

    SqlTemplateEnum(String code, String implement) {
        this.code = code;
        this.implement = implement;
    }

    public String getCode() {
        return code;
    }

    public String getImplement() {
        return implement;
    }


    public static Map<String, String> getMap() {

        Map<String, String> map = new HashMap<>();
        for (SqlTemplateEnum sqlTemplateEnum : SqlTemplateEnum.values()) {
            String code = sqlTemplateEnum.getCode();
            map.put(code, sqlTemplateEnum.getImplement());
        }

        return map;
    }
}
