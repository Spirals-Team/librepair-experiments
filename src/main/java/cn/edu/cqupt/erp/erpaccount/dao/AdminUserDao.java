package cn.edu.cqupt.erp.erpaccount.dao;

import cn.edu.cqupt.erp.erpaccount.entity.AdminUser;

import java.util.List;
public interface AdminUserDao {
	List<AdminUser> getAll();

	AdminUser findAdminuserByAdminID(String adminId);
}
