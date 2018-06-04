DROP TABLE IF EXISTS `adminuser`;
CREATE TABLE `adminuser` (
  `ADMINID` varchar(50) NOT NULL,
  `ADMINNAME` varchar(50) NOT NULL,
  `PASSWORD` varchar(30) NOT NULL,
  `POSITION` varchar(50) NOT NULL,
  `EMAIL` varchar(50) DEFAULT NULL,
  `TEL` varchar(50) DEFAULT NULL,
  `REM1` varchar(50) DEFAULT NULL,
  `ROLE` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`ADMINID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `approveduser`;
CREATE TABLE `approveduser` (
  `USERID` varchar(50) NOT NULL,
  `USERNAME` varchar(50) NOT NULL,
  `PASSWORD` varchar(30) NOT NULL,
  `MAJOR` varchar(30) DEFAULT NULL,
  `CLASS` varchar(30) DEFAULT NULL,
  `SID` varchar(50) NOT NULL,
  `EMAIL` varchar(50) DEFAULT NULL,
  `TEL` varchar(50) DEFAULT NULL,
  `TYPEID` varchar(5) DEFAULT NULL,
  `REM1` varchar(200) DEFAULT NULL,
  `REM2` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`USERID`),
  KEY `SID` (`SID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `registeruser`;
CREATE TABLE `registeruser` (
  `USERID` varchar(50) NOT NULL,
  `USERNAME` varchar(50) NOT NULL,
  `PASSWORD` varchar(30) NOT NULL,
  `MAJOR` varchar(30) DEFAULT NULL,
  `CLASS` varchar(30) DEFAULT NULL,
  `SID` varchar(50) NOT NULL,
  `EMAIL` varchar(50) DEFAULT NULL,
  `TEL` varchar(50) DEFAULT NULL,
  `STATUS` varchar(200) DEFAULT NULL,
  `REM1` varchar(200) DEFAULT NULL,
  `REM2` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`USERID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
