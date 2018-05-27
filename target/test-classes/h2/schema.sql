DROP TABLE IF EXISTS `interface_info`;

CREATE TABLE `interface_info` (
  `interface_id` int(11) NOT NULL AUTO_INCREMENT,
  `interface_name` varchar(20) NOT NULL,
  `method` varchar(5) NOT NULL,
  `description` text,
  `url` varchar(50) DEFAULT NULL,
  `json` text,
  `project_id` int(10) unsigned NOT NULL,
  `del_flag` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`interface_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `message`;

CREATE TABLE `message` (
  `message_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `del_flag` tinyint(4) DEFAULT '0',
  `read_flag` tinyint(4) DEFAULT '0',
  `send_user_id` int(10) unsigned NOT NULL,
  `team_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`message_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `project_info`;

CREATE TABLE `project_info` (
  `project_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `project_name` varchar(20) NOT NULL,
  `description` text,
  `project_address` varchar(100) DEFAULT NULL,
  `del_flag` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`project_id`),
  UNIQUE KEY `project_name` (`project_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `project_member`;

CREATE TABLE `project_member` (
  `project_id` int(10) unsigned NOT NULL,
  `user_id` int(10) unsigned NOT NULL,
  `join_time` datetime NOT NULL,
  PRIMARY KEY (`project_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `project_team_ref`;

CREATE TABLE `project_team_ref` (
  `project_id` int(10) unsigned NOT NULL,
  `team_id` int(10) unsigned NOT NULL,
  `create_user_id` int(10) unsigned NOT NULL,
  `del_flag` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`project_id`,`team_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `project_user_ref`;

CREATE TABLE `project_user_ref` (
  `project_id` int(10) unsigned NOT NULL,
  `user_id` int(10) unsigned NOT NULL,
  `del_flag` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`project_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `team_info`;

CREATE TABLE `team_info` (
  `team_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `team_name` varchar(20) NOT NULL,
  `description` text,
  `del_flag` tinyint(4) DEFAULT '0',
  `create_user_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`team_id`),
  UNIQUE KEY `team_name` (`team_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `user_info`;

CREATE TABLE `user_info` (
  `user_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) NOT NULL,
  `avatar` varchar(100) DEFAULT NULL,
  `sex` char(1) ,
  `introduction` text,
  `phone_number` char(11) DEFAULT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(20) NOT NULL,
  `del_flag` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_name` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `team_member_info`;

CREATE TABLE `team_member_info`
(
  member_id int auto_increment primary key,
  user_id   int    not null,
  team_id   int      not null,
  join_time datetime      not null,
  del_flag  tinyint default '0' null
)ENGINE=InnoDB DEFAULT CHARSET=utf8;