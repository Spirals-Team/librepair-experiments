package cn.edu.cqupt.erp.erpaccount.service.Impl;

import com.alibaba.fastjson.JSON;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import javax.annotation.Resource;

import cn.edu.cqupt.erp.erpaccount.manager.AdminUserManager;
import cn.edu.cqupt.erp.erpaccount.service.AdminUserService;

@RestController
public class AdminUserServiceImpl implements AdminUserService {
	@Resource
	private AdminUserManager adminUserManager;

	@RequestMapping("/test")
	public String test() {
		String result = JSON.toJSONString(adminUserManager.getAllAdmins());
		return result;
	}

	public boolean test1() {
		return false;
	}
}
