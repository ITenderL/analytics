/*
 Navicat Premium Data Transfer

 Source Server         : idata-mysql-test
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : idata-ptag-mysql-master-fh-t.seeke.net:3306
 Source Schema         : product

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 20/04/2024 15:27:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for product_target_conf
-- ----------------------------
DROP TABLE IF EXISTS `product_target_conf`;
CREATE TABLE `product_target_conf`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `target_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '指标code',
  `target_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '指标名称',
  `target_type` tinyint(4) NOT NULL DEFAULT 1 COMMENT '指标类型（1：原子指标，2：派生指标）',
  `target_expression` varchar(5000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表达式',
  `plan_field` tinyint(1) NULL DEFAULT 0 COMMENT '是否是计划指标',
  `plan_ex` tinyint(1) NULL DEFAULT 0 COMMENT '计划是否计算',
  `show_status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否展示',
  `value_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '值类型  int decimal percent string',
  `deal_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处理类型',
  `sort` int(11) NULL DEFAULT 0 COMMENT '指标排序',
  `definition` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '指标定义',
  `target_cate` tinyint(2) NULL DEFAULT NULL COMMENT '指标分类： 0：销售 1品质',
  `table_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表名，如果表名固定则直接取表名，如果为空走路由',
  `data_source` tinyint(1) NULL DEFAULT NULL COMMENT '0：greenplum 1: starrocks ',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '备注',
  `add_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `adder` bigint(20) NULL DEFAULT NULL COMMENT '添加人ID',
  `adder_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '添加人姓名',
  `mod_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `moder` bigint(20) NULL DEFAULT NULL COMMENT '更新人ID',
  `moder_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '更新人姓名',
  `delete_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除标识（0：未删除，1：已删除）',
  `table_expression` varchar(5000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '数据库查询表达式',
  `table_expression_type` tinyint(1) NULL DEFAULT NULL COMMENT '表达式类型：1：默认使用table_expression 2：根据条件获取不同的表达式',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 51 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '指标信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_target_conf
-- ----------------------------
INSERT INTO `product_target_conf` VALUES (1, 'fi_gmv', 'GMV(出库)', 1, 'fi_gmv', 0, 0, 1, 'decimal', 'base', 0, NULL, 0, NULL, NULL, '', '2023-04-19 18:48:11', NULL, '', '2023-11-15 21:01:04', NULL, '', 0, 'COALESCE(sum(fi_gmv),0)', NULL);
INSERT INTO `product_target_conf` VALUES (2, 'product_count', '销售件数(含赠品)', 1, 'product_count', 1, 0, 1, 'int', 'base', 0, NULL, 0, NULL, NULL, '', '2023-04-19 18:52:53', NULL, '', '2023-11-15 21:01:04', NULL, '', 0, 'COALESCE(sum(product_count),0)', NULL);
INSERT INTO `product_target_conf` VALUES (3, 'sale_pieces_count', '销售片数', 1, 'sale_pieces_count', 0, 0, 1, 'int', 'base', 0, NULL, 0, NULL, NULL, '', '2023-04-19 18:53:37', NULL, '', '2023-11-15 21:01:04', NULL, '', 0, 'COALESCE(sum(sale_pieces_count),0)', NULL);
INSERT INTO `product_target_conf` VALUES (4, 'price_per_product', '件单价', 2, 'product_amount/product_count_jdj', 0, 0, 1, 'decimal', 'base', 0, NULL, 0, NULL, NULL, '', '2023-04-19 18:54:05', NULL, '', '2023-11-15 21:01:04', NULL, '', 0, 'product_amount/product_count_jdj', NULL);
INSERT INTO `product_target_conf` VALUES (5, 'price_per_piece', '片单价', 2, 'product_amount/sale_pieces_count_jdj', 0, 0, 1, 'decimal', 'base', 0, NULL, 0, NULL, NULL, '', '2023-04-19 18:54:43', NULL, '', '2023-11-15 21:01:04', NULL, '', 0, 'product_amount/sale_pieces_count_jdj', NULL);
INSERT INTO `product_target_conf` VALUES (6, 'gross_profit_margin', '毛利额', 1, 'gross_profit_margin', 0, 0, 1, 'decimal', 'base', 0, NULL, 0, NULL, NULL, '', '2023-04-19 18:55:05', NULL, '', '2023-11-15 21:01:04', NULL, '', 0, 'COALESCE(sum(gross_profit_margin),0)', NULL);
INSERT INTO `product_target_conf` VALUES (7, 'gross_profit_rate', '毛利率', 2, 'gross_profit_margin/fi_gmv', 0, 0, 1, 'percent', 'base', 0, NULL, 0, NULL, NULL, '', '2023-04-19 18:55:25', NULL, '', '2023-11-15 21:01:04', NULL, '', 0, 'gross_profit_margin/fi_gmv', NULL);
INSERT INTO `product_target_conf` VALUES (8, 'buy_user', '商品买家数', 1, 'buy_user', 0, 0, 0, 'int', 'base', 0, NULL, 0, NULL, NULL, '', '2023-04-19 18:56:15', NULL, '', '2023-11-27 18:48:10', NULL, '', 0, 'COALESCE(sum(buy_user),0)', NULL);
INSERT INTO `product_target_conf` VALUES (9, 'product_uv', '商详页UV', 1, 'product_uv', 0, 0, 0, 'int', 'base', 0, NULL, 0, NULL, NULL, '', '2023-04-19 18:56:28', NULL, '', '2023-11-15 21:01:04', NULL, '', 0, 'COALESCE(sum(product_uv),0)', NULL);
INSERT INTO `product_target_conf` VALUES (10, 'pay_crv', '支付转化率', 2, 'buy_user/product_uv', 0, 0, 1, 'percent', 'base', 0, NULL, 0, NULL, NULL, '', '2023-04-19 18:56:39', NULL, '', '2023-11-15 21:01:04', NULL, '', 0, 'buy_user/product_uv', NULL);
INSERT INTO `product_target_conf` VALUES (12, 'active_evaluation_count', '主动评价数', 1, 'active_evaluation_count', 0, 0, 1, 'int', 'evaluation', 0, NULL, 1, NULL, NULL, '', '2023-07-27 10:46:53', NULL, '', '2024-03-18 10:35:19', NULL, '', 0, 'count(distinct feedback_id) ', NULL);
INSERT INTO `product_target_conf` VALUES (13, 'follow_up_evaluation_count', '追评评价数', 1, 'follow_up_evaluation_count', 0, 0, 1, 'int', 'base', 0, NULL, 1, NULL, NULL, '', '2023-07-27 10:48:31', NULL, '', '2023-11-15 21:01:04', NULL, '', 0, 'COALESCE(sum(follow_up_evaluation_count),0)', NULL);
INSERT INTO `product_target_conf` VALUES (14, 'total_opinion_count', '全部观点数', 1, 'negative_review_count+positive_opinion_count ', 0, 0, 1, 'int', 'base', 0, NULL, 1, NULL, NULL, '', '2023-07-27 10:51:03', NULL, '', '2023-11-15 21:01:04', NULL, '', 0, 'negative_review_count+positive_opinion_count ', NULL);
INSERT INTO `product_target_conf` VALUES (15, 'positive_opinion_count', '正面观点数', 1, 'positive_opinion_count ', 0, 0, 1, 'int', 'base', 0, NULL, 1, NULL, NULL, '', '2023-07-27 10:53:54', NULL, '', '2023-11-15 21:01:04', NULL, '', 0, 'COALESCE(sum(positive_opinion_count ),0)', NULL);
INSERT INTO `product_target_conf` VALUES (17, 'negative_opinion_ratio', '负面观点率', 2, 'negative_review_count/(negative_review_count+positive_opinion_count)', 0, 0, 1, 'percent', 'base', 0, NULL, 1, NULL, NULL, '', '2023-07-27 10:59:15', NULL, '', '2023-11-15 21:01:04', NULL, '', 0, 'negative_review_count/(negative_review_count+positive_opinion_count)', NULL);
INSERT INTO `product_target_conf` VALUES (18, 'product_amount', '商品金额(GMV未减退款)', 1, 'product_amount', 0, 0, 0, 'decimal', 'base', 0, NULL, 0, NULL, NULL, '', '2023-07-31 18:22:42', NULL, '', '2023-11-15 21:01:04', NULL, '', 0, 'COALESCE(sum(product_amount),0)', NULL);
INSERT INTO `product_target_conf` VALUES (19, 'product_count_jdj', '商品件数(不包含赠品，不剔除退货)', 1, 'product_count_jdj', 0, 0, 1, 'int', 'base', 0, NULL, 0, NULL, NULL, '', '2023-07-31 18:23:15', NULL, '', '2023-11-15 21:01:04', NULL, '', 0, 'COALESCE(sum(product_count_jdj),0)', NULL);
INSERT INTO `product_target_conf` VALUES (20, 'sale_pieces_count_jdj', '销售片数:不包含赠品，\r\n不剔除退货(隐形眼类: 件数*规格]', 1, 'sale_pieces_count_jdj', 0, 0, 1, 'int', 'base', 0, NULL, 0, NULL, NULL, '', '2023-07-31 18:23:43', NULL, '', '2023-11-15 21:01:04', NULL, '', 0, 'COALESCE(sum(sale_pieces_count_jdj),0)', NULL);
INSERT INTO `product_target_conf` VALUES (21, 'order_count', '订单量', 1, 'order_count', 0, 0, 1, 'int', 'base', 0, NULL, 1, 'stock_pay_order', NULL, '', '2023-08-03 10:56:05', NULL, '', '2024-02-26 10:30:59', NULL, '', 0, ' count( DISTINCT erp_order_code)', NULL);
INSERT INTO `product_target_conf` VALUES (22, 'positive_opinion_ratio', '正面观点观点率', 2, 'positive_opinion_count/(negative_review_count+positive_opinion_count)', 0, 0, 1, 'percent', 'base', 0, NULL, 1, NULL, NULL, '', '2023-08-07 14:34:57', NULL, '', '2023-11-15 21:01:04', NULL, '', 0, 'positive_opintion_count/(negative_review_count+positive_opinion_count)', NULL);
INSERT INTO `product_target_conf` VALUES (23, 'product_count_sppj', '销售件数(不剔除赠品不剔除退货)', 1, 'product_count_sppj', 0, 0, 1, 'int', 'base', 0, NULL, 0, NULL, NULL, '', '2023-08-15 11:23:28', NULL, '', '2023-11-15 21:01:04', NULL, '', 0, 'COALESCE(sum(product_count_sppj),0)', NULL);
INSERT INTO `product_target_conf` VALUES (24, 'negative_review_count', '负面观点数', 1, 'negative_review_count', 0, 0, 1, 'int', 'base', 0, NULL, 1, NULL, NULL, '', '2023-07-27 10:46:53', NULL, '', '2023-11-15 21:01:04', NULL, '', 0, 'COALESCE(sum(negative_review_count),0)', NULL);
INSERT INTO `product_target_conf` VALUES (25, 'sigo_sale_loss_amount', '视客销售机会损失额', 1, 'sigo_sale_loss_amount', 0, 0, 1, 'decimal', 'noChannel', 0, NULL, 2, NULL, 1, '', '2023-11-23 16:43:30', NULL, '', '2023-11-24 11:28:18', NULL, '', 0, 'COALESCE(sum(loss_amount),0)', NULL);
INSERT INTO `product_target_conf` VALUES (26, 'sigo_sale_loss_number', '视客销售机会损失量', 2, 'sigo_sale_loss_amount/(product_amount_last_30/product_count_last_30)', 0, 0, 1, 'decimal', 'base', 0, NULL, NULL, NULL, NULL, '', '2023-11-23 16:44:28', NULL, '', '2024-03-27 18:31:23', NULL, '', 1, NULL, NULL);
INSERT INTO `product_target_conf` VALUES (27, 'sigo_sale_loss_rate', '视客销售机会损失率', 2, 'sigo_sale_loss_amount/(fi_gmv+sigo_sale_loss_amount)', 0, 0, 1, 'percent', 'base', 0, NULL, NULL, NULL, NULL, '', '2023-11-23 16:45:19', NULL, '', '2023-11-23 17:55:07', NULL, '', 0, NULL, NULL);
INSERT INTO `product_target_conf` VALUES (28, 'channel_sale_loss_amount', '销售机会损失额', 2, '((sigo_sale_loss_amount/(fi_gmv+sigo_sale_loss_amount))*fi_gmv)/(1-(sale_loss_amount/(fi_gmv+sigo_sale_loss_amount)))', 0, 0, 1, 'decimal', 'base', 0, NULL, NULL, NULL, NULL, '', '2023-11-23 16:43:30', NULL, '', '2023-11-23 17:56:30', NULL, '', 0, '', NULL);
INSERT INTO `product_target_conf` VALUES (29, 'channel_sale_loss_number', '销售机会损失量', 2, '((sigo_sale_loss_amount/(product_count+sigo_sale_loss_amount))*fi_gmv)/(1-(sigo_sale_loss_amount/(fi_gmv+sigo_sale_loss_amount)))', 0, 0, 1, 'decimal', 'base', 0, NULL, NULL, NULL, NULL, '', '2023-11-23 16:44:28', NULL, '', '2023-11-23 17:56:27', NULL, '', 0, NULL, NULL);
INSERT INTO `product_target_conf` VALUES (30, 'channel_sale_loss_rate', '销售机会损失率', 2, '((sigo_sale_loss_amount/(fi_gmv+sale_loss_amount))*fi_gmv)/(1-(sale_loss_amount/(fi_gmv+sale_loss_amount)))/(fi_gmv+((sigo_sale_loss_amount/(fi_gmv+sigo_sale_loss_amount))*fi_gmv)/(1-(sigo_sale_loss_amount/(fi_gmv+sigo_sale_loss_amount))))', 0, 0, 1, 'percent', 'base', 0, NULL, NULL, NULL, NULL, '', '2023-11-23 16:45:19', NULL, '', '2023-11-23 17:56:40', NULL, '', 0, NULL, NULL);
INSERT INTO `product_target_conf` VALUES (32, 'product_count_last_30', '近30天销售件数(不包含赠品，不剔除退货)', 1, 'product_count_last_30', 0, 0, 1, 'decimal', 'noChannel', 0, NULL, 3, 'bd_sku_sale', 1, '', '2023-11-23 16:54:00', NULL, '', '2023-11-24 11:39:58', NULL, '', 0, 'COALESCE(sum(product_count_last_30),0)', NULL);
INSERT INTO `product_target_conf` VALUES (33, 'product_amount_last_30', '近30天商品金额(GMV未减退款)', 1, 'product_amount_last_30', 0, 0, 1, 'decimal', 'noChannel', 0, NULL, 3, 'bd_sku_sale', 1, '', '2023-11-23 16:54:00', NULL, '', '2023-11-24 11:40:03', NULL, '', 0, 'COALESCE(sum(product_amount_last_30),0)', NULL);
INSERT INTO `product_target_conf` VALUES (34, 'sale_loss_rate', '', 2, NULL, 0, 0, 1, 'percent', 'base', 0, NULL, NULL, NULL, NULL, '', '2023-11-23 16:58:05', NULL, '', '2023-11-27 14:52:21', NULL, '', 0, '', 2);
INSERT INTO `product_target_conf` VALUES (35, 'sale_loss_amount', '', 2, NULL, 0, 0, 1, 'decimal', 'base', 0, NULL, NULL, NULL, NULL, '', '2023-11-23 16:58:05', NULL, '', '2023-11-23 16:59:46', NULL, '', 0, '', 2);
INSERT INTO `product_target_conf` VALUES (36, 'sale_loss_number', '', 2, NULL, 0, 0, 1, 'decimal', 'base', 0, NULL, NULL, NULL, NULL, '', '2023-11-23 16:58:05', NULL, '', '2023-11-27 14:52:22', NULL, '', 0, '', 2);
INSERT INTO `product_target_conf` VALUES (37, 'sigo_fi_gmv', '', 1, 'sigo_fi_gmv', 0, 0, 1, 'decimal', 'noChannel', 0, NULL, 0, NULL, NULL, '', '2023-11-27 20:45:29', NULL, '', '2023-11-28 11:38:36', NULL, '', 0, 'COALESCE(sum(fi_gmv),0)', NULL);
INSERT INTO `product_target_conf` VALUES (38, 'positive_evaluation_rate', '评价正面率', 2, 'positive_evaluation_count/active_evaluation_count', 0, 0, 1, 'percent', 'evaluation', 0, NULL, 1, NULL, NULL, '', '2024-02-04 10:35:28', NULL, '', '2024-03-06 14:57:27', NULL, '', 0, 'positive_evaluation_count/active_evaluation_count', NULL);
INSERT INTO `product_target_conf` VALUES (39, 'negative_evaluation_count', '负面评价数', 1, 'negative_evaluation_count ', 0, 0, 1, 'int', 'evaluation', 0, NULL, 1, '(select * from customer_conversation_product_quality where negative_review_count > 0) ', NULL, '', '2024-02-04 10:35:28', NULL, '', '2024-03-18 10:35:19', NULL, '', 0, 'count(distinct feedback_id)', NULL);
INSERT INTO `product_target_conf` VALUES (40, 'negative_evaluation_rate', '评价负面率', 2, 'negative_evaluation_count/active_evaluation_count', 0, 0, 1, 'percent', 'evaluation', 0, NULL, 1, NULL, NULL, '', '2024-02-04 10:35:28', NULL, '', '2024-03-06 14:57:27', NULL, '', 0, 'negative_evaluation_count/active_evaluation_count', NULL);
INSERT INTO `product_target_conf` VALUES (41, 'positive_evaluation_count', '正面评价数', 1, 'positive_evaluation_count ', 0, 0, 1, 'int', 'evaluation', 0, NULL, 1, '(select * from customer_conversation_product_quality where positive_opinion_count > 0) ', NULL, '', '2024-02-04 10:35:44', NULL, '', '2024-03-18 10:35:19', NULL, '', 0, 'count(distinct feedback_id)', NULL);
INSERT INTO `product_target_conf` VALUES (42, 'order_line_count', '订单行量', 1, 'order_line_count', 0, 0, 1, 'int', 'base', 0, NULL, 1, 'stock_pay_order', NULL, '', '2024-02-22 14:45:04', NULL, '', '2024-03-07 09:43:14', NULL, '', 0, ' count( DISTINCT erp_order_detail_code)', NULL);
INSERT INTO `product_target_conf` VALUES (43, 'active_evaluation_rate', '主动评价率', 2, 'active_evaluation_count/order_line_count', 0, 0, 1, 'percent', 'evaluation', 0, NULL, 1, NULL, NULL, '', '2024-02-22 14:45:04', NULL, '', '2024-03-06 14:57:27', NULL, '', 0, 'active_evaluation_count/order_line_count', NULL);
INSERT INTO `product_target_conf` VALUES (44, 'order_after_sale_count', '售后订单量', 1, 'order_after_sale_count', 0, 0, 0, 'int', 'afterSale', 0, NULL, 0, 'after_sale_detail', NULL, '', '2024-03-08 16:15:08', NULL, '', '2024-03-20 16:00:15', NULL, '', 0, 'count(distinct channel_order_code)', NULL);
INSERT INTO `product_target_conf` VALUES (45, 'channel_order_count', '渠道单量', 1, 'channel_order_count', 0, 0, 0, 'int', 'afterSale', 0, NULL, 0, 'delivery_order_detail', NULL, '', '2024-03-08 16:15:08', NULL, '', '2024-03-20 15:59:35', NULL, '', 0, 'count(distinct channel_order_code)', NULL);
INSERT INTO `product_target_conf` VALUES (46, 'order_after_sale_rate', '订单售后发生率', 2, 'order_after_sale_count/channel_order_count', 0, 0, 1, 'percent', 'afterSale', 0, NULL, 0, 'after_sale_detail', NULL, '', '2024-03-08 16:15:08', NULL, '', '2024-03-20 15:59:35', NULL, '', 0, 'order_after_sale_count/channel_order_count', NULL);
INSERT INTO `product_target_conf` VALUES (47, 'product_after_sale_count', '发生售后的商品数量', 1, 'product_after_sale_count', 0, 0, 0, 'int', 'afterSale', 0, NULL, 0, 'after_sale_detail', NULL, '', '2024-03-08 16:15:08', NULL, '', '2024-03-20 15:59:35', NULL, '', 0, 'COALESCE(sum(product_after_sale_count),0)', NULL);
INSERT INTO `product_target_conf` VALUES (48, 'product_shipped_count', '所有商品发货数量', 1, 'product_shipped_count', 0, 0, 0, 'int', 'afterSale', 0, NULL, 0, 'delivery_order_detail', NULL, '', '2024-03-08 16:15:08', NULL, '', '2024-03-20 15:59:35', NULL, '', 0, 'COALESCE(sum(product_shipped_count),0)', NULL);
INSERT INTO `product_target_conf` VALUES (49, 'product_after_sale_rate', '商品售后发生率', 2, 'product_after_sale_count/product_shipped_count', 0, 0, 1, 'percent', 'afterSale', 0, NULL, 0, 'after_sale_detail', NULL, '', '2024-03-08 16:15:08', NULL, '', '2024-03-20 15:59:35', NULL, '', 0, 'product_after_sale_count/product_shipped_count', NULL);
INSERT INTO `product_target_conf` VALUES (50, 'sigo_sale_loss_number', '视客销售机会损失量', 1, 'sigo_sale_loss_number', 0, 0, 1, 'decimal', 'noChannel', 0, NULL, 2, NULL, 1, '', '2023-11-23 16:43:30', NULL, '', '2024-04-02 15:31:20', NULL, '', 0, 'COALESCE(sum(loss_number),0)', NULL);

SET FOREIGN_KEY_CHECKS = 1;
