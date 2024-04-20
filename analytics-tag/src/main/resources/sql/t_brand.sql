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

 Date: 20/04/2024 15:27:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_brand
-- ----------------------------
DROP TABLE IF EXISTS `t_brand`;
CREATE TABLE `t_brand`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `brand_id` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '品牌ID',
  `brand_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '品牌名称',
  `brand_property` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '品牌属性:owner:自由准自由;tp:代理;direct:经销',
  `brand_property_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '品牌属性名称',
  `brand_active` tinyint(1) NULL DEFAULT NULL COMMENT '品牌状态 0正常 1停用',
  `logic_brand_id` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '逻辑品牌id  生成',
  `logic_brand_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '逻辑品牌名称',
  `logic_brand_property` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '逻辑品牌属性:owner:自由准自由;tp:代理;direct:经销aisei 属于代理',
  `logic_brand_property_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '逻辑品牌属性名称',
  `logic_brand_active` tinyint(1) NULL DEFAULT NULL COMMENT '逻辑品牌状态 0正常 1停用',
  `logic_pay_amount_month` decimal(18, 6) NULL DEFAULT NULL COMMENT '近一个月销量 逻辑品牌 brand_id',
  `pay_amount_month` decimal(18, 6) NULL DEFAULT NULL COMMENT '近一个月销量 物理品牌 logic_brand_id',
  `is_combine` tinyint(1) NULL DEFAULT NULL COMMENT '1 是，0不是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 50829 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '逻辑品牌维表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_brand
