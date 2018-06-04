package cn.edu.cqupt.erp.erpaccount.entity;

import lombok.Data;

@Data
public class AdminUser {
	private String adminId;
	private String adminName;
	private String password;
	private String position;
	private String email;
	private String tel;
	// 备注
	private String rem1;
	private String role;
}
