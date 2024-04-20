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

 Date: 20/04/2024 15:17:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_attribute_id_relation_vip
-- ----------------------------
DROP TABLE IF EXISTS `t_attribute_id_relation_vip`;
CREATE TABLE `t_attribute_id_relation_vip`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `attribute_pid` int(11) NULL DEFAULT NULL COMMENT '属性父级id',
  `attribute_id` int(11) NULL DEFAULT NULL COMMENT '属性子id',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `level` int(11) NULL DEFAULT NULL COMMENT '层级',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 468 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '标签属性层级关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_attribute_id_relation_vip
-- ----------------------------
INSERT INTO `t_attribute_id_relation_vip` VALUES (314, 0, 10000, 1, 1);
INSERT INTO `t_attribute_id_relation_vip` VALUES (315, 0, 10001, 2, 1);
INSERT INTO `t_attribute_id_relation_vip` VALUES (316, 0, 10002, 3, 1);
INSERT INTO `t_attribute_id_relation_vip` VALUES (317, 10002, 10003, 4, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (318, 10002, 10008, 5, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (319, 10003, 10004, 1, 3);
INSERT INTO `t_attribute_id_relation_vip` VALUES (320, 10003, 10005, 2, 3);
INSERT INTO `t_attribute_id_relation_vip` VALUES (321, 10003, 10006, 3, 3);
INSERT INTO `t_attribute_id_relation_vip` VALUES (322, 10003, 10007, 4, 3);
INSERT INTO `t_attribute_id_relation_vip` VALUES (323, 10008, 10009, 1, 3);
INSERT INTO `t_attribute_id_relation_vip` VALUES (324, 10008, 10010, 2, 3);
INSERT INTO `t_attribute_id_relation_vip` VALUES (325, 0, 10011, 4, 1);
INSERT INTO `t_attribute_id_relation_vip` VALUES (326, 10011, 10012, 1, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (327, 10011, 10013, 2, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (328, 0, 10014, 6, 1);
INSERT INTO `t_attribute_id_relation_vip` VALUES (329, 10014, 10015, 1, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (330, 10014, 10016, 2, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (331, 10016, 10017, 1, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (332, 10012, 10019, 1, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (333, 10012, 10020, 2, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (334, 10011, 10021, 1, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (335, 10011, 10022, 2, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (336, 10021, 10019, 1, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (337, 10021, 10020, 2, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (338, 10015, 10018, 1, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (339, 0, 11000, 1, 1);
INSERT INTO `t_attribute_id_relation_vip` VALUES (340, 11000, 11001, 1, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (341, 11000, 11003, 2, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (342, 11000, 11005, 3, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (343, 0, 11002, 2, 1);
INSERT INTO `t_attribute_id_relation_vip` VALUES (344, 0, 11004, 2, 1);
INSERT INTO `t_attribute_id_relation_vip` VALUES (345, 0, 11006, 2, 1);
INSERT INTO `t_attribute_id_relation_vip` VALUES (346, 0, 12000, 1, 1);
INSERT INTO `t_attribute_id_relation_vip` VALUES (347, 12000, 12001, 1, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (348, 12000, 12002, 2, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (349, 0, 13000, 1, 1);
INSERT INTO `t_attribute_id_relation_vip` VALUES (350, 13000, 13001, 1, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (351, 13000, 13002, 2, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (352, 10003, 10023, 5, 3);
INSERT INTO `t_attribute_id_relation_vip` VALUES (353, 10003, 10024, 6, 3);
INSERT INTO `t_attribute_id_relation_vip` VALUES (354, 10003, 10025, 7, 3);
INSERT INTO `t_attribute_id_relation_vip` VALUES (355, 10003, 10026, 8, 3);
INSERT INTO `t_attribute_id_relation_vip` VALUES (356, 10003, 10027, 9, 3);
INSERT INTO `t_attribute_id_relation_vip` VALUES (357, 10003, 10028, 10, 3);
INSERT INTO `t_attribute_id_relation_vip` VALUES (358, 10008, 10023, 3, 3);
INSERT INTO `t_attribute_id_relation_vip` VALUES (359, 10008, 10024, 4, 3);
INSERT INTO `t_attribute_id_relation_vip` VALUES (360, 10008, 10025, 5, 3);
INSERT INTO `t_attribute_id_relation_vip` VALUES (361, 10008, 10026, 6, 3);
INSERT INTO `t_attribute_id_relation_vip` VALUES (362, 10008, 10027, 7, 3);
INSERT INTO `t_attribute_id_relation_vip` VALUES (363, 10008, 10028, 8, 3);
INSERT INTO `t_attribute_id_relation_vip` VALUES (364, 10062, 10029, 3, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (365, 10062, 10030, 4, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (366, 10062, 10031, 5, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (367, 10062, 10032, 6, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (368, 10062, 10033, 7, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (369, 10062, 10034, 8, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (370, 10062, 10035, 9, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (371, 10062, 10036, 10, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (372, 10062, 10037, 11, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (373, 10062, 10038, 12, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (374, 10014, 10039, 3, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (375, 10014, 10040, 4, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (376, 10014, 10043, 5, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (378, 10014, 10071, 7, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (379, 10039, 10041, 1, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (380, 10040, 10042, 1, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (381, 10043, 10044, 1, 3);
INSERT INTO `t_attribute_id_relation_vip` VALUES (382, 10043, 10045, 2, 3);
INSERT INTO `t_attribute_id_relation_vip` VALUES (383, 10043, 10046, 3, 3);
INSERT INTO `t_attribute_id_relation_vip` VALUES (384, 10043, 10047, 4, 3);
INSERT INTO `t_attribute_id_relation_vip` VALUES (385, 10043, 10048, 5, 3);
INSERT INTO `t_attribute_id_relation_vip` VALUES (386, 10043, 10049, 6, 3);
INSERT INTO `t_attribute_id_relation_vip` VALUES (387, 10043, 10050, 7, 3);
INSERT INTO `t_attribute_id_relation_vip` VALUES (388, 10043, 10051, 8, 3);
INSERT INTO `t_attribute_id_relation_vip` VALUES (389, 10052, 10057, 1, 3);
INSERT INTO `t_attribute_id_relation_vip` VALUES (390, 10052, 10058, 2, 3);
INSERT INTO `t_attribute_id_relation_vip` VALUES (391, 10052, 10059, 3, 3);
INSERT INTO `t_attribute_id_relation_vip` VALUES (392, 10052, 10060, 4, 3);
INSERT INTO `t_attribute_id_relation_vip` VALUES (393, 10055, 10027, 4, 4);
INSERT INTO `t_attribute_id_relation_vip` VALUES (394, 10056, 10024, 1, 4);
INSERT INTO `t_attribute_id_relation_vip` VALUES (395, 10056, 10027, 4, 4);
INSERT INTO `t_attribute_id_relation_vip` VALUES (396, 10011, 10063, 3, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (397, 10011, 10064, 4, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (398, 10011, 10065, 5, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (399, 10011, 10066, 6, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (400, 10011, 10067, 7, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (401, 10011, 10068, 8, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (402, 10011, 10069, 9, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (403, 10011, 10070, 10, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (404, 10071, 10072, 1, 3);
INSERT INTO `t_attribute_id_relation_vip` VALUES (405, 0, 10062, 5, 1);
INSERT INTO `t_attribute_id_relation_vip` VALUES (406, 10014, 10052, 8, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (408, 0, 118, 1, 1);
INSERT INTO `t_attribute_id_relation_vip` VALUES (409, 0, 119, 2, 1);
INSERT INTO `t_attribute_id_relation_vip` VALUES (410, 0, 120, 3, 1);
INSERT INTO `t_attribute_id_relation_vip` VALUES (411, 120, 121, 1, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (412, 121, 124, 1, 3);
INSERT INTO `t_attribute_id_relation_vip` VALUES (413, 453, 124, 1, 4);
INSERT INTO `t_attribute_id_relation_vip` VALUES (414, 121, 125, 2, 3);
INSERT INTO `t_attribute_id_relation_vip` VALUES (415, 121, 126, 3, 3);
INSERT INTO `t_attribute_id_relation_vip` VALUES (416, 452, 127, 4, 4);
INSERT INTO `t_attribute_id_relation_vip` VALUES (417, 453, 127, 4, 4);
INSERT INTO `t_attribute_id_relation_vip` VALUES (418, 121, 127, 4, 3);
INSERT INTO `t_attribute_id_relation_vip` VALUES (419, 121, 128, 5, 3);
INSERT INTO `t_attribute_id_relation_vip` VALUES (420, 0, 129, 5, 1);
INSERT INTO `t_attribute_id_relation_vip` VALUES (421, 129, 130, 1, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (422, 129, 131, 2, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (423, 129, 132, 3, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (424, 129, 133, 4, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (425, 129, 134, 5, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (426, 129, 135, 6, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (427, 129, 136, 7, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (428, 129, 137, 8, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (429, 129, 138, 9, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (430, 129, 139, 10, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (431, 0, 140, 6, 1);
INSERT INTO `t_attribute_id_relation_vip` VALUES (432, 140, 141, 1, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (433, 140, 142, 2, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (434, 141, 147, 1, 4);
INSERT INTO `t_attribute_id_relation_vip` VALUES (435, 142, 148, 1, 4);
INSERT INTO `t_attribute_id_relation_vip` VALUES (436, 140, 149, 3, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (437, 149, 150, 1, 4);
INSERT INTO `t_attribute_id_relation_vip` VALUES (438, 140, 151, 4, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (439, 151, 152, 1, 4);
INSERT INTO `t_attribute_id_relation_vip` VALUES (440, 121, 452, 1, 3);
INSERT INTO `t_attribute_id_relation_vip` VALUES (441, 121, 453, 2, 3);
INSERT INTO `t_attribute_id_relation_vip` VALUES (442, 142, 1055, 1, 4);
INSERT INTO `t_attribute_id_relation_vip` VALUES (443, 1055, 1056, 1, 4);
INSERT INTO `t_attribute_id_relation_vip` VALUES (444, 0, 1144, 2, 1);
INSERT INTO `t_attribute_id_relation_vip` VALUES (445, 0, 1153, 4, 1);
INSERT INTO `t_attribute_id_relation_vip` VALUES (446, 1153, 1154, 1, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (447, 1153, 1155, 2, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (448, 1153, 1156, 3, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (449, 1153, 1157, 4, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (450, 1153, 1158, 5, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (451, 1153, 1159, 6, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (452, 1153, 1160, 7, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (453, 1153, 1162, 8, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (454, 140, 1197, 5, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (455, 1197, 1198, 1, 3);
INSERT INTO `t_attribute_id_relation_vip` VALUES (456, 0, 12100, 1, 1);
INSERT INTO `t_attribute_id_relation_vip` VALUES (457, 12100, 12101, 1, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (458, 12100, 12102, 2, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (459, 0, 12200, 1, 1);
INSERT INTO `t_attribute_id_relation_vip` VALUES (460, 12200, 12201, 1, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (461, 12200, 12202, 2, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (462, 0, 12300, 1, 1);
INSERT INTO `t_attribute_id_relation_vip` VALUES (463, 12300, 12301, 1, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (464, 12300, 12302, 2, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (465, 12300, 12303, 3, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (466, 10014, 10073, 3, 2);
INSERT INTO `t_attribute_id_relation_vip` VALUES (467, 10073, 10074, 1, 2);

SET FOREIGN_KEY_CHECKS = 1;
