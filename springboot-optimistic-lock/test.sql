/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80018
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 80018
File Encoding         : 65001

Date: 2023-02-15 16:35:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for goods_info
-- ----------------------------
DROP TABLE IF EXISTS `goods_info`;
CREATE TABLE `goods_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '库存数量',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单编号',
  `amount` int(11) DEFAULT '0' COMMENT '库存数量',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of goods_info
-- ----------------------------
INSERT INTO `goods_info` VALUES ('1', '10001', '10', '0');
INSERT INTO `goods_info` VALUES ('2', '10002', '20', '0');
INSERT INTO `goods_info` VALUES ('3', '10003', '30', '0');
