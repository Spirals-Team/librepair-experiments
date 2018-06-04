package cn.edu.cqupt.erp.erpaccount.manager.impl;

import cn.edu.cqupt.erp.erpaccount.dao.AdminUserDao;
import cn.edu.cqupt.erp.erpaccount.entity.AdminUser;
import cn.edu.cqupt.erp.erpaccount.manager.AdminUserManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class AdminUserManagerImpl implements AdminUserManager {
	@Resource
	private AdminUserDao adminUserDao;

	@Override
	public List<AdminUser> getAllAdmins() {
		return null;
	}

	@Override
	public AdminUser findAdminuserByAdminID(String adminId) {
		return adminUserDao.findAdminuserByAdminID(adminId);
	}
}
