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

 Date: 20/04/2024 15:18:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_template_mapping
-- ----------------------------
DROP TABLE IF EXISTS `t_template_mapping`;
CREATE TABLE `t_template_mapping`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tempalte_part` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板组成部分',
  `template_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '模板映射表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_template_mapping
-- ----------------------------
INSERT INTO `t_template_mapping` VALUES (2, 'do', 'parseDoTemplate');
INSERT INTO `t_template_mapping` VALUES (3, 'do|and', 'parseDoAndTemplate');
INSERT INTO `t_template_mapping` VALUES (5, 'notdo', 'parseNotDoTemplate');
INSERT INTO `t_template_mapping` VALUES (13, 'sysuser', 'parseSysUserTemplate');
INSERT INTO `t_template_mapping` VALUES (14, 'event_do|event_and', 'parseEventDoAndTemplate');
INSERT INTO `t_template_mapping` VALUES (18, 'do|maxmin', 'parseDoMaxMinTemplate');
INSERT INTO `t_template_mapping` VALUES (21, 'do|pay', 'parseDoAndTemplate');
INSERT INTO `t_template_mapping` VALUES (22, 'do|and|pay', 'parseDoAndTemplate');
INSERT INTO `t_template_mapping` VALUES (23, 'notdo|pay', 'parseNotDoPayTemplate');
INSERT INTO `t_template_mapping` VALUES (25, 'sys_agg_max', 'parseAggMaxSceneTemplate');
INSERT INTO `t_template_mapping` VALUES (26, 'vipdo', 'vipParseDoTemplate');
INSERT INTO `t_template_mapping` VALUES (27, 'vipdo|and', 'vipParseDoAndTemplate');
INSERT INTO `t_template_mapping` VALUES (28, 'vipnotdo', 'vipParseNotDoTemplate');
INSERT INTO `t_template_mapping` VALUES (29, 'vipdo|pay', 'vipParseDoAndTemplate');
INSERT INTO `t_template_mapping` VALUES (30, 'vipdo|and|pay', 'vipParseDoAndTemplate');
INSERT INTO `t_template_mapping` VALUES (31, 'vipnotdo|pay', 'vipParseNotDoPayTemplate');
INSERT INTO `t_template_mapping` VALUES (32, 'vipevent_do|vipevent_and', 'vipParseEventDoAndTemplate');
INSERT INTO `t_template_mapping` VALUES (33, 'vipdo|maxmin', 'vipParseDoMaxMinTemplate');
INSERT INTO `t_template_mapping` VALUES (34, 'vipsys_agg_max', 'vipParseAggMaxSceneTemplate');
INSERT INTO `t_template_mapping` VALUES (35, 'vipsysuser', 'vipParseSysUserTemplate');
INSERT INTO `t_template_mapping` VALUES (36, 'vipdo|coupon', 'vipParseDoCouponPrizeTemplate');
INSERT INTO `t_template_mapping` VALUES (37, 'vipnotdo|coupon', 'vipParseNotDoCouponPrizeTemplate');
INSERT INTO `t_template_mapping` VALUES (38, 'vipdo|and|coupon', 'vipParseDoAndCouponPrizeTemplate');
INSERT INTO `t_template_mapping` VALUES (39, 'vipdo|prize', 'vipParseDoCouponPrizeTemplate');
INSERT INTO `t_template_mapping` VALUES (40, 'vipnotdo|prize', 'vipParseNotDoCouponPrizeTemplate');
INSERT INTO `t_template_mapping` VALUES (41, 'vipdo|and|prize', 'vipParseDoAndCouponPrizeTemplate');
INSERT INTO `t_template_mapping` VALUES (42, 'vipnotdo|and|coupon', 'vipParseNotDoCouponPrizeTemplate');
INSERT INTO `t_template_mapping` VALUES (43, 'vipnotdo|and|prize', 'vipParseNotDoCouponPrizeTemplate');

SET FOREIGN_KEY_CHECKS = 1;
