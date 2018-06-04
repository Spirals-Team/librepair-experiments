package cn.edu.cqupt.erp.erpaccount.manager;

import cn.edu.cqupt.erp.erpaccount.entity.AdminUser;

import java.util.List;

public interface AdminUserManager {
	List<AdminUser> getAllAdmins();

	AdminUser findAdminuserByAdminID(String adminId);
}
