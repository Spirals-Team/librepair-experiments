CREATE DATABASE IF NOT EXISTS DB_ZERO
  DEFAULT CHARSET utf8mb4
  COLLATE utf8mb4_bin;
USE DB_ZERO;
SET NAMES 'UTF8';
-- ----------------------------
-- Table Purging
-- ----------------------------
DROP TABLE IF EXISTS `SYS_TABULAR`;
CREATE TABLE `SYS_TABULAR` (
  `PK_ID`         BIGINT(20)                      NOT NULL AUTO_INCREMENT
  COMMENT 'uniqueId,PK_ID',
  `T_COMMENT`     TEXT COLLATE utf8mb4_bin COMMENT 'comment,T_COMMENT',
  `S_NAME`        VARCHAR(64) COLLATE utf8mb4_bin NOT NULL
  COMMENT 'name,S_NAME',
  `S_CODE`        VARCHAR(36) COLLATE utf8mb4_bin          DEFAULT NULL
  COMMENT 'code,S_CODE',
  `S_SERIAL`      VARCHAR(64) COLLATE utf8mb4_bin          DEFAULT NULL
  COMMENT 'serial,S_SERIAL',
  `S_TYPE`        VARCHAR(32) COLLATE utf8mb4_bin NOT NULL
  COMMENT '标识Tabular的类型字符串,type,S_TYPE',
  `J_CONFIG`      TEXT COLLATE utf8mb4_bin COMMENT 'config,J_CONFIG',
  `I_ORDER`       INT(11)                         NOT NULL
  COMMENT 'order,I_ORDER',
  `IS_ACTIVE`     TINYINT(1)                               DEFAULT NULL
  COMMENT 'active,IS_ACTIVE',
  `Z_SIGMA`       VARCHAR(32) COLLATE utf8mb4_bin          DEFAULT NULL
  COMMENT 'sigma,Z_SIGMA',
  `Z_LANGUAGE`    VARCHAR(8) COLLATE utf8mb4_bin           DEFAULT NULL
  COMMENT 'language,Z_LANGUAGE',
  `Z_CREATE_BY`   VARCHAR(36) COLLATE utf8mb4_bin          DEFAULT NULL
  COMMENT 'createBy,Z_CREATE_BY',
  `Z_CREATE_TIME` DATETIME                                 DEFAULT NULL
  COMMENT 'createTime,Z_CREATE_TIME',
  `Z_UPDATE_BY`   VARCHAR(36) COLLATE utf8mb4_bin          DEFAULT NULL
  COMMENT 'updateBy,Z_UPDATE_BY',
  `Z_UPDATE_TIME` DATETIME                                 DEFAULT NULL
  COMMENT 'updateTime,Z_UPDATE_TIME',
  PRIMARY KEY (`PK_ID`),
  UNIQUE KEY `UK_SYS_TABULAR_S_CODE_S_TYPE_Z_SIGMA_Z_LANGUAGE` (`S_CODE`, `S_TYPE`, `Z_SIGMA`, `Z_LANGUAGE`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 169
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin;

