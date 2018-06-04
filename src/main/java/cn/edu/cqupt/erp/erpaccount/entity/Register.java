package cn.edu.cqupt.erp.erpaccount.entity;

import lombok.Data;

@Data
public class Register {
    private  String  userID;
    private  String  name;
    private  String  password;
    private  String  major;
    private  String  className;
    private  String  studentID;
    private  String  email;
    private  String  tel;
    //未获得审批的原因字段
    private  String  status;
    //存放学院属性
    private  String  rem1;
}
