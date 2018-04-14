/*
 Navicat Premium Data Transfer

 Source Server         : 本地连接
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : localhost:3306
 Source Schema         : spring-boot-mini-program-examples

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : 65001

 Date: 10/04/2018 23:56:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for banner
-- ----------------------------
DROP TABLE IF EXISTS `banner`;
CREATE TABLE `banner` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `category` int(11) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `status` int(11) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `url` varchar(255) NOT NULL,
  `version` int(11) NOT NULL,
  `owner_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of banner
-- ----------------------------
BEGIN;
INSERT INTO `banner` VALUES (1, 0, '2018-04-10 23:35:30.000000', 0, '2018-04-10 23:35:30.000000', 'https://i1.mifile.cn/a4/xmad_15221426844518_moLSI.jpg', 1, NULL);
INSERT INTO `banner` VALUES (2, 0, '2018-04-10 23:35:30.000000', 0, '2018-04-10 23:35:30.000000', 'https://i1.mifile.cn/a4/xmad_15206016147333_zJPru.jpg', 1, NULL);
INSERT INTO `banner` VALUES (3, 0, '2018-04-10 23:35:30.000000', 0, '2018-04-10 23:35:30.000000', 'https://i1.mifile.cn/a4/xmad_15211176853942_HVcRJ.jpg', 1, NULL);
INSERT INTO `banner` VALUES (4, 0, '2018-04-10 23:35:30.000000', 0, '2018-04-10 23:35:30.000000', 'https://i1.mifile.cn/a4/xmad_15211196784723_YEbGM.jpg', 1, NULL);
COMMIT;

-- ----------------------------
-- Table structure for channel
-- ----------------------------
DROP TABLE IF EXISTS `channel`;
CREATE TABLE `channel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `status` int(11) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of channel
-- ----------------------------
BEGIN;
INSERT INTO `channel` VALUES (1, '2018-04-10 23:35:30.000000', '手机', 0, '2018-04-10 23:35:30.000000');
INSERT INTO `channel` VALUES (2, '2018-04-10 23:35:30.000000', '电视', 0, '2018-04-10 23:35:30.000000');
INSERT INTO `channel` VALUES (3, '2018-04-10 23:35:30.000000', '电脑', 0, '2018-04-10 23:35:30.000000');
INSERT INTO `channel` VALUES (4, '2018-04-10 23:35:30.000000', '智能', 0, '2018-04-10 23:35:30.000000');
INSERT INTO `channel` VALUES (5, '2018-04-10 23:35:30.000000', '新品', 0, '2018-04-10 23:35:30.000000');
COMMIT;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `status` int(11) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `product_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `current_selling_price` decimal(19,2) NOT NULL,
  `description` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `original_selling_price` decimal(19,2) NOT NULL,
  `status` int(11) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `theme_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product
-- ----------------------------
BEGIN;
INSERT INTO `product` VALUES (1, '2018-04-10 23:35:30.000000', 999.00, '全面屏 / 4000mAh大电量 / 前置柔光自拍 / 14m骁龙八核处理器', '红米5 Plus', 999.00, 0, '2018-04-10 23:35:30.000000', 1);
INSERT INTO `product` VALUES (2, '2018-04-10 23:35:30.000000', 1399.00, '光学变焦双摄，拍人更美 / 5.5\"大屏轻薄全金属 / 骁龙八核处理器 / 4GB大内存', '小米5X 变焦双摄', 1499.00, 0, '2018-04-10 23:35:30.000000', 1);
INSERT INTO `product` VALUES (3, '2018-04-10 23:35:30.000000', 3999.00, '4K超高清屏 / 四核64位高性能处理器，支持HDR10 / 2GB+8GB大存储，可配32GB版 / 人工智能语音 / 支持壁挂', '小米电视4A 65英寸', 4499.00, 0, '2018-04-10 23:35:30.000000', 1);
COMMIT;

-- ----------------------------
-- Table structure for product_color
-- ----------------------------
DROP TABLE IF EXISTS `product_color`;
CREATE TABLE `product_color` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `current_additional_price` decimal(19,2) NOT NULL,
  `name` varchar(255) NOT NULL,
  `original_additional_price` decimal(19,2) NOT NULL,
  `status` int(11) NOT NULL,
  `stocks` int(11) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `product_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product_color
-- ----------------------------
BEGIN;
INSERT INTO `product_color` VALUES (1, '2018-04-10 23:35:30.000000', 0.00, '黑色', 0.00, 0, 100, '2018-04-10 23:35:30.000000', 3);
INSERT INTO `product_color` VALUES (2, '2018-04-10 23:35:30.000000', 0.00, '红色', 0.00, 0, 100, '2018-04-10 23:35:30.000000', 2);
INSERT INTO `product_color` VALUES (3, '2018-04-10 23:35:30.000000', 0.00, '金色', 0.00, 0, 100, '2018-04-10 23:35:30.000000', 2);
INSERT INTO `product_color` VALUES (4, '2018-04-10 23:35:30.000000', 0.00, '玫瑰金', 0.00, 0, 100, '2018-04-10 23:35:30.000000', 2);
INSERT INTO `product_color` VALUES (5, '2018-04-10 23:35:30.000000', 0.00, '黑色', 0.00, 0, 100, '2018-04-10 23:35:30.000000', 2);
INSERT INTO `product_color` VALUES (6, '2018-04-10 23:35:30.000000', 0.00, '黑色', 0.00, 0, 100, '2018-04-10 23:35:30.000000', 1);
INSERT INTO `product_color` VALUES (7, '2018-04-10 23:35:30.000000', 0.00, '金色', 0.00, 0, 100, '2018-04-10 23:35:30.000000', 1);
INSERT INTO `product_color` VALUES (8, '2018-04-10 23:35:30.000000', 0.00, '玫瑰金', 0.00, 0, 100, '2018-04-10 23:35:30.000000', 1);
INSERT INTO `product_color` VALUES (9, '2018-04-10 23:35:30.000000', 0.00, '浅蓝', 0.00, 0, 100, '2018-04-10 23:35:30.000000', 1);
COMMIT;

-- ----------------------------
-- Table structure for product_specification
-- ----------------------------
DROP TABLE IF EXISTS `product_specification`;
CREATE TABLE `product_specification` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `current_additional_price` decimal(19,2) NOT NULL,
  `name` varchar(255) NOT NULL,
  `original_additional_price` decimal(19,2) NOT NULL,
  `status` int(11) NOT NULL,
  `stocks` int(11) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `product_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product_specification
-- ----------------------------
BEGIN;
INSERT INTO `product_specification` VALUES (1, '2018-04-10 23:35:30.000000', 0.00, '3GB+32GB', 0.00, 0, 100, '2018-04-10 23:35:30.000000', 1);
INSERT INTO `product_specification` VALUES (2, '2018-04-10 23:35:30.000000', 250.00, '4GB+64GB', 250.00, 0, 100, '2018-04-10 23:35:30.000000', 1);
INSERT INTO `product_specification` VALUES (3, '2018-04-10 23:35:30.000000', 0.00, '4GB+64GB', 0.00, 0, 100, '2018-04-10 23:35:30.000000', 2);
INSERT INTO `product_specification` VALUES (4, '2018-04-10 23:35:30.000000', 0.00, '65英寸', 0.00, 0, 100, '2018-04-10 23:35:30.000000', 3);
COMMIT;

-- ----------------------------
-- Table structure for product_tag
-- ----------------------------
DROP TABLE IF EXISTS `product_tag`;
CREATE TABLE `product_tag` (
  `tag_id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  PRIMARY KEY (`product_id`,`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product_tag
-- ----------------------------
BEGIN;
INSERT INTO `product_tag` VALUES (1, 1);
INSERT INTO `product_tag` VALUES (1, 2);
COMMIT;

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `status` int(11) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `url` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tag
-- ----------------------------
BEGIN;
INSERT INTO `tag` VALUES (1, '2018-04-10 23:35:30.000000', '米粉节特惠', 0, '2018-04-10 23:35:30.000000', 'https://cdn.cnbj0.fds.api.mi-img.com/b2c-mimall-media/40da06c558dfd4c9ee8bf1b4aaac7690.png?w=180&h=48');
INSERT INTO `tag` VALUES (2, '2018-04-10 23:35:30.000000', '限时特惠', 0, '2018-04-10 23:35:30.000000', 'https://cdn.cnbj0.fds.api.mi-img.com/b2c-mimall-media/0c55f13eefcb247d5a706ae91cff24c2.png?w=180&h=48');
INSERT INTO `tag` VALUES (3, '2018-04-10 23:35:30.000000', '新品', 0, '2018-04-10 23:35:30.000000', 'http://cdn.cnbj0.fds.api.mi-img.com/b2c-mimall-media/dc366c6277333af59cd0fec8285b5677.png?w=120&h=48');
COMMIT;

-- ----------------------------
-- Table structure for theme
-- ----------------------------
DROP TABLE IF EXISTS `theme`;
CREATE TABLE `theme` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `status` int(11) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of theme
-- ----------------------------
BEGIN;
INSERT INTO `theme` VALUES (1, '2018-04-10 23:35:30.000000', '明星单品', 0, '2018-04-10 23:35:30.000000');
INSERT INTO `theme` VALUES (2, '2018-04-10 23:35:30.000000', '新品上线', 0, '2018-04-10 23:35:30.000000');
INSERT INTO `theme` VALUES (3, '2018-04-10 23:35:30.000000', '米家智能', 0, '2018-04-10 23:35:30.000000');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
