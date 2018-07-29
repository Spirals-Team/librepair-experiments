/*
Navicat MySQL Data Transfer
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_mall_goods
-- ----------------------------
DROP TABLE IF EXISTS `t_mall_goods`;
CREATE TABLE `t_mall_goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `g_id` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '商品编号',
  `g_name` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT '产品名称',
  `price` decimal(9,2) DEFAULT NULL COMMENT '产品销售价格',
  `in_price` decimal(9,2) DEFAULT NULL COMMENT '商品进货价格',
  `icon_url` varchar(500) CHARACTER SET utf8 DEFAULT NULL COMMENT '商品图片地址 ',
  `description` varchar(500) CHARACTER SET utf8 DEFAULT NULL,
  `subcategory_id` int(11) DEFAULT NULL COMMENT '商品子类',
  `unit` varchar(10) CHARACTER SET utf8 DEFAULT NULL,
  `up_for_sale` varchar(1) CHARACTER SET utf8 DEFAULT '1' COMMENT '是否上架销售；0 该商品下架， 1 商品上架销售',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `show_popup` varchar(1) CHARACTER SET utf8 NOT NULL DEFAULT '0' COMMENT '商品详情是否有附加属性弹窗 0 不弹出 1 弹出属性框',
  `expirable` int(11) DEFAULT NULL,
  `stock` int(11) DEFAULT '10000',
  `sold` int(11) DEFAULT '0',
  `warehouse_id` int(11) DEFAULT NULL,
  `supplier_id` int(11) DEFAULT NULL COMMENT '供应商ID',
  `is_recommend` varchar(1) CHARACTER SET utf8 DEFAULT '0' COMMENT '是否推荐,0为否1为是',
  PRIMARY KEY (`id`),
  KEY `gId` (`g_id`),
  KEY `goods_subid` (`subcategory_id`),
  KEY `goods_supplierId` (`supplier_id`),
  CONSTRAINT `goods_subid` FOREIGN KEY (`subcategory_id`) REFERENCES `t_mall_subcategory` (`subcategoryId`),
  CONSTRAINT `goods_supplierId` FOREIGN KEY (`supplier_id`) REFERENCES `t_mall_supplier_detail` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3629 DEFAULT CHARSET=latin1 COMMENT='当一个商品被下架时， upForSale被标注为 0, 那么在查询商品时该商品仍然会被返回。在显示该商品详情时， 用户会被提示"该商品已被下架"；\r\n有关该商品的历史纪录（我的订单）将仍然会被显示；但当点击该商品时， 商品详情将显示“改商品已被下架”；\r\n我的收藏仍然显示该商品， 但商品详情会显示“改商品已被下架”。\r\n\r\n\r\nimportant!!!\r\n\r\n1. 当一个商品本身没有弹出框，但它所属的子类具有属性表：\r\n  根据该商品的id查找该商品，查询会返回propertyTableName\r\n\r\n2.  当一个商品有属性弹窗， showPopup = 1， 但它所属的子类没有属性表：\r\n\r\n';

-- ----------------------------
-- Table structure for t_mall_goods_photos
-- ----------------------------
DROP TABLE IF EXISTS `t_mall_goods_photos`;
CREATE TABLE `t_mall_goods_photos` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `g_id` int(11) NOT NULL,
  `img_url` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
