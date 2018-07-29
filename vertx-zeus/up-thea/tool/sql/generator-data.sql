USE DB_ZERO;
-- ----------------------------
-- Records of SYS_TABULAR
-- ----------------------------
BEGIN;
INSERT INTO `SYS_TABULAR` VALUES
  (1, NULL, '挂牌价', 'Standard', NULL, 'code.pricecat', NULL, 1, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (2, NULL, '散客执行价', 'Single', NULL, 'code.pricecat', NULL, 2, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (3, NULL, '会员价', 'Member', NULL, 'code.pricecat', NULL, 3, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (4, NULL, '协议单位价', 'Cooperation', NULL, 'code.pricecat', NULL, 4, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (5, NULL, '旅行社协议价', 'TravelAgent', NULL, 'code.pricecat', NULL, 5, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (6, NULL, '内招价', 'Internal', NULL, 'code.pricecat', NULL, 6, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (7, NULL, '钟点房', 'HourRoom', NULL, 'code.pricecat', NULL, 7, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (8, NULL, '可操作', 'Operation', NULL, 'room.status', '{\"visible\":false}', 1, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq',
      'cn', NULL, '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES (9, NULL, '维修中', 'Mind', NULL, 'room.status', '{\"icon\":\"red configure\"}', 2, 1,
                                     'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL, '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (10, NULL, '预留房', 'Left', NULL, 'room.status', '{\"icon\":\"green heart\"}', 3, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq',
       'cn', NULL, '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (11, NULL, '停用房', 'Disabled', NULL, 'room.status', '{\"icon\":\"red dont circle\"}', 4, 1,
       'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL, '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES (12, NULL, '自用房', 'Self', NULL, 'room.status', '{\"icon\":\"blue recycle\"}', 5, 1,
                                      'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL, '2018-02-07 04:09:32', NULL,
                                  NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (13, NULL, '免费房', 'Free', NULL, 'room.status', '{\"icon\":\"red recycle\"}', 6, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq',
       'cn', NULL, '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES (14, NULL, '长住房', 'Dwell', NULL, 'room.status', '{\"icon\":\"green recycle\"}', 7, 1,
                                      'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL, '2018-02-07 04:09:32', NULL,
                                  NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (15, NULL, '注册会员', 'Registered', NULL, 'member.type', NULL, 1, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (16, NULL, '白银会员', 'Silver', NULL, 'member.type', NULL, 2, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (17, NULL, '黄金会员', 'Gold', NULL, 'member.type', NULL, 3, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (18, NULL, '白金会员', 'Platinum', NULL, 'member.type', NULL, 4, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (19, NULL, '钻石会员', 'Diamond', NULL, 'member.type', NULL, 5, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (20, NULL, '协议单位', 'Corporation', NULL, 'partner.type', NULL, 1, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (21, NULL, '供应商', 'Vendor', NULL, 'partner.type', NULL, 2, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (22, NULL, '旅行社', 'TravelAgent', NULL, 'partner.type', NULL, 3, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (23, NULL, '流动资产', 'LiquidAssets', NULL, 'accounting.type', NULL, 1, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn',
   NULL, '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (24, NULL, '非流动资产', 'NonLiquidAssets', NULL, 'accounting.type', NULL, 2, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn',
   NULL, '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (25, NULL, '流动负债', 'LiquidLiabilities', NULL, 'accounting.type', NULL, 3, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn',
   NULL, '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (26, NULL, '非流动负债', 'NonLiquidLiabilities', NULL, 'accounting.type', NULL, 4, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq',
       'cn', NULL, '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (27, NULL, '所有者权益', 'OwnerEquity', NULL, 'accounting.type', NULL, 5, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn',
   NULL, '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (28, NULL, '成本', 'Cost', NULL, 'accounting.type', NULL, 6, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (29, NULL, '营业收入', 'OprRevenue', NULL, 'accounting.type', NULL, 7, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (30, NULL, '其他收益', 'OtherEquity', NULL, 'accounting.type', NULL, 8, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (31, NULL, '营业成本及税金', 'CostTax', NULL, 'accounting.type', NULL, 9, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (32, NULL, '其他损失', 'OtherExpense', NULL, 'accounting.type', NULL, 10, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn',
   NULL, '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (33, NULL, '期间费用', 'Expense', NULL, 'accounting.type', NULL, 11, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (34, NULL, '所得税', 'IncomeTax', NULL, 'accounting.type', NULL, 12, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (35, NULL, '新创建', 'Created', NULL, 'code.group.status', NULL, 1, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (36, NULL, '正在使用', 'OnGoing', NULL, 'code.group.status', NULL, 2, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (37, NULL, '已过期', 'Expired', NULL, 'code.group.status', NULL, 3, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (38, NULL, '现金', 'Cash', NULL, 'pay.type', NULL, 1, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (39, NULL, '信用卡', 'Credit', NULL, 'pay.type', NULL, 2, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (40, NULL, '借记卡', 'Debit', NULL, 'pay.type', NULL, 3, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (41, NULL, '支票', 'Cheque', NULL, 'pay.type', NULL, 4, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (42, NULL, '银行转账', 'Transfer', NULL, 'pay.type', NULL, 5, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (43, NULL, '网上支付', 'Net', NULL, 'pay.type', NULL, 6, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (44, NULL, '散客预定', 'Personal', NULL, 'preorder.category', NULL, 1, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (45, NULL, '团队预定', 'Company', NULL, 'preorder.category', NULL, 2, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (46, NULL, '费用项', 'Expenses', NULL, 'accounting.code', NULL, 1, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (47, NULL, '代收代付', 'Collection', NULL, 'accounting.code', NULL, 2, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (48, NULL, '折扣款待', 'Discount', NULL, 'accounting.code', NULL, 3, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (49, NULL, '收款项', 'Receivables', NULL, 'accounting.code', NULL, 4, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (50, NULL, '在线', 'OnLine', NULL, 'shop.type', NULL, 1, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (51, NULL, '加盟店', 'Chain', NULL, 'shop.type', NULL, 2, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (52, NULL, '直营店', 'Regular', NULL, 'shop.type', NULL, 3, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (53, NULL, '定额', 'Fixed', NULL, 'code.commission.type', NULL, 1, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (54, NULL, '比例', 'Rate', NULL, 'code.commission.type', NULL, 2, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (55, NULL, '底价', 'Base', NULL, 'code.commission.type', NULL, 3, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (56, NULL, '普通房', 'Common', NULL, 'in.room.type', NULL, 1, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (57, NULL, '钟点房', 'Hourly', NULL, 'in.room.type', NULL, 2, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (58, NULL, '特殊房', 'Special', NULL, 'in.room.type', NULL, 3, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (59, NULL, '自用房', 'Self', NULL, 'in.room.type', NULL, 4, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (60, NULL, '免费房', 'Free', NULL, 'in.room.type', NULL, 5, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (61, NULL, '公寓房', 'Apartment', NULL, 'in.room.type', NULL, 6, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (62, NULL, '公司协议', 'Company', NULL, 'contract.type', NULL, 1, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (63, NULL, '中介协议', 'Agent', NULL, 'contract.type', NULL, 2, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (64, NULL, '旅行协议', 'Travel', NULL, 'contract.type', NULL, 3, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES (65, NULL, '占用房', 'Taken', NULL, 'room.op.status', '{\"icon\":\"blue users\"}', 1, 1,
                                      'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL, '2018-02-07 04:09:32', NULL,
                                  NULL);
INSERT INTO `SYS_TABULAR` VALUES (66, NULL, '空房间', 'Empty', NULL, 'room.op.status', '{\"icon\":\"green home\"}', 2, 1,
                                      'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL, '2018-02-07 04:09:32', NULL,
                                  NULL);
INSERT INTO `SYS_TABULAR` VALUES (67, NULL, '预离房', 'Leaving', NULL, 'room.op.status', '{\"icon\":\"blue plane\"}', 3, 1,
                                      'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL, '2018-02-07 04:09:32', NULL,
                                  NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (68, NULL, '预抵房', 'Arriving', NULL, 'room.op.status', '{\"icon\":\"green plane\"}', 4, 1,
       'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL, '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (69, NULL, '费用码', 'Expense', NULL, 'pay.term.type', NULL, 1, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (70, NULL, '付款码', 'Pay', NULL, 'pay.term.type', NULL, 2, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (71, NULL, '无担保', 'None', NULL, 'surety.type', NULL, 1, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (72, NULL, '担保到店', 'Partial', NULL, 'surety.type', NULL, 2, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (73, NULL, '全程担保', 'All', NULL, 'surety.type', NULL, 3, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (74, NULL, '茶楼', 'Teahouse', NULL, 'menu.type', NULL, 1, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (75, NULL, '餐厅', 'Canteen', NULL, 'menu.type', NULL, 2, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (76, NULL, '已激活', 'Active', NULL, 'member.status', NULL, 1, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (77, NULL, '未激活', 'New', NULL, 'member.status', NULL, 2, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (78, NULL, '已认证', 'Verified', NULL, 'member.status', NULL, 3, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (79, NULL, '已锁定', 'Locked', NULL, 'member.status', NULL, 4, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (80, NULL, '脏房', 'Dirty', NULL, 'room.clean.status', '{\"icon\":\"violet certificate\"}', 1, 1,
       'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL, '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (81, NULL, '清洁已检查', 'Checked', NULL, 'room.clean.status', '{\"icon\":\"green toggle on\"}', 2, 1,
       'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL, '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (82, NULL, '清洁未检查', 'Unchecked', NULL, 'room.clean.status', '{\"icon\":\"red toggle off\"}', 3, 1,
       'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL, '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (83, NULL, '上门预定', 'Visit', NULL, 'preorder.method', NULL, 1, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (84, NULL, '电话预定', 'Phone', NULL, 'preorder.method', NULL, 2, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (85, NULL, '微信预定', 'WeChat', NULL, 'preorder.method', NULL, 3, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (86, NULL, '网页预定', 'Web', NULL, 'preorder.method', NULL, 4, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (87, NULL, 'APP预定', 'App', NULL, 'preorder.method', NULL, 5, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (88, NULL, '团队预定', 'Team', NULL, 'preorder.method', NULL, 6, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (89, NULL, '旅行社预定', 'TravelAgent', NULL, 'preorder.method', NULL, 7, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn',
   NULL, '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (90, NULL, 'OTA预定', 'OTA', NULL, 'preorder.method', NULL, 8, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (91, NULL, '企业单位', 'Enterprise', NULL, 'code.source', NULL, 1, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (92, NULL, '事业单位', 'Goverment', NULL, 'code.source', NULL, 2, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (93, NULL, '非营利组织', 'Non-Profit', NULL, 'code.source', NULL, 3, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (94, NULL, '国外公司', 'Overseas', NULL, 'code.source', NULL, 4, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (95, NULL, '回头客', 'Customer', NULL, 'code.source', NULL, 5, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (96, NULL, '直接上门', 'Direct', NULL, 'code.source', NULL, 6, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (97, NULL, '订房中心', 'Center', NULL, 'code.source', NULL, 7, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (98, NULL, '地区销售', 'AreaSale', NULL, 'code.source', NULL, 8, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (99, NULL, '男', 'Male', NULL, 'gender.type', NULL, 1, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (100, NULL, '女', 'Female', NULL, 'gender.type', NULL, 2, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (101, NULL, '充值', 'Direct', NULL, 'deposit.type', NULL, 1, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (102, NULL, '赠送', 'Present', NULL, 'deposit.type', NULL, 2, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (103, NULL, '协议单位账号', 'Cooperation.Account', NULL, 'accmgmt.type', NULL, 1, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq',
        'cn', NULL, '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (104, NULL, '供应商账号', 'Vendor.Account', NULL, 'accmgmt.type', NULL, 2, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn',
   NULL, '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (105, NULL, '酒店会员账号', 'Member.Account', NULL, 'accmgmt.type', NULL, 3, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn',
   NULL, '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (106, NULL, '现金', 'Cash', NULL, 'deposit.source', NULL, 1, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (107, NULL, '刷卡', 'Card', NULL, 'deposit.source', NULL, 2, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (108, NULL, '赠送', 'Present', NULL, 'deposit.source', NULL, 3, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (109, NULL, '支付宝', 'Alipay', NULL, 'deposit.source', NULL, 4, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (110, NULL, '微信支付', 'WeChat', NULL, 'deposit.source', NULL, 5, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (111, NULL, '房务账单', 'Room', NULL, 'bill.type', NULL, 1, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (112, NULL, '预定账单', 'Order', NULL, 'bill.type', NULL, 2, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (113, NULL, '消费账单', 'Consume', NULL, 'bill.type', NULL, 3, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (114, NULL, '挂账账单', 'RunUp', NULL, 'bill.type', NULL, 4, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (115, NULL, '赊账账单', 'Knocker', NULL, 'bill.type', NULL, 5, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (116, NULL, '虚拟账单', 'Virtual', NULL, 'bill.type', NULL, 6, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (117, NULL, '已预定', 'Ordered', NULL, 'order.status', NULL, 1, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (118, NULL, '正在排房', 'Scheduling', NULL, 'order.status', NULL, 2, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (119, NULL, '排房完成', 'Scheduled', NULL, 'order.status', NULL, 3, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (120, NULL, '部分入住', 'Inoccuping', NULL, 'order.status', NULL, 4, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (121, NULL, '入住完成', 'Registered', NULL, 'order.status', NULL, 5, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (122, NULL, '已结账', 'Payed', NULL, 'order.status', NULL, 6, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (123, NULL, '已退房', 'Finished', NULL, 'order.status', NULL, 7, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (124, NULL, '有效账目', 'Valid', NULL, 'bill.filter.type', NULL, 1, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (125, NULL, '无效账目', 'Invalid', NULL, 'bill.filter.type', NULL, 2, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (126, NULL, '全天返半天返', 'WholeAndHalf', NULL, 'code.money.payback', NULL, 1, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq',
        'cn', NULL, '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (127, NULL, '全天返半天不返', 'WholeOnly', NULL, 'code.money.payback', NULL, 2, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn',
   NULL, '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (128, NULL, '全天不返半天返', 'HalfOnly', NULL, 'code.money.payback', NULL, 3, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn',
   NULL, '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (129, NULL, '全天不返半天不返', 'BothNot', NULL, 'code.money.payback', NULL, 4, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn',
   NULL, '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (130, NULL, '临时会员卡（未激活）', 'Inactive', NULL, 'card.type', NULL, 1, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (131, NULL, '正式会员卡（已激活）', 'Using', NULL, 'card.type', NULL, 2, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (132, NULL, '白金会员卡', 'Best', NULL, 'card.type', NULL, 3, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (133, NULL, '订单发票', 'Order', NULL, 'invoice.type', NULL, 1, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (134, NULL, '充值发票', 'Deposit', NULL, 'invoice.type', NULL, 2, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (135, NULL, '消费发票', 'Payment', NULL, 'invoice.type', NULL, 3, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (136, NULL, '房费发票', 'Room', NULL, 'invoice.type', NULL, 4, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (137, NULL, '试用员工', 'CW', NULL, 'employee.type', NULL, 1, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (138, NULL, '正式员工', 'Regular', NULL, 'employee.type', NULL, 2, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (139, NULL, '新创建', 'Created', NULL, 'inoccup.status', NULL, 1, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (140, NULL, '已登记', 'Registered', NULL, 'inoccup.status', NULL, 2, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (141, NULL, '延迟', 'Delay', NULL, 'inoccup.status', NULL, 3, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (142, NULL, '已撤销', 'Cancel', NULL, 'inoccup.status', NULL, 4, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (143, NULL, '在住宾客', 'OnGoing', NULL, 'traveler.status', NULL, 1, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (144, NULL, '历史宾客', 'History', NULL, 'traveler.status', NULL, 2, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (145, NULL, '一代身份证', 'First', NULL, 'idc.type', NULL, 1, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (146, NULL, '二代身份证', 'Second', NULL, 'idc.type', NULL, 2, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (147, NULL, '驾驶证', 'Driving.License', NULL, 'idc.type', NULL, 3, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (148, NULL, '护照', 'Passport', NULL, 'idc.type', NULL, 4, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (149, NULL, '军官证', 'Osifer', NULL, 'idc.type', NULL, 5, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (150, NULL, '士兵证', 'Soldier', NULL, 'idc.type', NULL, 6, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (151, NULL, '港澳通行证', 'HK.Macau', NULL, 'idc.type', NULL, 7, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (152, NULL, '回乡证', 'Home.Return', NULL, 'idc.type', NULL, 8, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (153, NULL, '临时身份证', 'Interim', NULL, 'idc.type', NULL, 9, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (154, NULL, '户口薄', 'Household', NULL, 'idc.type', NULL, 10, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (155, NULL, '警官证', 'Police', NULL, 'idc.type', NULL, 11, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (156, NULL, '其他', 'Other', NULL, 'idc.type', NULL, 12, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (157, NULL, '未婚', 'Single', NULL, 'marital.type', NULL, 1, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (158, NULL, '已婚', 'Married', NULL, 'marital.type', NULL, 2, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (159, NULL, '客房', 'Room', NULL, 'shift.type', NULL, 1, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (160, NULL, '餐厅', 'Restaurant', NULL, 'shift.type', NULL, 2, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (161, NULL, '前台散客', 'Frant', NULL, 'code.market', NULL, 1, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (162, NULL, '去哪儿网', 'Qunar', NULL, 'code.market', NULL, 2, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (163, NULL, '同程网', 'LY', NULL, 'code.market', NULL, 3, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (164, NULL, '携程网', 'Ctrip', NULL, 'code.market', NULL, 4, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (165, NULL, '艺龙网', 'ELong', NULL, 'code.market', NULL, 5, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (166, NULL, '旅行社', 'TravelAgent', NULL, 'code.market', NULL, 6, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (167, NULL, '内招', 'Internal', NULL, 'code.market', NULL, 7, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
INSERT INTO `SYS_TABULAR` VALUES
  (168, NULL, '自用', 'Self', NULL, 'code.market', NULL, 8, 1, 'ENhwBAJPZuSgIAE5EDakR6yrIQbOoOPq', 'cn', NULL,
   '2018-02-07 04:09:32', NULL, NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;