-- ----------------------------
INSERT INTO `t_brand` VALUES (48, '0', '未知', 'other', '其他', 0, '48', '未知', 'other', '其他', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49, '1', 'BCFA', 'direct', '经销', 0, '49', 'BCFA', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (50, '2', 'BENZ', 'direct', '经销', 0, '50', 'BENZ', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (51, '3', 'BESCON', 'direct', '经销', 0, '51', 'BESCON', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (52, '4', 'BLG', 'direct', '经销', 0, '52', 'BLG', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (53, '5', 'BOA宝儿', 'direct', '经销', 0, '53', 'BOA宝儿', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (54, '6', 'BS', 'direct', '经销', 0, '54', 'BS', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (55, '7', 'CACETATE', 'direct', '经销', 0, '55', 'CACETATE', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (56, '8', 'Charmant夏蒙', 'direct', '经销', 0, '56', 'Charmant夏蒙', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (57, '9', 'CHLOE', 'direct', '经销', 0, '57', 'CHLOE', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1567, '10', 'CK', 'direct', '经销', 1, '1567', 'CK', 'direct', '经销', 1, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1568, '11', 'D&G', 'direct', '经销', 1, '1568', 'D&G', 'direct', '经销', 1, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1569, '12', 'FASHONSHOW', 'direct', '经销', 0, '1569', 'FASHONSHOW', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1570, '13', 'GUESS', 'direct', '经销', 0, '1570', 'GUESS', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1571, '14', 'ITALY DESIGN', 'direct', '经销', 0, '1571', 'ITALY DESIGN', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1572, '15', 'JIAN MING', 'direct', '经销', 0, '1572', 'JIAN MING', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1573, '16', 'JLL', 'direct', '经销', 0, '1573', 'JLL', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1574, '17', 'NEO', 'owner', '自有准自有', 0, '1574', 'NEO', 'owner', '自有准自有', 0, 895754.758540, 895754.758540, 0);
INSERT INTO `t_brand` VALUES (1575, '18', 'NIKE耐克', 'direct', '经销', 0, '1575', 'NIKE耐克', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1576, '19', 'OCCO欧蔻', 'direct', '经销', 0, '1576', 'OCCO欧蔻', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1599, '21', 'OYEA欧野', 'direct', '经销', 0, '1599', 'OYEA欧野', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1600, '22', 'POLICE', 'direct', '经销', 0, '1600', 'POLICE', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1601, '23', 'Poly', 'direct', '经销', 0, '1601', 'Poly', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1602, '24', 'Prada普拉达', 'direct', '经销', 0, '1602', 'Prada普拉达', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1603, '25', 'PUMA彪马', 'direct', '经销', 0, '1603', 'PUMA彪马', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1604, '26', 'Renoma', 'direct', '经销', 0, '1604', 'Renoma', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1605, '27', 'SECG', 'direct', '经销', 0, '1605', 'SECG', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1606, '28', 'sigo视客', 'direct', '经销', 0, '1606', 'sigo视客', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1607, '29', 'SNOOPY', 'direct', '经销', 1, '1607', 'SNOOPY', 'direct', '经销', 1, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1629, '30', 'US', 'direct', '经销', 0, '1629', 'US', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1630, '31', '阿格莱亚', 'direct', '经销', 0, '1630', '阿格莱亚', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1631, '32', '阿利安', 'direct', '经销', 0, '1631', '阿利安', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1632, '33', '艾爵', 'direct', '经销', 0, '1632', '艾爵', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1633, '34', '爱尔康', 'direct', '经销', 0, '1633', '爱尔康', 'direct', '经销', 0, 1484138.777830, 1484138.777830, 0);
INSERT INTO `t_brand` VALUES (1634, '35', '傲视', 'direct', '经销', 0, '1634', '傲视', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1635, '36', '奥克拉', 'direct', '经销', 0, '1635', '奥克拉', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1636, '37', '澳洲袋鼠', 'direct', '经销', 0, '1636', '澳洲袋鼠', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1637, '38', '巴黎工坊', 'direct', '经销', 0, '1637', '巴黎工坊', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1638, '39', '芭比', 'direct', '经销', 0, '1638', '芭比', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1660, '40', '邦尼', 'direct', '经销', 0, '1660', '邦尼', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1661, '41', '邦士度', 'direct', '经销', 0, '1661', '邦士度', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1662, '42', '宝丽来', 'direct', '经销', 0, '1662', '宝丽来', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1663, '43', '宝姿', 'direct', '经销', 1, '1663', '宝姿', 'direct', '经销', 1, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1664, '44', '保圣', 'direct', '经销', 0, '1664', '保圣', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1665, '45', '保视宁', 'direct', '经销', 1, '1665', '保视宁', 'direct', '经销', 1, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1666, '46', '暴龙', 'direct', '经销', 0, '1666', '暴龙', 'direct', '经销', 0, 4267.990000, 4267.990000, 0);
INSERT INTO `t_brand` VALUES (1667, '47', '冰鱼', 'direct', '经销', 0, '1667', '冰鱼', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1668, '48', '博莱雅', 'direct', '经销', 0, '1668', '博莱雅', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1669, '49', '博士伦', 'direct', '经销', 0, '1669', '博士伦', 'direct', '经销', 0, 6791101.155400, 6791101.155400, 0);
INSERT INTO `t_brand` VALUES (1691, '50', '博视顿', 'direct', '经销', 0, '1691', '博视顿', 'direct', '经销', 0, 1884653.096910, 1884653.096910, 0);
INSERT INTO `t_brand` VALUES (1692, '51', '纯睛', 'direct', '经销', 0, '1692', '纯睛', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1694, '53', '迪克逊', 'direct', '经销', 0, '1694', '迪克逊', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1695, '54', '笛可纯钛', 'direct', '经销', 0, '1695', '笛可纯钛', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1696, '55', '帝陀DITUO', 'direct', '经销', 0, '1696', '帝陀DITUO', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1697, '56', '鼎霸', 'direct', '经销', 0, '1697', '鼎霸', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1698, '57', '范思哲', 'direct', '经销', 1, '1698', '范思哲', 'direct', '经销', 1, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1699, '58', '菲拉格慕', 'direct', '经销', 1, '1699', '菲拉格慕', 'direct', '经销', 1, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1700, '59', '菲律康', 'direct', '经销', 0, '1700', '菲律康', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1722, '60', '沸典', 'direct', '经销', 0, '1722', '沸典', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1723, '61', 'test2', 'direct', '经销', 1, '1723', 'test2', 'direct', '经销', 1, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1724, '62', '哥拓普', 'direct', '经销', 0, '1724', '哥拓普', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1725, '63', '光美', 'direct', '经销', 0, '1725', '光美', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1726, '64', '海昌', 'direct', '经销', 0, '1726', '海昌', 'direct', '经销', 0, 1248651.770180, 1248651.770180, 0);
INSERT INTO `t_brand` VALUES (1727, '65', '海伦•凯勒', 'direct', '经销', 0, '1727', '海伦•凯勒', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1728, '66', '海视', 'direct', '经销', 0, '1728', '海视', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1729, '67', '海豚', 'direct', '经销', 0, '1729', '海豚', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1730, '68', '韩国inter', 'direct', '经销', 0, '1730', '韩国inter', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1731, '69', '韩国IR', 'direct', '经销', 0, '1731', '韩国IR', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1753, '70', '韩国solb', 'direct', '经销', 0, '1753', '韩国solb', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1754, '71', '韩国SOLE’ S', 'direct', '经销', 0, '1754', '韩国SOLE’ S', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1755, '72', '韩国金属板材', 'direct', '经销', 0, '1755', '韩国金属板材', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1756, '73', '皇家乐园', 'direct', '经销', 0, '1756', '皇家乐园', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1757, '74', '灰姑娘', 'direct', '经销', 0, '1757', '灰姑娘', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1758, '75', '活动特价', 'direct', '经销', 0, '1758', '活动特价', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1759, '76', '捷佳', 'direct', '经销', 0, '1759', '捷佳', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1760, '77', '晶采视界', 'direct', '经销', 0, '1760', '晶采视界', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1761, '78', '晶彩', 'direct', '经销', 0, '1761', '晶彩', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1762, '79', '精华帝康', 'direct', '经销', 0, '1762', '精华帝康', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1785, '81', '卡奥思', 'direct', '经销', 1, '1785', '卡奥思', 'direct', '经销', 1, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1786, '82', '卡帝奥尼', 'direct', '经销', 0, '1786', '卡帝奥尼', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1787, '83', '卡帝乐CARTELO', 'direct', '经销', 0, '1787', '卡帝乐CARTELO', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1788, '84', '卡莱迪', 'direct', '经销', 0, '1788', '卡莱迪', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1789, '85', '卡诺', 'direct', '经销', 0, '1789', '卡诺', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1790, '86', '凯达', 'direct', '经销', 0, '1790', '凯达', 'direct', '经销', 0, 15418.381470, 15418.381470, 0);
INSERT INTO `t_brand` VALUES (1791, '87', '科莱博', 'direct', '经销', 0, '1791', '科莱博', 'direct', '经销', 0, 1930.080000, 1930.080000, 0);
INSERT INTO `t_brand` VALUES (1792, '88', '肯地雅', 'direct', '经销', 0, '1792', '肯地雅', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1793, '89', '库博', 'direct', '经销', 0, '1793', '库博', 'direct', '经销', 0, 6486315.301990, 6486315.301990, 0);
INSERT INTO `t_brand` VALUES (1815, '90', '酷视', 'direct', '经销', 0, '1815', '酷视', 'direct', '经销', 0, 23458.270000, 23458.270000, 0);
INSERT INTO `t_brand` VALUES (1816, '91', '莱恩波仕', 'direct', '经销', 1, '1816', '莱恩波仕', 'direct', '经销', 1, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1817, '92', '蓝睛灵', 'direct', '经销', 0, '1817', '蓝睛灵', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1818, '93', '蓝色沸点', 'direct', '经销', 0, '1818', '蓝色沸点', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1819, '94', '浪特梦', 'direct', '经销', 0, '1819', '浪特梦', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1820, '95', '雷朋', 'direct', '经销', 0, '1820', '雷朋', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1821, '96', '李维斯', 'direct', '经销', 0, '1821', '李维斯', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1822, '97', '理查德', 'direct', '经销', 0, '1822', '理查德', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1823, '98', '亮视', 'direct', '经销', 0, '1823', '亮视', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (1824, '99', '零距离', 'direct', '经销', 0, '1824', '零距离', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48625, '100', '柳川一郎', 'direct', '经销', 1, '48625', '柳川一郎', 'direct', '经销', 1, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48626, '101', '美欣', 'direct', '经销', 0, '48626', '美欣', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48627, '102', '名轩', 'direct', '经销', 0, '48627', '名轩', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48628, '103', '纽尚', 'direct', '经销', 0, '48628', '纽尚', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48629, '104', '诺帝卡', 'direct', '经销', 0, '48629', '诺帝卡', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48630, '105', '欧博', 'direct', '经销', 0, '48630', '欧博', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48631, '106', '派丽蒙', 'direct', '经销', 0, '48631', '派丽蒙', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48632, '107', '皮尔卡丹', 'direct', '经销', 0, '48632', '皮尔卡丹', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48633, '108', '其他', 'direct', '经销', 0, '48633', '其他', 'direct', '经销', 0, 190632.930860, 190632.930860, 0);
INSERT INTO `t_brand` VALUES (48634, '109', '其他小商品', 'direct', '经销', 0, '48634', '其他小商品', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48656, '110', '奇亮', 'direct', '经销', 0, '48656', '奇亮', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48657, '111', '强生', 'direct', '经销', 0, '48657', '强生', 'direct', '经销', 0, 8539962.826850, 8539962.826850, 0);
INSERT INTO `t_brand` VALUES (48658, '112', '全能', 'direct', '经销', 0, '48658', '全能', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48659, '113', '沙福隆', 'direct', '经销', 0, '48659', '沙福隆', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48660, '114', '胜腾', 'direct', '经销', 0, '48660', '胜腾', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48661, '115', '圣迪奥', 'direct', '经销', 0, '48661', '圣迪奥', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48662, '116', '圣笛', 'direct', '经销', 0, '48662', '圣笛', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48663, '117', '施洛华', 'direct', '经销', 0, '48663', '施洛华', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48664, '118', '视康', 'direct', '经销', 0, '48664', '视康', 'direct', '经销', 0, 1779157.252100, 1779157.252100, 0);
INSERT INTO `t_brand` VALUES (48665, '119', '视客', 'direct', '经销', 0, '48665', '视客', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48687, '120', '视客钛架', 'direct', '经销', 0, '48687', '视客钛架', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48688, '121', '舒洁', 'direct', '经销', 0, '48688', '舒洁', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48689, '122', '测试水汪汪', 'owner', '自有准自有', 0, '48689', '测试水汪汪', 'owner', '自有准自有', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48690, '123', '天骑', 'direct', '经销', 0, '48690', '天骑', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48691, '124', '外贸精品', 'direct', '经销', 0, '48691', '外贸精品', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48692, '125', '维斯', 'direct', '经销', 0, '48692', '维斯', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48693, '126', '卫康', 'direct', '经销', 0, '48693', '卫康', 'direct', '经销', 0, 813871.304290, 813871.304290, 0);
INSERT INTO `t_brand` VALUES (48694, '127', '夕阳伴侣', 'direct', '经销', 0, '48694', '夕阳伴侣', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48695, '128', '新加坡P2', 'direct', '经销', 0, '48695', '新加坡P2', 'direct', '经销', 0, 27868.920000, 27868.920000, 0);
INSERT INTO `t_brand` VALUES (48696, '129', '新派克', 'direct', '经销', 0, '48696', '新派克', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48718, '130', '雪龙', 'direct', '经销', 0, '48718', '雪龙', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48719, '131', '伊丽视', 'direct', '经销', 1, '48719', '伊丽视', 'direct', '经销', 1, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48720, '132', '银潮', 'direct', '经销', 0, '48720', '银潮', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48721, '133', '盈彩', 'direct', '经销', 0, '48721', '盈彩', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48722, '134', '中港', 'direct', '经销', 0, '48722', '中港', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48723, '135', '自然美', 'direct', '经销', 0, '48723', '自然美', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48724, '136', '贝尔莫', 'direct', '经销', 0, '48724', '贝尔莫', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48726, '138', '安格尔', 'direct', '经销', 0, '48726', '安格尔', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48727, '139', '水密码', 'direct', '经销', 0, '48727', '水密码', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48751, '142', '实瞳', 'direct', '经销', 0, '48751', '实瞳', 'direct', '经销', 0, 391796.964890, 391796.964890, 0);
INSERT INTO `t_brand` VALUES (48752, '143', '可丽妍', 'direct', '经销', 0, '48752', '可丽妍', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48754, '145', '3M口罩', 'direct', '经销', 0, '48754', '3M口罩', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48755, '146', '韩后', 'direct', '经销', 0, '48755', '韩后', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48756, '147', '碧润', 'direct', '经销', 0, '48756', '碧润', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48757, '148', '朵米娜', 'direct', '经销', 0, '48757', '朵米娜', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48758, '149', '雅士', 'direct', '经销', 0, '48758', '雅士', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48780, '150', '酷柏', 'direct', '经销', 0, '48780', '酷柏', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48781, '151', '布利克BLICK', 'direct', '经销', 0, '48781', '布利克BLICK', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48782, '152', '丝路', 'direct', '经销', 0, '48782', '丝路', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48783, '153', '海俪恩', 'direct', '经销', 0, '48783', '海俪恩', 'direct', '经销', 0, 791506.016590, 791506.016590, 0);
INSERT INTO `t_brand` VALUES (48784, '154', 'vogue', 'direct', '经销', 0, '48784', 'vogue', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48785, '155', '科尔视', 'direct', '经销', 0, '48785', '科尔视', 'direct', '经销', 0, 1610.680000, 1610.680000, 0);
INSERT INTO `t_brand` VALUES (48786, '156', '迪士尼', 'direct', '经销', 0, '48786', '迪士尼', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48787, '157', '韩国妙递', 'direct', '经销', 0, '48787', '韩国妙递', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48788, '158', '趴趴熊', 'direct', '经销', 0, '48788', '趴趴熊', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48789, '159', '优能', 'direct', '经销', 0, '48789', '优能', 'direct', '经销', 0, 9684.460000, 9684.460000, 0);
INSERT INTO `t_brand` VALUES (48811, '160', '年轻态', 'direct', '经销', 1, '48811', '年轻态', 'direct', '经销', 1, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48812, '161', 'GEO', 'direct', '经销', 0, '48812', 'GEO', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48813, '162', '恋彩', 'direct', '经销', 0, '48813', '恋彩', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48814, '163', '水密语', 'direct', '经销', 0, '48814', '水密语', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48815, '164', 'happynco', 'direct', '经销', 0, '48815', 'happynco', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48816, '165', '凡歌', 'direct', '经销', 0, '48816', '凡歌', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48817, '166', '洁达', 'direct', '经销', 0, '48817', '洁达', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48818, '167', '欧瑞明', 'direct', '经销', 0, '48818', '欧瑞明', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48819, '168', '美光', 'direct', '经销', 0, '48819', '美光', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48820, '169', 'G&G ', 'direct', '经销', 0, '48820', 'G&G ', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48842, '170', '芳姿', 'direct', '经销', 1, '48842', '芳姿', 'direct', '经销', 1, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48843, '171', '美汐', 'direct', '经销', 0, '48843', '美汐', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48844, '172', '菲士康', 'direct', '经销', 0, '48844', '菲士康', 'direct', '经销', 0, 200.690000, 200.690000, 0);
INSERT INTO `t_brand` VALUES (48845, '173', '安瞳', 'direct', '经销', 0, '48845', '安瞳', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48846, '174', '澜柏', 'direct', '经销', 0, '48846', '澜柏', 'direct', '经销', 0, 46556.330000, 46556.330000, 0);
INSERT INTO `t_brand` VALUES (48847, '175', '保丽', 'direct', '经销', 0, '48847', '保丽', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48848, '176', '真维斯', 'direct', '经销', 0, '48848', '真维斯', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48849, '177', '雪柔', 'direct', '经销', 0, '48849', '雪柔', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48850, '178', '欧威视', 'direct', '经销', 0, '48850', '欧威视', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48851, '179', '贝黎诗', 'direct', '经销', 0, '48851', '贝黎诗', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48873, '180', '有华', 'direct', '经销', 0, '48873', '有华', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48874, '181', '艾乐视', 'direct', '经销', 0, '48874', '艾乐视', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48876, '183', '妆美堂', 'direct', '经销', 0, '48876', '妆美堂', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48877, '184', '永爱', 'direct', '经销', 0, '48877', '永爱', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48878, '185', '薇薇爱', 'direct', '经销', 0, '48878', '薇薇爱', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48879, '186', '可米路', 'direct', '经销', 0, '48879', '可米路', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48880, '187', '珂俪维', 'direct', '经销', 0, '48880', '珂俪维', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48881, '188', '目立康', 'direct', '经销', 0, '48881', '目立康', 'direct', '经销', 0, 3282165.368070, 3282165.368070, 0);
INSERT INTO `t_brand` VALUES (48882, '189', '蔡司', 'direct', '经销', 0, '48882', '蔡司', 'direct', '经销', 0, 117628.589860, 117628.589860, 0);
INSERT INTO `t_brand` VALUES (48905, '191', '可丽博', 'direct', '经销', 1, '48905', '可丽博', 'direct', '经销', 1, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48906, '192', '培克能', 'direct', '经销', 0, '48906', '培克能', 'direct', '经销', 0, 462557.501700, 462557.501700, 0);
INSERT INTO `t_brand` VALUES (48907, '193', '思汉普', 'direct', '经销', 0, '48907', '思汉普', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48908, '194', 'KKR', 'direct', '经销', 1, '48908', 'KKR', 'direct', '经销', 1, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48909, '195', '明月', 'direct', '经销', 1, '48909', '明月', 'direct', '经销', 1, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48910, '196', '爱芙丽', 'direct', '经销', 0, '48910', '爱芙丽', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48911, '197', '伊厶康', 'direct', '经销', 0, '48911', '伊厶康', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48912, '198', '依视路', 'direct', '经销', 1, '48912', '依视路', 'direct', '经销', 1, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (48913, '199', '我的秘密日记', 'direct', '经销', 0, '48913', '我的秘密日记', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49586, '200', '晶硕', 'direct', '经销', 0, '49586', '晶硕', 'direct', '经销', 0, 258475.139260, 258475.139260, 0);
INSERT INTO `t_brand` VALUES (49587, '201', '洁康', 'direct', '经销', 1, '49587', '洁康', 'direct', '经销', 1, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49588, '202', '茵洛', 'direct', '经销', 0, '49588', '茵洛', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49589, '203', '安洁', 'direct', '经销', 0, '49589', '安洁', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49590, '204', '高视能', 'direct', '经销', 0, '49590', '高视能', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49591, '205', '米如', 'direct', '经销', 0, '49591', '米如', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49592, '206', 'bingbing', 'direct', '经销', 0, '49592', 'bingbing', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49593, '207', '美若康', 'tp', '代理', 0, '49593', '美若康', 'tp', '代理', 0, 18123812.052960, 18123812.052960, 0);
INSERT INTO `t_brand` VALUES (49594, '208', '尤妮佳', 'direct', '经销', 1, '49594', '尤妮佳', 'direct', '经销', 1, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49595, '209', '雅培', 'direct', '经销', 0, '49595', '雅培', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49617, '210', 'CeraVe肌润源', 'direct', '经销', 1, '49617', 'CeraVe肌润源', 'direct', '经销', 1, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49618, '211', '优卓', 'direct', '经销', 0, '49618', '优卓', 'direct', '经销', 0, 325867.090000, 325867.090000, 0);
INSERT INTO `t_brand` VALUES (49619, '212', '菲诗小铺', 'direct', '经销', 0, '49619', '菲诗小铺', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49620, '213', '护妍天使', 'direct', '经销', 0, '49620', '护妍天使', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49621, '214', '久舞', 'direct', '经销', 0, '49621', '久舞', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49622, '215', '3N', 'direct', '经销', 0, '49622', '3N', 'direct', '经销', 0, 290.043000, 290.043000, 0);
INSERT INTO `t_brand` VALUES (49623, '216', '安娜苏', 'direct', '经销', 0, '49623', '安娜苏', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49624, '217', '花印', 'direct', '经销', 0, '49624', '花印', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49625, '218', 'LALISH', 'tp', '代理', 0, '92823343', 'aisei', 'tp', '代理', 0, 3588481.214630, 1597152.994290, 1);
INSERT INTO `t_brand` VALUES (49626, '219', 'CandyMagic', 'direct', '经销', 0, '49626', 'CandyMagic', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49648, '220', '新视野', 'direct', '经销', 0, '49648', '新视野', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49649, '221', 'YOUHOO', 'direct', '经销', 0, '49649', 'YOUHOO', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49650, '222', '妃蜜莉', 'direct', '经销', 0, '49650', '妃蜜莉', 'direct', '经销', 0, 81423.219840, 81423.219840, 0);
INSERT INTO `t_brand` VALUES (49652, '224', '奥普铁克', 'direct', '经销', 0, '49652', '奥普铁克', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49653, '225', '镜特舒', 'direct', '经销', 0, '49653', '镜特舒', 'direct', '经销', 0, 2223197.979810, 2223197.979810, 0);
INSERT INTO `t_brand` VALUES (49654, '226', 'DAZZSHOP', 'direct', '经销', 0, '49654', 'DAZZSHOP', 'direct', '经销', 0, 4695.920000, 4695.920000, 0);
INSERT INTO `t_brand` VALUES (49655, '227', 'MAC魅可', 'direct', '经销', 0, '49655', 'MAC魅可', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49656, '228', '元气', 'direct', '经销', 0, '49656', '元气', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49657, '229', '绮芙莉', 'direct', '经销', 0, '49657', '绮芙莉', 'direct', '经销', 0, 759286.486460, 759286.486460, 0);
INSERT INTO `t_brand` VALUES (49679, '230', 'Utour', 'direct', '经销', 0, '49679', 'Utour', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49680, '231', 'EYEFREE', 'direct', '经销', 0, '49680', 'EYEFREE', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49681, '232', '乐敦清', 'direct', '经销', 0, '49681', '乐敦清', 'direct', '经销', 0, 154683.636930, 154683.636930, 0);
INSERT INTO `t_brand` VALUES (49682, '233', '人鱼姬', 'owner', '自有准自有', 0, '49682', '人鱼姬', 'owner', '自有准自有', 0, 4680235.663640, 4680235.663640, 0);
INSERT INTO `t_brand` VALUES (49683, '234', '拉拜诗', 'owner', '自有准自有', 0, '49683', '拉拜诗', 'owner', '自有准自有', 0, 78159052.817080, 78159052.817080, 0);
INSERT INTO `t_brand` VALUES (49684, '235', '戴森', 'direct', '经销', 0, '49684', '戴森', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49685, '236', 'SK-II', 'direct', '经销', 0, '49685', 'SK-II', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49686, '237', '汤姆福特', 'direct', '经销', 0, '49686', '汤姆福特', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49687, '238', 'HolyNara', 'direct', '经销', 0, '49687', 'HolyNara', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49688, '239', 'kename', 'owner', '自有准自有', 0, '49688', 'kename', 'owner', '自有准自有', 0, 10503.340000, 10503.340000, 0);
INSERT INTO `t_brand` VALUES (49710, '240', 'ReVIA蕾美', 'direct', '经销', 0, '49710', 'ReVIA蕾美', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49711, '241', '爱如久', 'tp', '代理', 0, '92823343', 'aisei', 'tp', '代理', 0, 3588481.214630, 56211.830000, 1);
INSERT INTO `t_brand` VALUES (49712, '242', '海淘EverColor', 'direct', '经销', 0, '92823343', 'aisei', 'tp', '代理', 0, 3588481.214630, 118184.150000, 1);
INSERT INTO `t_brand` VALUES (49713, '243', 'NeoSight', 'direct', '经销', 0, '49713', 'NeoSight', 'direct', '经销', 0, 5484.320000, 5484.320000, 0);
INSERT INTO `t_brand` VALUES (49714, '244', 'O-LENS', 'direct', '经销', 0, '49714', 'O-LENS', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49715, '245', '伊视', 'direct', '经销', 0, '49715', '伊视', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49716, '246', '爱能视', 'direct', '经销', 0, '49716', '爱能视', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49717, '247', 'FLANMY', 'tp', '代理', 0, '49717', 'FLANMY', 'tp', '代理', 0, 15484457.343800, 15484457.343800, 0);
INSERT INTO `t_brand` VALUES (49718, '248', '百视安', 'direct', '经销', 0, '49718', '百视安', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49719, '249', 'Envie', 'direct', '经销', 0, '49719', 'Envie', 'direct', '经销', 0, 299252.420000, 299252.420000, 0);
INSERT INTO `t_brand` VALUES (49741, '250', '补差价商品专用品牌', 'direct', '经销', 0, '49741', '补差价商品专用品牌', 'direct', '经销', 0, 5.000000, 5.000000, 0);
INSERT INTO `t_brand` VALUES (49742, '251', '依视明', 'direct', '经销', 0, '49742', '依视明', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49743, '252', '美分子', 'direct', '经销', 0, '49743', '美分子', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49744, '253', '百肤美', 'direct', '经销', 0, '49744', '百肤美', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49745, '254', '魔净', 'direct', '经销', 0, '49745', '魔净', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49746, '255', 'TONYMOLY', 'direct', '经销', 0, '49746', 'TONYMOLY', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49747, '256', '陌森', 'direct', '经销', 0, '49747', '陌森', 'direct', '经销', 0, 60371.890000, 60371.890000, 0);
INSERT INTO `t_brand` VALUES (49748, '257', 'iClear', 'direct', '经销', 0, '49748', 'iClear', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49749, '258', 'Refrear', 'direct', '经销', 0, '49749', 'Refrear', 'direct', '经销', 0, 359.750000, 359.750000, 0);
INSERT INTO `t_brand` VALUES (49750, '259', 'BIJOU', 'direct', '经销', 0, '49750', 'BIJOU', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49772, '260', 'LuMia', 'direct', '经销', 0, '49772', 'LuMia', 'direct', '经销', 0, 2907.100000, 2907.100000, 0);
INSERT INTO `t_brand` VALUES (49773, '261', 'laDéesse女神秘语', 'direct', '经销', 0, '49773', 'laDéesse女神秘语', 'direct', '经销', 0, 184649.754290, 184649.754290, 0);
INSERT INTO `t_brand` VALUES (49774, '262', 'ACUVUE', 'direct', '经销', 0, '49774', 'ACUVUE', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49775, '263', 'Alcon', 'direct', '经销', 0, '49775', 'Alcon', 'direct', '经销', 0, 8721.020000, 8721.020000, 0);
INSERT INTO `t_brand` VALUES (49776, '264', 'CooperVision', 'direct', '经销', 0, '49776', 'CooperVision', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49777, '265', 'Miacare', 'tp', '代理', 0, '49777', 'Miacare', 'tp', '代理', 0, 106143.070000, 106143.070000, 0);
INSERT INTO `t_brand` VALUES (49781, '269', '优珞美', 'direct', '经销', 0, '49781', '优珞美', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49803, '270', 'SHERIQUE', 'direct', '经销', 0, '49803', 'SHERIQUE', 'direct', '经销', 0, 78.380000, 78.380000, 0);
INSERT INTO `t_brand` VALUES (49804, '271', 'LILMOON', 'direct', '经销', 0, '49804', 'LILMOON', 'direct', '经销', 0, 2791.840000, 2791.840000, 0);
INSERT INTO `t_brand` VALUES (49805, '272', 'TOPARDS', 'direct', '经销', 0, '49805', 'TOPARDS', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49806, '273', 'Diya', 'direct', '经销', 0, '49806', 'Diya', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49807, '274', '瞳颜', 'direct', '经销', 0, '49807', '瞳颜', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49808, '275', '瞳昕', 'direct', '经销', 0, '49808', '瞳昕', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49809, '276', '萌哆奇', 'direct', '经销', 0, '49809', '萌哆奇', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49810, '277', 'RICH STANDARD', 'direct', '经销', 0, '49810', 'RICH STANDARD', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49811, '278', 'Luna', 'direct', '经销', 0, '49811', 'Luna', 'direct', '经销', 0, 211.230000, 211.230000, 0);
INSERT INTO `t_brand` VALUES (49812, '279', 'LENSTOWN', 'direct', '经销', 0, '49812', 'LENSTOWN', 'direct', '经销', 0, 156.760000, 156.760000, 0);
INSERT INTO `t_brand` VALUES (49834, '280', 'RICHBABY', 'direct', '经销', 0, '49834', 'RICHBABY', 'direct', '经销', 0, 213.910000, 213.910000, 0);
INSERT INTO `t_brand` VALUES (49835, '281', '艾绮拉', 'direct', '经销', 0, '49835', '艾绮拉', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49836, '282', 'MAXLOOK', 'direct', '经销', 0, '49836', 'MAXLOOK', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49837, '283', 'iTH', 'direct', '经销', 0, '49837', 'iTH', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49838, '284', 'T-Garden', 'tp', '代理', 0, '49838', 'T-Garden', 'tp', '代理', 0, 17816122.152130, 17816122.152130, 0);
INSERT INTO `t_brand` VALUES (49839, '285', '眸论', 'direct', '经销', 0, '49839', '眸论', 'direct', '经销', 0, 2402.970000, 2402.970000, 0);
INSERT INTO `t_brand` VALUES (49840, '286', '海兀', 'direct', '经销', 0, '49840', '海兀', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49841, '287', '久爱妮', 'tp', '代理', 0, '92823343', 'aisei', 'tp', '代理', 0, 3588481.214630, 26055.720000, 1);
INSERT INTO `t_brand` VALUES (49865, '290', '星欧', 'direct', '经销', 0, '49865', '星欧', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49866, '291', 'Ophthalab', 'owner', '自有准自有', 0, '49866', 'Ophthalab', 'owner', '自有准自有', 0, 2309186.404730, 2309186.404730, 0);
INSERT INTO `t_brand` VALUES (49867, '292', '啵啵实验室', 'direct', '经销', 0, '49867', '啵啵实验室', 'direct', '经销', 0, 28008.670000, 28008.670000, 0);
INSERT INTO `t_brand` VALUES (49868, '293', 'ENA', 'direct', '经销', 0, '49868', 'ENA', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49869, '294', '乐森林', 'direct', '经销', 0, '49869', '乐森林', 'direct', '经销', 0, 3619.330000, 3619.330000, 0);
INSERT INTO `t_brand` VALUES (49870, '295', '海丽视', 'owner', '自有准自有', 0, '49870', '海丽视', 'owner', '自有准自有', 0, 6031.130000, 6031.130000, 0);
INSERT INTO `t_brand` VALUES (49871, '296', 'MOLAK', 'direct', '经销', 0, '49871', 'MOLAK', 'direct', '经销', 0, 1508.060000, 1508.060000, 0);
INSERT INTO `t_brand` VALUES (49872, '297', 'Fashionista', 'direct', '经销', 0, '49872', 'Fashionista', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (49873, '298', 'LUCE', 'direct', '经销', 0, '49873', 'LUCE', 'direct', '经销', 0, 266.000000, 266.000000, 0);
INSERT INTO `t_brand` VALUES (49874, '299', 'N’s COLLECTION', 'direct', '经销', 0, '49874', 'N’s COLLECTION', 'direct', '经销', 0, 760.480000, 760.480000, 0);
INSERT INTO `t_brand` VALUES (50547, '300', 'Lilou', 'direct', '经销', 0, '50547', 'Lilou', 'direct', '经销', 0, 89.860000, 89.860000, 0);
INSERT INTO `t_brand` VALUES (50549, '302', 'EYEPONY', 'owner', '自有准自有', 0, '50549', 'EYEPONY', 'owner', '自有准自有', 0, 3159380.443600, 3159380.443600, 0);
INSERT INTO `t_brand` VALUES (50550, '303', 'SAP思汉普', 'direct', '经销', 0, '50550', 'SAP思汉普', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (50551, '304', '强生全视', 'direct', '经销', 0, '50551', '强生全视', 'direct', '经销', 0, 57572.238300, 57572.238300, 0);
INSERT INTO `t_brand` VALUES (50552, '305', 'Lifeshow生活秀', 'direct', '经销', 0, '50552', 'Lifeshow生活秀', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (50553, '306', 'TUU', 'direct', '经销', 0, '50553', 'TUU', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (50554, '307', 'Johnson&Johnson', 'direct', '经销', 0, '50554', 'Johnson&Johnson', 'direct', '经销', 0, 1011687.480000, 1011687.480000, 0);
INSERT INTO `t_brand` VALUES (50555, '308', 'Bausch&Lomb', 'direct', '经销', 0, '50555', 'Bausch&Lomb', 'direct', '经销', 0, 125914.400000, 125914.400000, 0);
INSERT INTO `t_brand` VALUES (50556, '309', 'Miche bloomin', 'direct', '经销', 0, '50556', 'Miche bloomin', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (50578, '310', '安比', 'direct', '经销', 0, '50578', '安比', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (50579, '311', 'BIOCLEN', 'direct', '经销', 0, '50579', 'BIOCLEN', 'direct', '经销', 0, 80431.840000, 80431.840000, 0);
INSERT INTO `t_brand` VALUES (50580, '312', 'CREO', 'direct', '经销', 0, '50580', 'CREO', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (50581, '313', '帕莎', 'direct', '经销', 0, '50581', '帕莎', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (50582, '314', 'FURLA', 'direct', '经销', 0, '50582', 'FURLA', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (50583, '315', 'CHOPARD', 'direct', '经销', 0, '50583', 'CHOPARD', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (50584, '316', '夕阳红', 'direct', '经销', 0, '50584', '夕阳红', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (50585, '317', '欧舒天', 'owner', '自有准自有', 1, '50585', '欧舒天', 'owner', '自有准自有', 1, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (50587, '319', '贰次圆', 'direct', '经销', 0, '50587', '贰次圆', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (50609, '320', '眼舒康', 'direct', '经销', 0, '50609', '眼舒康', 'direct', '经销', 0, 3435.080000, 3435.080000, 0);
INSERT INTO `t_brand` VALUES (50610, '321', '熙臻', 'direct', '经销', 0, '50610', '熙臻', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (50611, '322', 'GLAM UP', 'owner', '自有准自有', 0, '50611', 'GLAM UP', 'owner', '自有准自有', 0, 1498184.301230, 1498184.301230, 0);
INSERT INTO `t_brand` VALUES (50612, '323', 'iNYX', 'owner', '自有准自有', 0, '50612', 'iNYX', 'owner', '自有准自有', 0, 2969363.268990, 2969363.268990, 0);
INSERT INTO `t_brand` VALUES (50613, '324', '必优目viewm', 'tp', '代理', 0, '92823343', 'aisei', 'tp', '代理', 0, 3588481.214630, 11511.799250, 1);
INSERT INTO `t_brand` VALUES (50614, '325', 'CoFANCY', 'direct', '经销', 0, '50614', 'CoFANCY', 'direct', '经销', 0, 307547.948890, 307547.948890, 0);
INSERT INTO `t_brand` VALUES (50615, '326', 'Ote', 'tp', '代理', 0, '50615', 'Ote', 'tp', '代理', 0, 185069.320000, 185069.320000, 0);
INSERT INTO `t_brand` VALUES (50616, '327', '诺清', 'direct', '经销', 0, '50616', '诺清', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (50617, '328', '美国夸克', 'direct', '经销', 0, '50617', '美国夸克', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (50618, '329', 'Origin Envy', 'direct', '经销', 0, '50618', 'Origin Envy', 'direct', '经销', 0, 4200.750000, 4200.750000, 0);
INSERT INTO `t_brand` VALUES (50640, '330', 'Olola', 'direct', '经销', 0, '50640', 'Olola', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (50641, '331', '瑞尔康', 'direct', '经销', 0, '50641', '瑞尔康', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (50642, '332', 'ANESTHESIA ', 'direct', '经销', 0, '50642', 'ANESTHESIA ', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (50643, '333', '优菲视unifresh', 'direct', '经销', 0, '50643', '优菲视unifresh', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (50644, '334', 'colors1day', 'direct', '经销', 0, '50644', 'colors1day', 'direct', '经销', 0, 23238.880000, 23238.880000, 0);
INSERT INTO `t_brand` VALUES (50645, '335', 'Flurry', 'tp', '代理', 0, '50645', 'Flurry', 'tp', '代理', 0, 444146.550000, 444146.550000, 0);
INSERT INTO `t_brand` VALUES (50646, '336', '海淘Airlens', 'tp', '代理', 0, '50646', '海淘Airlens', 'tp', '代理', 0, 226569.150000, 226569.150000, 0);
INSERT INTO `t_brand` VALUES (50648, '338', 'Motecon', 'direct', '经销', 0, '50648', 'Motecon', 'direct', '经销', 0, 1180.630000, 1180.630000, 0);
INSERT INTO `t_brand` VALUES (50649, '339', 'LENSME', 'tp', '代理', 0, '50649', 'LENSME', 'tp', '代理', 0, 43360.700000, 43360.700000, 0);
INSERT INTO `t_brand` VALUES (50671, '340', '氧视加', 'tp', '代理', 0, '50671', '氧视加', 'tp', '代理', 0, 6174.670000, 6174.670000, 0);
INSERT INTO `t_brand` VALUES (50672, '341', 'SEED', 'direct', '经销', 0, '50672', 'SEED', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (50673, '342', '森之漾', 'direct', '经销', 0, '50673', '森之漾', 'direct', '经销', 0, 207435.195920, 207435.195920, 0);
INSERT INTO `t_brand` VALUES (50674, '343', 'MENICON', 'direct', '经销', 0, '50674', 'MENICON', 'direct', '经销', 0, 846.160000, 846.160000, 0);
INSERT INTO `t_brand` VALUES (50675, '344', '海淘T-Garden', 'tp', '代理', 0, '50675', '海淘T-Garden', 'tp', '代理', 0, 198583.560000, 198583.560000, 0);
INSERT INTO `t_brand` VALUES (50676, '345', '海淘Flanmy', 'tp', '代理', 0, '50676', '海淘Flanmy', 'tp', '代理', 0, 148844.620000, 148844.620000, 0);
INSERT INTO `t_brand` VALUES (50677, '346', '可啦啦', 'direct', '经销', 0, '50677', '可啦啦', 'direct', '经销', 0, 66852.470000, 66852.470000, 0);
INSERT INTO `t_brand` VALUES (50678, '347', '焕晰 HUAN XI', 'direct', '经销', 0, '50678', '焕晰 HUAN XI', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (50679, '348', '硬镜汇 × 腾康汇医诊所', 'direct', '经销', 0, '50679', '硬镜汇 × 腾康汇医诊所', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (50680, '349', 'Moody', 'direct', '经销', 0, '50680', 'Moody', 'direct', '经销', 0, 370555.107030, 370555.107030, 0);
INSERT INTO `t_brand` VALUES (50702, '350', 'BOSTON', 'direct', '经销', 0, '50702', 'BOSTON', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (50703, '351', '普诺瞳', 'direct', '经销', 0, '50703', '普诺瞳', 'direct', '经销', 0, 12662.709950, 12662.709950, 0);
INSERT INTO `t_brand` VALUES (50704, '352', '测试品牌1', 'direct', '经销', 1, '50704', '测试品牌1', 'direct', '经销', 1, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (50711, '359', '哲高', 'direct', '经销', 0, '50711', '哲高', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (50733, '360', '花水匠', 'direct', '经销', 0, '50733', '花水匠', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (50735, '362', '沐光里', 'direct', '经销', 0, '50735', '沐光里', 'direct', '经销', 0, 2450.720000, 2450.720000, 0);
INSERT INTO `t_brand` VALUES (50736, '363', '测试品牌1122', 'direct', '经销', 1, '50736', '测试品牌1122', 'direct', '经销', 1, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (50737, '364', '六六视觉', 'direct', '经销', 0, '50737', '六六视觉', 'direct', '经销', 0, 22404.623200, 22404.623200, 0);
INSERT INTO `t_brand` VALUES (50738, '365', '品牌', 'direct', '经销', 0, '50738', '品牌', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (50739, '366', '测试品牌1215', 'direct', '经销', 1, '50739', '测试品牌1215', 'direct', '经销', 1, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (50740, '367', '海淘Glam up', 'direct', '经销', 0, '50740', '海淘Glam up', 'direct', '经销', 0, 1928.570000, 1928.570000, 0);
INSERT INTO `t_brand` VALUES (50741, '368', '珍视明', 'direct', '经销', 0, '50741', '珍视明', 'direct', '经销', 0, 91475.840000, 91475.840000, 0);
INSERT INTO `t_brand` VALUES (50771, '377', 'EverColor', 'tp', '代理', 0, '92823343', 'aisei', 'tp', '代理', 0, 3588481.214630, 1777320.661090, 1);
INSERT INTO `t_brand` VALUES (50772, '378', '鱼跃', 'direct', '经销', 0, '50772', '鱼跃', 'direct', '经销', 0, 295275.078950, 295275.078950, 0);
INSERT INTO `t_brand` VALUES (50796, '381', '欧几里德', 'direct', '经销', 0, '50796', '欧几里德', 'direct', '经销', 0, 4687.590000, 4687.590000, 0);
INSERT INTO `t_brand` VALUES (50797, '382', '爱尔小熊', 'direct', '经销', 0, '50797', '爱尔小熊', 'direct', '经销', 0, 0.000000, 0.000000, 0);
INSERT INTO `t_brand` VALUES (50798, '383', '蝶适', 'direct', '经销', 0, '50798', '蝶适', 'direct', '经销', 0, 2107.000000, 2107.000000, 0);
INSERT INTO `t_brand` VALUES (50799, '384', '露晰得', 'direct', '经销', 0, '50799', '露晰得', 'direct', '经销', 0, 524.930000, 524.930000, 0);
INSERT INTO `t_brand` VALUES (50800, '385', '镜沐康', 'direct', '经销', 0, '50800', '镜沐康', 'direct', '经销', 0, 18623.460000, 18623.460000, 0);
INSERT INTO `t_brand` VALUES (50801, '386', 'aisei周边产品', 'tp', '代理', 0, '92823343', 'aisei', 'tp', '代理', 0, 3588481.214630, 2044.060000, 1);
INSERT INTO `t_brand` VALUES (50827, '391', 'Mueyeco', 'direct', '经销', 0, '50827', 'Mueyeco', 'direct', '经销', 0, 11962.530000, 11962.530000, 0);
INSERT INTO `t_brand` VALUES (50828, '392', '缤瞳', 'direct', '经销', 0, '50828', '缤瞳', 'direct', '经销', 0, 905.110000, 905.110000, 0);

SET FOREIGN_KEY_CHECKS = 1;
