package cn.edu.cqupt.erp.erpaccount.entity;

import lombok.Data;

@Data
public class ApprovedUser {
    private  String  userID;
    private  String  name;
    private  String  password;
    private  String  major;
    private  String  className;
    private  String  studentID;
    private  String  email;
    private  String  tel;
    //用户类型，与注册表不一样的地方
    private  String  typeID;
}
