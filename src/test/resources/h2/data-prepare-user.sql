INSERT INTO `adminuser` (`ADMINID`, `ADMINNAME`, `PASSWORD`, `POSITION`, `EMAIL`, `TEL`, `REM1`, `ROLE`) VALUES ('0101311', '王永', '123', '老师', NULL, NULL, NULL, '管理员');
INSERT INTO `adminuser` (`ADMINID`, `ADMINNAME`, `PASSWORD`, `POSITION`, `EMAIL`, `TEL`, `REM1`, `ROLE`) VALUES ('1', '1', '1', '老师', NULL, NULL, NULL, '管理员');


INSERT INTO `registeruser` (`USERID`, `USERNAME`, `PASSWORD`, `MAJOR`, `CLASS`, `SID`, `EMAIL`, `TEL`, `STATUS`, `REM1`, `REM2`) VALUES ('Zane', '徐志', 'xz960104', '信息管理与商务智能实验班', '0391402', '2014210876', 'fuck@u.com', '1888399000000000', '等待审批', NULL, NULL);
INSERT INTO `registeruser` (`USERID`, `USERNAME`, `PASSWORD`, `MAJOR`, `CLASS`, `SID`, `EMAIL`, `TEL`, `STATUS`, `REM1`, `REM2`) VALUES ('张梦龙', '张梦龙', '207710', '信息管理与商务智能创新实验班', '0391402', '2014211036', '879630422@qq.com', '18883944233', '等待审批', NULL, NULL);



INSERT INTO `approveduser` (`USERID`, `USERNAME`, `PASSWORD`, `MAJOR`, `CLASS`, `SID`, `EMAIL`, `TEL`, `TYPEID`, `REM1`, `REM2`) VALUES ('0101311', '王永', '123', '信息管理', '1', '0101311', '1@1.com', '1', '1', NULL, NULL);
INSERT INTO `approveduser` (`USERID`, `USERNAME`, `PASSWORD`, `MAJOR`, `CLASS`, `SID`, `EMAIL`, `TEL`, `TYPEID`, `REM1`, `REM2`) VALUES ('1', '1', '123456', '1', '1', '1', NULL, NULL, '1', '1', NULL);
INSERT INTO `approveduser` (`USERID`, `USERNAME`, `PASSWORD`, `MAJOR`, `CLASS`, `SID`, `EMAIL`, `TEL`, `TYPEID`, `REM1`, `REM2`) VALUES ('12', '12', '12', '12', '12', '12', '12@qq.com', '12', '1', NULL, NULL);
INSERT INTO `approveduser` (`USERID`, `USERNAME`, `PASSWORD`, `MAJOR`, `CLASS`, `SID`, `EMAIL`, `TEL`, `TYPEID`, `REM1`, `REM2`) VALUES ('13456', 'ERP系统测试', '123456', '计算机科学', '311402', '13456', NULL, NULL, '1', '计算机学院', NULL);
INSERT INTO `approveduser` (`USERID`, `USERNAME`, `PASSWORD`, `MAJOR`, `CLASS`, `SID`, `EMAIL`, `TEL`, `TYPEID`, `REM1`, `REM2`) VALUES ('2', '2', '123456', '2', '2', '2', NULL, NULL, '1', '2', NULL);
