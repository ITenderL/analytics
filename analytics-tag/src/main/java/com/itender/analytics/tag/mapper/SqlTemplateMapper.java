package com.itender.analytics.tag.mapper;



import java.util.Map;

public interface SqlTemplateMapper {

    /**
     * 交集
     *
     * @param sqlLeft
     * @param sqlRight
     * @return
     */
    String intersect(String sqlLeft, String sqlRight);

    /**
     * 并集
     *
     * @param sqlLeft
     * @param sqlRight
     * @return
     */
    String union(String sqlLeft, String sqlRight);

    /**
     * 差集
     *
     * @param sqlLeft
     * @param sqlRight
     * @return
     */
    String minus(String sqlLeft, String sqlRight);

    /**
     * 会员行为满足-做过 券 奖-无行为对象
     *
     * @param map
     * @return
     */
    String vipParseDoCouponPrizeTemplate(Map<String, Object> map);


    /**
     * 会员行为满足-做过 券 奖-无行为对象
     *
     * @param map
     * @return
     */
    String vipParseNotDoCouponPrizeTemplate(Map<String, Object> map);


    /**
     * 会员行为满足-做过 券 奖- 有行为对象
     *
     * @param map
     * @return
     */
    String vipParseDoAndCouponPrizeTemplate(Map<String, Object> map);

    /**
     * 系统标签
     *
     * @param map
     * @return
     */
    String vipParseSysUserTemplate(Map<String, Object> map);


    /**
     * 常用
     * @param map
     * @return
     */
    String vipParseAggMaxSceneTemplate(Map<String, Object> map);

    /**
     * 末次
     * @param map
     * @return
     */
    String vipParseMaxSceneTemplate(Map<String, Object> map);

    /**
     * 首次
     * @param map
     * @return
     */
    String vipParseMinSceneTemplate(Map<String, Object> map);


    /**
     * 会员-事件偏好属性
     *
     * @param map
     * @return
     */
    String vipParseEventDoAndTemplate(Map<String, Object> map);

    /**
     * 会员行为满足-做过-无行为对象
     *
     * @param map
     * @return
     */
    String vipParseDoTemplate(Map<String, Object> map);

    /**
     * 会员行为满足-做过-有行为对象-行为对象需全满足（且）
     *
     * @param map
     * @return
     */
    String vipParseDoAndTemplate(Map<String, Object> map);

    /**
     * 会员行为满足-未做过
     *
     * @param map
     * @return
     */
    String vipParseNotDoTemplate(Map<String, Object> map);

    /**
     * 会员行为满足-未做过支付
     *
     * @param map
     * @return
     */
    String vipParseNotDoPayTemplate(Map<String, Object> map);

    /**
     * 用户行为满足-做过-无行为对象
     *
     * @param map
     * @return
     */
    String parseDoTemplate(Map<String, Object> map);

    /**
     * 用户行为满足-做过-有行为对象-行为对象需全满足（且）
     *
     * @param map
     * @return
     */
    String parseDoAndTemplate(Map<String, Object> map);

    /**
     * 用户行为满足-未做过
     *
     * @param map
     * @return
     */
    String parseNotDoTemplate(Map<String, Object> map);


    /**
     * 未做过支付
     * @param map
     * @return
     */
    String parseNotDoPayTemplate(Map<String, Object> map);

    /**
     * 系统标签
     *
     * @param map
     * @return
     */
    String parseSysUserTemplate(Map<String, Object> map);

    /**
     * 事件偏好属性
     *
     * @param map
     * @return
     */
    String parseEventDoAndTemplate(Map<String, Object> map);


    /**
     * 常用
     * @param map
     * @return
     */
    String parseAggMaxSceneTemplate(Map<String, Object> map);

    /**
     * 末次
     * @param map
     * @return
     */
    String parseMaxSceneTemplate(Map<String, Object> map);

    /**
     * 首次
     * @param map
     * @return
     */
    String parseMinSceneTemplate(Map<String, Object> map);



    /**
     * 获取人群sql
     * @param channelLimit 限制渠道
     * @param prCode 一级渠道
     * @param storeCode 店铺
     * @param table 表（如果是一级渠道，需要有粉丝数据）
     * @param sql 圈人sql
     */
    // void getCrowdChannelLimit(ChannelLimit channelLimit, String prCode, String storeCode, String table, String sql);
}
