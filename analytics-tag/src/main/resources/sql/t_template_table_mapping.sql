/*
 Navicat Premium Data Transfer

 Source Server         : idata-mysql-test
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : idata-ptag-mysql-master-fh-t.seeke.net:3306
 Source Schema         : ptag

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 20/04/2024 15:19:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_template_table_mapping
-- ----------------------------
DROP TABLE IF EXISTS `t_template_table_mapping`;
CREATE TABLE `t_template_table_mapping`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `template_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板code',
  `table_location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '位置',
  `table_part` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表组成',
  `actual_table` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '实际的表',
  `priority` smallint(6) NULL DEFAULT NULL COMMENT '优先级 一级最高',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10112 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '模板内表映射' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_template_table_mapping
-- ----------------------------
INSERT INTO `t_template_table_mapping` VALUES (3, 'parseDoAndTemplate', 'table_1', '[\"pay\",\"product\"]', 'ads_rb_user_gds_cs_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (4, 'parseDoAndTemplate', 'table_1', '[\"pv\",\"product\"]', 'dws_log_user_egds_pv_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (6, 'parseDoAndTemplate', 'table_1', '[\"addCart\",\"product\"]', 'dws_ord_user_egds_spt_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (7, 'parseDoAndTemplate', 'table_1', '[\"collect\",\"product\"]', 'dws_irc_user_egds_clt_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (8, 'parseEventDoAndTemplate', 'table_1', '[\"pay\",\"product\"]', 'ads_rb_user_gds_cs_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (9, 'parseEventDoAndTemplate', 'table_1', '[\"pv\",\"product\"]', 'dws_log_user_egds_pv_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (10, 'parseEventDoAndTemplate', 'table_1', '[\"addCart\",\"product\"]', 'dws_ord_user_egds_spt_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (11, 'parseEventDoAndTemplate', 'table_1', '[\"collect\",\"product\"]', 'dws_irc_user_egds_clt_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (12, 'parseDoAndTemplate', 'table_1', '[\"pv\",\"brand\"]', 'dws_log_user_egds_pv_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (13, 'parseSysUserTemplate', 'table_1', '[\"SigoWeb\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (14, 'parseSysUserTemplate', 'table_1', '[\"YJH\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (15, 'parseSysUserTemplate', 'table_1', '[\"YYG\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (19, 'parseSysUserTemplate', 'table_1', '[\"SigoYao\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (24, 'parseSysUserTemplate', 'table_1', '[\"SigoWeb\",\"fans_register\"]', 'ads_chnl_fans_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (25, 'parseSysUserTemplate', 'table_1', '[\"YJH\",\"fans_register\"]', 'ads_chnl_fans_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (26, 'parseSysUserTemplate', 'table_1', '[\"YYG\",\"fans_register\"]', 'ads_chnl_fans_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (32, 'parseDoAndTemplate', 'table_1', '[\"pay\",\"brand\"]', 'ads_rb_user_gds_cs_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (33, 'parseDoAndTemplate', 'table_1', '[\"pay\",\"cycle\"]', 'ads_rb_user_gds_cs_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (34, 'parseDoAndTemplate', 'table_1', '[\"pay\",\"color\"]', 'ads_rb_user_gds_cs_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (35, 'parseEventDoAndTemplate', 'table_1', '[\"pay\",\"brand\"]', 'ads_rb_user_gds_cs_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (36, 'parseEventDoAndTemplate', 'table_1', '[\"pay\",\"cycle\"]', 'ads_rb_user_gds_cs_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (37, 'parseEventDoAndTemplate', 'table_1', '[\"pay\",\"cate\"]', 'ads_rb_user_gds_cs_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (38, 'parseEventDoAndTemplate', 'table_1', '[\"addCart\",\"brand\"]', 'dws_ord_user_egds_spt_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (39, 'parseEventDoAndTemplate', 'table_1', '[\"addCart\",\"cycle\"]', 'dws_ord_user_egds_spt_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (40, 'parseEventDoAndTemplate', 'table_1', '[\"addCart\",\"cate\"]', 'dws_ord_user_egds_spt_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (41, 'parseEventDoAndTemplate', 'table_1', '[\"pv\",\"brand\"]', 'dws_log_user_egds_pv_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (42, 'parseEventDoAndTemplate', 'table_1', '[\"pv\",\"cycle\"]', 'dws_log_user_egds_pv_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (43, 'parseEventDoAndTemplate', 'table_1', '[\"pv\",\"cate\"]', 'dws_log_user_egds_pv_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (44, 'parseEventDoAndTemplate', 'table_1', '[\"collect\",\"brand\"]', 'dws_irc_user_egds_clt_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (45, 'parseEventDoAndTemplate', 'table_1', '[\"collect\",\"cycle\"]', 'dws_irc_user_egds_clt_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (46, 'parseEventDoAndTemplate', 'table_1', '[\"collect\",\"cate\"]', 'dws_irc_user_egds_clt_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (47, 'parseDoAndTemplate', 'table_1', '[\"addCart\",\"brand\"]', 'dws_ord_user_egds_spt_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (48, 'parseDoAndTemplate', 'table_1', '[\"addCart\",\"cycle\"]', 'dws_ord_user_egds_spt_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (49, 'parseDoAndTemplate', 'table_1', '[\"addCart\",\"color\"]', 'dws_ord_user_egds_spt_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (50, 'parseDoAndTemplate', 'table_1', '[\"collect\",\"brand\"]', 'dws_irc_user_egds_clt_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (51, 'parseDoAndTemplate', 'table_1', '[\"collect\",\"cycle\"]', 'dws_irc_user_egds_clt_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (52, 'parseDoAndTemplate', 'table_1', '[\"collect\",\"color\"]', 'dws_irc_user_egds_clt_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (53, 'parseDoAndTemplate', 'table_1', '[\"pv\",\"cycle\"]', 'dws_log_user_egds_pv_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (54, 'parseDoAndTemplate', 'table_1', '[\"pv\",\"color\"]', 'dws_log_user_egds_pv_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (55, 'parseSysUserTemplate', 'table_1', '[\"SigoYao\",\"fans_register\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (56, 'parseNotDoTemplate', 'table_1', '[\"SigoWeb\",\"pay\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (57, 'parseNotDoTemplate', 'table_1', '[\"YYG\",\"pay\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (58, 'parseNotDoTemplate', 'table_1', '[\"YJH\",\"pay\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (59, 'parseNotDoTemplate', 'table_1', '[\"SigoYao\",\"pay\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (60, 'parseDoAndTemplate', 'table_1', '[\"pay\",\"cate\"]', 'ads_rb_user_gds_cs_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (69, 'parseNotDoTemplate', 'table_1', '[\"SigoWeb\",\"addCart\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (70, 'parseNotDoTemplate', 'table_1', '[\"YYG\",\"addCart\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (71, 'parseNotDoTemplate', 'table_1', '[\"YJH\",\"addCart\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (73, 'parseNotDoTemplate', 'table_1', '[\"SigoWeb\",\"collect\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (74, 'parseNotDoTemplate', 'table_1', '[\"YYG\",\"collect\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (75, 'parseNotDoTemplate', 'table_1', '[\"YJH\",\"collect\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (77, 'parseNotDoTemplate', 'table_1', '[\"SigoWeb\",\"pv\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (78, 'parseNotDoTemplate', 'table_1', '[\"YYG\",\"pv\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (79, 'parseNotDoTemplate', 'table_1', '[\"YJH\",\"pv\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (80, 'parseNotDoTemplate', 'table_1', '[\"SigoYao\",\"pv\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (82, 'parseNotDoTemplate', 'table_1', '[\"YYG\",\"view\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (83, 'parseNotDoTemplate', 'table_1', '[\"YJH\",\"view\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (85, 'parseNotDoTemplate', 'table_1', '[\"SigoWeb\",\"view\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (86, 'parseNotDoTemplate', 'table_1', '[\"AllChannel\",\"pay\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (87, 'parseNotDoTemplate', 'table_1', '[\"AllChannel\",\"addCart\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (88, 'parseNotDoTemplate', 'table_1', '[\"AllChannel\",\"collect\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (89, 'parseNotDoTemplate', 'table_1', '[\"AllChannel\",\"pv\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (90, 'parseNotDoTemplate', 'table_1', '[\"AllChannel\",\"view\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (91, 'parseDoAndTemplate', 'table_1', '[\"pay\"]', 'ads_rb_user_gds_cs_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (92, 'parseAggMaxSceneTemplate', 'table_1', '[\"city\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (93, 'parseSysUserTemplate', 'table_1', '[\"SigoWeb\",\"channel_register\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (94, 'parseSysUserTemplate', 'table_1', '[\"YJH\",\"channel_register\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (95, 'parseSysUserTemplate', 'table_1', '[\"YYG\",\"channel_register\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (97, 'parseSysUserTemplate', 'table_1', '[\"SigoWeb\",\"city\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (98, 'parseSysUserTemplate', 'table_1', '[\"YJH\",\"city\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (99, 'parseSysUserTemplate', 'table_1', '[\"YYG\",\"city\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (100, 'parseSysUserTemplate', 'table_1', '[\"SigoYao\",\"city\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (103, 'parseSysUserTemplate', 'table_1', '[\"SigoWeb\",\"reForecast\"]', 'ads_user_prmry_repay_forecast_date', 1);
INSERT INTO `t_template_table_mapping` VALUES (104, 'parseSysUserTemplate', 'table_1', '[\"YJH\",\"reForecast\"]', 'ads_user_prmry_repay_forecast_date', 1);
INSERT INTO `t_template_table_mapping` VALUES (105, 'parseSysUserTemplate', 'table_1', '[\"YYG\",\"reForecast\"]', 'ads_user_prmry_repay_forecast_date', 1);
INSERT INTO `t_template_table_mapping` VALUES (106, 'parseSysUserTemplate', 'table_1', '[\"SigoYao\",\"reForecast\"]', 'ads_user_prmry_repay_forecast_date', 1);
INSERT INTO `t_template_table_mapping` VALUES (107, 'parseDoAndTemplate', 'table_1', '[\"addCart\",\"cate\"]', 'dws_ord_user_egds_spt_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (108, 'parseDoAndTemplate', 'table_1', '[\"collect\",\"cate\"]', 'dws_irc_user_egds_clt_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (110, 'parseDoAndTemplate', 'table_1', '[\"pv\",\"cate\"]', 'dws_log_user_egds_pv_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (111, 'parseSysUserTemplate', 'table_1', '[\"prmry\",\"SigoWeb\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (112, 'parseSysUserTemplate', 'table_1', '[\"prmry\",\"YJH\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (113, 'parseSysUserTemplate', 'table_1', '[\"prmry\",\"YYG\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (114, 'parseSysUserTemplate', 'table_1', '[\"prmry\",\"SigoYao\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (115, 'parseSysUserTemplate', 'table_1', '[\"prmry\",\"SigoWeb\",\"fans_register\"]', 'ads_chnl_fans_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (116, 'parseSysUserTemplate', 'table_1', '[\"prmry\",\"YJH\",\"fans_register\"]', 'ads_chnl_fans_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (117, 'parseSysUserTemplate', 'table_1', '[\"prmry\",\"YYG\",\"fans_register\"]', 'ads_chnl_fans_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (118, 'parseSysUserTemplate', 'table_1', '[\"prmry\",\"SigoYao\",\"fans_register\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (119, 'parseSysUserTemplate', 'table_1', '[\"prmry\",\"SigoWeb\",\"channel_register\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (120, 'parseSysUserTemplate', 'table_1', '[\"prmry\",\"YJH\",\"channel_register\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (121, 'parseSysUserTemplate', 'table_1', '[\"prmry\",\"YYG\",\"channel_register\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (122, 'parseSysUserTemplate', 'table_1', '[\"prmry\",\"SigoWeb\",\"city\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (123, 'parseSysUserTemplate', 'table_1', '[\"prmry\",\"YJH\",\"city\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (124, 'parseSysUserTemplate', 'table_1', '[\"prmry\",\"YYG\",\"city\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (125, 'parseSysUserTemplate', 'table_1', '[\"prmry\",\"SigoYao\",\"city\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (126, 'parseSysUserTemplate', 'table_1', '[\"prmry\",\"SigoWeb\",\"reForecast\"]', 'ads_user_prmry_repay_forecast_date', 1);
INSERT INTO `t_template_table_mapping` VALUES (127, 'parseSysUserTemplate', 'table_1', '[\"prmry\",\"YJH\",\"reForecast\"]', 'ads_user_prmry_repay_forecast_date', 1);
INSERT INTO `t_template_table_mapping` VALUES (128, 'parseSysUserTemplate', 'table_1', '[\"prmry\",\"YYG\",\"reForecast\"]', 'ads_user_prmry_repay_forecast_date', 1);
INSERT INTO `t_template_table_mapping` VALUES (129, 'parseSysUserTemplate', 'table_1', '[\"prmry\",\"SigoYao\",\"reForecast\"]', 'ads_user_prmry_repay_forecast_date', 1);
INSERT INTO `t_template_table_mapping` VALUES (130, 'parseSysUserTemplate', 'table_1', '[\"store\",\"SigoWeb\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (131, 'parseSysUserTemplate', 'table_1', '[\"store\",\"YJH\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (132, 'parseSysUserTemplate', 'table_1', '[\"store\",\"YYG\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (133, 'parseSysUserTemplate', 'table_1', '[\"store\",\"SigoYao\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (134, 'parseSysUserTemplate', 'table_1', '[\"store\",\"SigoWeb\",\"fans_register\"]', 'ads_chnl_fans_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (135, 'parseSysUserTemplate', 'table_1', '[\"store\",\"YJH\",\"fans_register\"]', 'ads_chnl_fans_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (136, 'parseSysUserTemplate', 'table_1', '[\"store\",\"YYG\",\"fans_register\"]', 'ads_chnl_fans_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (137, 'parseSysUserTemplate', 'table_1', '[\"store\",\"SigoYao\",\"fans_register\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (138, 'parseSysUserTemplate', 'table_1', '[\"store\",\"SigoWeb\",\"channel_register\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (139, 'parseSysUserTemplate', 'table_1', '[\"store\",\"YJH\",\"channel_register\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (140, 'parseSysUserTemplate', 'table_1', '[\"store\",\"YYG\",\"channel_register\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (141, 'parseSysUserTemplate', 'table_1', '[\"store\",\"SigoWeb\",\"city\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (142, 'parseSysUserTemplate', 'table_1', '[\"store\",\"YJH\",\"city\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (143, 'parseSysUserTemplate', 'table_1', '[\"store\",\"YYG\",\"city\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (144, 'parseSysUserTemplate', 'table_1', '[\"store\",\"SigoYao\",\"city\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (145, 'parseSysUserTemplate', 'table_1', '[\"store\",\"SigoWeb\",\"reForecast\"]', 'ads_user_cs_repay_forecast_date', 1);
INSERT INTO `t_template_table_mapping` VALUES (146, 'parseSysUserTemplate', 'table_1', '[\"store\",\"YJH\",\"reForecast\"]', 'ads_user_cs_repay_forecast_date', 1);
INSERT INTO `t_template_table_mapping` VALUES (147, 'parseSysUserTemplate', 'table_1', '[\"store\",\"YYG\",\"reForecast\"]', 'ads_user_cs_repay_forecast_date', 1);
INSERT INTO `t_template_table_mapping` VALUES (148, 'parseSysUserTemplate', 'table_1', '[\"store\",\"SigoYao\",\"reForecast\"]', 'ads_user_cs_repay_forecast_date', 1);
INSERT INTO `t_template_table_mapping` VALUES (149, 'parseSysUserTemplate', 'table_1', '[\"SigoWeb\",\"fans_first_add\"]', 'ads_chnl_fans_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (150, 'parseSysUserTemplate', 'table_1', '[\"YJH\",\"fans_first_add\"]', 'ads_chnl_fans_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (151, 'parseSysUserTemplate', 'table_1', '[\"YYG\",\"fans_first_add\"]', 'ads_chnl_fans_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (152, 'parseSysUserTemplate', 'table_1', '[\"prmry\",\"SigoWeb\",\"fans_first_add\"]', 'ads_chnl_fans_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (153, 'parseSysUserTemplate', 'table_1', '[\"prmry\",\"YJH\",\"fans_first_add\"]', 'ads_chnl_fans_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (154, 'parseSysUserTemplate', 'table_1', '[\"prmry\",\"YYG\",\"fans_first_add\"]', 'ads_chnl_fans_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (155, 'parseSysUserTemplate', 'table_1', '[\"brand_qy\"]', 'brand_user_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (156, 'parseDoAndTemplate', 'table_1', '[\"pay\",\"degree\"]', 'ads_rb_user_gds_degree_cs_d_idm', 1);
INSERT INTO `t_template_table_mapping` VALUES (159, 'parseDoAndTemplate', 'table_1', '[\"addCart\",\"degree\"]', 'dws_ord_user_egds_degree_spt_d_idm', 1);
INSERT INTO `t_template_table_mapping` VALUES (160, 'parseEventDoAndTemplate', 'table_1', '[\"pay\",\"degree\"]', 'ads_rb_user_gds_degree_cs_d_idm', 1);
INSERT INTO `t_template_table_mapping` VALUES (161, 'parseEventDoAndTemplate', 'table_1', '[\"addCart\",\"degree\"]', 'dws_ord_user_egds_degree_spt_d_idm', 1);
INSERT INTO `t_template_table_mapping` VALUES (162, 'parseSysUserTemplate', 'table_1', '[\"SigoLanmou\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (163, 'parseSysUserTemplate', 'table_1', '[\"SigoLanmou\",\"fans_register\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (164, 'parseNotDoTemplate', 'table_1', '[\"SigoLanmou\",\"pay\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (165, 'parseNotDoTemplate', 'table_1', '[\"SigoLanmou\",\"pv\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (166, 'parseSysUserTemplate', 'table_1', '[\"SigoLanmou\",\"city\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (167, 'parseSysUserTemplate', 'table_1', '[\"SigoLanmou\",\"reForecast\"]', 'ads_user_prmry_repay_forecast_date', 1);
INSERT INTO `t_template_table_mapping` VALUES (168, 'parseSysUserTemplate', 'table_1', '[\"prmry\",\"SigoLanmou\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (169, 'parseSysUserTemplate', 'table_1', '[\"prmry\",\"SigoLanmou\",\"fans_register\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (170, 'parseSysUserTemplate', 'table_1', '[\"prmry\",\"SigoLanmou\",\"city\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (171, 'parseSysUserTemplate', 'table_1', '[\"prmry\",\"SigoLanmou\",\"reForecast\"]', 'ads_user_prmry_repay_forecast_date', 1);
INSERT INTO `t_template_table_mapping` VALUES (172, 'parseSysUserTemplate', 'table_1', '[\"store\",\"SigoLanmou\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (173, 'parseSysUserTemplate', 'table_1', '[\"store\",\"SigoLanmou\",\"fans_register\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (174, 'parseSysUserTemplate', 'table_1', '[\"store\",\"SigoLanmou\",\"city\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (175, 'parseSysUserTemplate', 'table_1', '[\"store\",\"SigoLanmou\",\"reForecast\"]', 'ads_user_cs_repay_forecast_date', 1);
INSERT INTO `t_template_table_mapping` VALUES (10000, 'vipParseSysUserTemplate', 'table_1', '[\"SigoWeb\",\"vip_register\"]', 'ads_user_member_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (10001, 'vipParseSysUserTemplate', 'table_1', '[\"YJH\",\"vip_register\"]', 'ads_user_member_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (10002, 'vipParseSysUserTemplate', 'table_1', '[\"YYG\",\"vip_register\"]', 'ads_user_member_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (10003, 'vipParseSysUserTemplate', 'table_1', '[\"prmry\",\"SigoWeb\",\"vip_register\"]', 'ads_user_member_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (10004, 'vipParseSysUserTemplate', 'table_1', '[\"prmry\",\"YJH\",\"vip_register\"]', 'ads_user_member_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (10005, 'vipParseSysUserTemplate', 'table_1', '[\"prmry\",\"YYG\",\"vip_register\"]', 'ads_user_member_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (10006, 'vipParseSysUserTemplate', 'table_1', '[\"store\",\"SigoWeb\",\"vip_register\"]', 'ads_user_member_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (10007, 'vipParseSysUserTemplate', 'table_1', '[\"store\",\"YJH\",\"vip_register\"]', 'ads_user_member_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (10008, 'vipParseSysUserTemplate', 'table_1', '[\"store\",\"YYG\",\"vip_register\"]', 'ads_user_member_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (10009, 'vipParseDoAndTemplate', 'table_1', '[\"pay\",\"product\"]', 'ads_rb_user_gds_cs_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (10010, 'vipParseDoAndTemplate', 'table_1', '[\"pv\",\"product\"]', 'dws_log_user_egds_pv_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (10011, 'vipParseDoAndTemplate', 'table_1', '[\"addCart\",\"product\"]', 'dws_ord_user_egds_spt_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (10012, 'vipParseDoAndTemplate', 'table_1', '[\"collect\",\"product\"]', 'dws_irc_user_egds_clt_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (10013, 'vipParseDoAndTemplate', 'table_1', '[\"pv\",\"brand\"]', 'dws_log_user_egds_pv_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (10014, 'vipParseDoAndTemplate', 'table_1', '[\"pay\",\"brand\"]', 'ads_rb_user_gds_cs_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (10015, 'vipParseDoAndTemplate', 'table_1', '[\"pay\",\"cycle\"]', 'ads_rb_user_gds_cs_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (10016, 'vipParseDoAndTemplate', 'table_1', '[\"pay\",\"color\"]', 'ads_rb_user_gds_cs_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (10017, 'vipParseDoAndTemplate', 'table_1', '[\"addCart\",\"brand\"]', 'dws_ord_user_egds_spt_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (10018, 'vipParseDoAndTemplate', 'table_1', '[\"addCart\",\"cycle\"]', 'dws_ord_user_egds_spt_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (10019, 'vipParseDoAndTemplate', 'table_1', '[\"addCart\",\"color\"]', 'dws_ord_user_egds_spt_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (10020, 'vipParseDoAndTemplate', 'table_1', '[\"collect\",\"brand\"]', 'dws_irc_user_egds_clt_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (10021, 'vipParseDoAndTemplate', 'table_1', '[\"collect\",\"cycle\"]', 'dws_irc_user_egds_clt_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (10022, 'vipParseDoAndTemplate', 'table_1', '[\"collect\",\"color\"]', 'dws_irc_user_egds_clt_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (10023, 'vipParseDoAndTemplate', 'table_1', '[\"pv\",\"cycle\"]', 'dws_log_user_egds_pv_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (10024, 'vipParseDoAndTemplate', 'table_1', '[\"pv\",\"color\"]', 'dws_log_user_egds_pv_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (10025, 'vipParseDoAndTemplate', 'table_1', '[\"pay\",\"cate\"]', 'ads_rb_user_gds_cs_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (10026, 'vipParseDoAndTemplate', 'table_1', '[\"pay\"]', 'ads_rb_user_gds_cs_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (10027, 'vipParseDoAndTemplate', 'table_1', '[\"addCart\",\"cate\"]', 'dws_ord_user_egds_spt_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (10028, 'vipParseDoAndTemplate', 'table_1', '[\"collect\",\"cate\"]', 'dws_irc_user_egds_clt_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (10029, 'vipParseDoAndTemplate', 'table_1', '[\"pv\",\"cate\"]', 'dws_log_user_egds_pv_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (10030, 'vipParseDoAndTemplate', 'table_1', '[\"pay\",\"degree\"]', 'ads_rb_user_gds_degree_cs_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (10031, 'vipParseDoAndTemplate', 'table_1', '[\"addCart\",\"degree\"]', 'dws_ord_user_egds_degree_spt_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (10032, 'vipParseNotDoTemplate', 'table_1', '[\"SigoWeb\",\"pay\"]', 'ads_user_member_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (10033, 'vipParseNotDoTemplate', 'table_1', '[\"YYG\",\"pay\"]', 'ads_user_member_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (10034, 'vipParseNotDoTemplate', 'table_1', '[\"YJH\",\"pay\"]', 'ads_user_member_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (10035, 'vipParseNotDoTemplate', 'table_1', '[\"SigoYao\",\"pay\"]', 'ads_user_member_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (10036, 'vipParseNotDoTemplate', 'table_1', '[\"SigoWeb\",\"addCart\"]', 'ads_user_member_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (10037, 'vipParseNotDoTemplate', 'table_1', '[\"YYG\",\"addCart\"]', 'ads_user_member_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (10038, 'vipParseNotDoTemplate', 'table_1', '[\"YJH\",\"addCart\"]', 'ads_user_member_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (10039, 'vipParseNotDoTemplate', 'table_1', '[\"SigoWeb\",\"collect\"]', 'ads_user_member_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (10040, 'vipParseNotDoTemplate', 'table_1', '[\"YYG\",\"collect\"]', 'ads_user_member_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (10041, 'vipParseNotDoTemplate', 'table_1', '[\"YJH\",\"collect\"]', 'ads_user_member_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (10042, 'vipParseNotDoTemplate', 'table_1', '[\"SigoWeb\",\"pv\"]', 'ads_user_member_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (10043, 'vipParseNotDoTemplate', 'table_1', '[\"YYG\",\"pv\"]', 'ads_user_member_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (10044, 'vipParseNotDoTemplate', 'table_1', '[\"YJH\",\"pv\"]', 'ads_user_member_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (10045, 'vipParseNotDoTemplate', 'table_1', '[\"SigoYao\",\"pv\"]', 'ads_user_member_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (10046, 'vipParseNotDoTemplate', 'table_1', '[\"YYG\",\"view\"]', 'ads_user_member_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (10047, 'vipParseNotDoTemplate', 'table_1', '[\"YJH\",\"view\"]', 'ads_user_member_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (10048, 'vipParseNotDoTemplate', 'table_1', '[\"SigoWeb\",\"view\"]', 'ads_user_member_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (10049, 'vipParseNotDoTemplate', 'table_1', '[\"AllChannel\",\"pay\"]', 'ads_user_member_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (10050, 'vipParseNotDoTemplate', 'table_1', '[\"AllChannel\",\"addCart\"]', 'ads_user_member_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (10051, 'vipParseNotDoTemplate', 'table_1', '[\"AllChannel\",\"collect\"]', 'ads_user_member_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (10052, 'vipParseNotDoTemplate', 'table_1', '[\"AllChannel\",\"pv\"]', 'ads_user_member_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (10053, 'vipParseNotDoTemplate', 'table_1', '[\"AllChannel\",\"view\"]', 'ads_user_member_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (10054, 'vipParseNotDoTemplate', 'table_1', '[\"SigoLanmou\",\"pay\"]', 'ads_user_member_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (10055, 'vipParseNotDoTemplate', 'table_1', '[\"SigoLanmou\",\"pv\"]', 'ads_user_member_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (10056, 'vipParseEventDoAndTemplate', 'table_1', '[\"pay\",\"product\"]', 'ads_rb_user_gds_cs_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (10057, 'vipParseEventDoAndTemplate', 'table_1', '[\"pv\",\"product\"]', 'dws_log_user_egds_pv_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (10058, 'vipParseEventDoAndTemplate', 'table_1', '[\"addCart\",\"product\"]', 'dws_ord_user_egds_spt_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (10059, 'vipParseEventDoAndTemplate', 'table_1', '[\"collect\",\"product\"]', 'dws_irc_user_egds_clt_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (10060, 'vipParseEventDoAndTemplate', 'table_1', '[\"pay\",\"brand\"]', 'ads_rb_user_gds_cs_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (10061, 'vipParseEventDoAndTemplate', 'table_1', '[\"pay\",\"cycle\"]', 'ads_rb_user_gds_cs_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (10062, 'vipParseEventDoAndTemplate', 'table_1', '[\"pay\",\"cate\"]', 'ads_rb_user_gds_cs_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (10063, 'vipParseEventDoAndTemplate', 'table_1', '[\"addCart\",\"brand\"]', 'dws_ord_user_egds_spt_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (10064, 'vipParseEventDoAndTemplate', 'table_1', '[\"addCart\",\"cycle\"]', 'dws_ord_user_egds_spt_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (10065, 'vipParseEventDoAndTemplate', 'table_1', '[\"addCart\",\"cate\"]', 'dws_ord_user_egds_spt_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (10066, 'vipParseEventDoAndTemplate', 'table_1', '[\"pv\",\"brand\"]', 'dws_log_user_egds_pv_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (10067, 'vipParseEventDoAndTemplate', 'table_1', '[\"pv\",\"cycle\"]', 'dws_log_user_egds_pv_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (10068, 'vipParseEventDoAndTemplate', 'table_1', '[\"pv\",\"cate\"]', 'dws_log_user_egds_pv_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (10069, 'vipParseEventDoAndTemplate', 'table_1', '[\"collect\",\"brand\"]', 'dws_irc_user_egds_clt_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (10070, 'vipParseEventDoAndTemplate', 'table_1', '[\"collect\",\"cycle\"]', 'dws_irc_user_egds_clt_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (10071, 'vipParseEventDoAndTemplate', 'table_1', '[\"collect\",\"cate\"]', 'dws_irc_user_egds_clt_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (10072, 'vipParseEventDoAndTemplate', 'table_1', '[\"pay\",\"degree\"]', 'ads_rb_user_gds_degree_cs_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (10073, 'vipParseEventDoAndTemplate', 'table_1', '[\"addCart\",\"degree\"]', 'dws_ord_user_egds_degree_spt_d', 1);
INSERT INTO `t_template_table_mapping` VALUES (10074, 'vipParseDoAndCouponPrizeTemplate', 'table_1', '[\"coupon\",\"coupon_template\"]', 'dws_user_coupon_prize_action', 1);
INSERT INTO `t_template_table_mapping` VALUES (10075, 'vipParseDoAndCouponPrizeTemplate', 'table_1', '[\"prize\",\"prize_product\"]', 'dws_user_coupon_prize_action', 1);
INSERT INTO `t_template_table_mapping` VALUES (10076, 'vipParseNotDoCouponPrizeTemplate', 'table_1', '[\"SigoWeb\",\"coupon\"]', 'dws_user_coupon_prize_action', 1);
INSERT INTO `t_template_table_mapping` VALUES (10077, 'vipParseNotDoCouponPrizeTemplate', 'table_1', '[\"YYG\",\"coupon\"]', 'dws_user_coupon_prize_action', 1);
INSERT INTO `t_template_table_mapping` VALUES (10078, 'vipParseNotDoCouponPrizeTemplate', 'table_1', '[\"YJH\",\"coupon\"]', 'dws_user_coupon_prize_action', 1);
INSERT INTO `t_template_table_mapping` VALUES (10079, 'vipParseNotDoCouponPrizeTemplate', 'table_1', '[\"SigoWeb\",\"prize\"]', 'dws_user_coupon_prize_action', 1);
INSERT INTO `t_template_table_mapping` VALUES (10080, 'vipParseNotDoCouponPrizeTemplate', 'table_1', '[\"YYG\",\"prize\"]', 'dws_user_coupon_prize_action', 1);
INSERT INTO `t_template_table_mapping` VALUES (10081, 'vipParseNotDoCouponPrizeTemplate', 'table_1', '[\"YJH\",\"prize\"]', 'dws_user_coupon_prize_action', 1);
INSERT INTO `t_template_table_mapping` VALUES (10082, 'vipParseNotDoCouponPrizeTemplate', 'table_1', '[\"SigoWeb\",\"coupon\",\"coupon_template\"]', 'dws_user_coupon_prize_action', 1);
INSERT INTO `t_template_table_mapping` VALUES (10083, 'vipParseNotDoCouponPrizeTemplate', 'table_1', '[\"YYG\",\"coupon\",\"coupon_template\"]', 'dws_user_coupon_prize_action', 1);
INSERT INTO `t_template_table_mapping` VALUES (10084, 'vipParseNotDoCouponPrizeTemplate', 'table_1', '[\"YJH\",\"coupon\",\"coupon_template\"]', 'dws_user_coupon_prize_action', 1);
INSERT INTO `t_template_table_mapping` VALUES (10085, 'vipParseNotDoCouponPrizeTemplate', 'table_1', '[\"SigoWeb\",\"prize\",\"prize_product\"]', 'dws_user_coupon_prize_action', 1);
INSERT INTO `t_template_table_mapping` VALUES (10086, 'vipParseNotDoCouponPrizeTemplate', 'table_1', '[\"YYG\",\"prize\",\"prize_product\"]', 'dws_user_coupon_prize_action', 1);
INSERT INTO `t_template_table_mapping` VALUES (10087, 'vipParseNotDoCouponPrizeTemplate', 'table_1', '[\"YJH\",\"prize\",\"prize_product\"]', 'dws_user_coupon_prize_action', 1);
INSERT INTO `t_template_table_mapping` VALUES (10088, 'parseSysUserTemplate', 'table_1', '[\"store\",\"city\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (10089, 'parseSysUserTemplate', 'table_1', '[\"city\"]', 'ads_user_oneid_action_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (10090, 'vipParseDoAndCouponPrizeTemplate', 'table_1', '[\"prize\",\"prize_activity\"]', 'dws_user_coupon_prize_action', 1);
INSERT INTO `t_template_table_mapping` VALUES (10091, 'vipParseNotDoCouponPrizeTemplate', 'table_1', '[\"YJH\",\"prize\",\"prize_activity\"]', 'dws_user_coupon_prize_action', 1);
INSERT INTO `t_template_table_mapping` VALUES (10092, 'vipParseNotDoCouponPrizeTemplate', 'table_1', '[\"YYG\",\"prize\",\"prize_activity\"]', 'dws_user_coupon_prize_action', 1);
INSERT INTO `t_template_table_mapping` VALUES (10093, 'vipParseNotDoCouponPrizeTemplate', 'table_1', '[\"SigoWeb\",\"prize\",\"prize_activity\"]', 'dws_user_coupon_prize_action', 1);
INSERT INTO `t_template_table_mapping` VALUES (10103, 'parseSysUserTemplate', 'table_1', '[\"SigoWeb\",\"brand_fans\"]', 'wecom_user_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (10104, 'parseSysUserTemplate', 'table_1', '[\"YJH\",\"brand_fans\"]', 'wecom_user_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (10105, 'parseSysUserTemplate', 'table_1', '[\"YYG\",\"brand_fans\"]', 'wecom_user_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (10106, 'parseSysUserTemplate', 'table_1', '[\"prmry\",\"SigoWeb\",\"brand_fans\"]', 'wecom_user_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (10107, 'parseSysUserTemplate', 'table_1', '[\"prmry\",\"YJH\",\"brand_fans\"]', 'wecom_user_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (10108, 'parseSysUserTemplate', 'table_1', '[\"prmry\",\"YYG\",\"brand_fans\"]', 'wecom_user_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (10109, 'parseSysUserTemplate', 'table_1', '[\"store\",\"SigoWeb\",\"brand_fans\"]', 'wecom_user_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (10110, 'parseSysUserTemplate', 'table_1', '[\"store\",\"YJH\",\"brand_fans\"]', 'wecom_user_tag_app', 1);
INSERT INTO `t_template_table_mapping` VALUES (10111, 'parseSysUserTemplate', 'table_1', '[\"store\",\"YYG\",\"brand_fans\"]', 'wecom_user_tag_app', 1);

SET FOREIGN_KEY_CHECKS = 1;
