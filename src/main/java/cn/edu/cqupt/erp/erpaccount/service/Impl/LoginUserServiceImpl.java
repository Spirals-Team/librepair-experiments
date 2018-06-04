package cn.edu.cqupt.erp.erpaccount.service.Impl;

import cn.edu.cqupt.erp.erpaccount.constant.UserOperateConstant;
import cn.edu.cqupt.erp.erpaccount.entity.AdminUser;
import cn.edu.cqupt.erp.erpaccount.entity.ApprovedUser;
import cn.edu.cqupt.erp.erpaccount.entity.Register;
import cn.edu.cqupt.erp.erpaccount.manager.AdminUserManager;
import cn.edu.cqupt.erp.erpaccount.manager.ApprovedUserManager;
import cn.edu.cqupt.erp.erpaccount.manager.RegisterManager;
import cn.edu.cqupt.erp.erpaccount.service.LoginUserService;
import cn.edu.cqupt.erp.erpaccount.util.MapUtil;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginUserServiceImpl implements LoginUserService {
    @Resource
    private RegisterManager registerManager;
    @Resource
    private ApprovedUserManager approvedUserManager;
    @Resource
    private AdminUserManager adminUserManager;

    @Override
    @RequestMapping(value = "/loginUser", method = RequestMethod.GET)
    public String loginUser(String userId, String password, String status) {
        Map map;
        String loginResult;
        if (!StringUtils.isNotBlank(userId) || !StringUtils.isNotBlank(password) || !StringUtils.isNotBlank(status)){
            map = MapUtil.toMap(false,UserOperateConstant.FAIL_FLAG,UserOperateConstant.PARAM_ERROR);
            loginResult = JSON.toJSONString(map);
            return loginResult;
        }
        if (UserOperateConstant.STUDENT_STATUS.equals(status)) {
            Register register = registerManager.findRegisterByUserId(userId);
            if (register != null && password.equals(register.getPassword())){
                    map = MapUtil.toMap(true,UserOperateConstant.SUCCESS_FLAG,null);
                    loginResult = JSON.toJSONString(map);
                    return loginResult;
            }
            ApprovedUser approvedUser = approvedUserManager.findApproveduserById(userId);
            if (approvedUser != null && password.equals(approvedUser.getPassword())){
                    map = MapUtil.toMap(true,UserOperateConstant.SUCCESS_FLAG,null);
                    loginResult = JSON.toJSONString(map);
                    return loginResult;
            }
            map = MapUtil.toMap(false,UserOperateConstant.FAIL_FLAG,UserOperateConstant.USER_NOT_EXIT);
            loginResult = JSON.toJSONString(map);
            return loginResult;
        }
        if (UserOperateConstant.TEACHER_STATUS.equals(status)){
            AdminUser adminUser = adminUserManager.findAdminuserByAdminID(userId);
            if (adminUser != null && password.equals(adminUser.getPassword())){
                    map = MapUtil.toMap(true, UserOperateConstant.SUCCESS_FLAG,null);
                    loginResult = JSON.toJSONString(map);
                    return loginResult;
            }
            map = MapUtil.toMap(false,UserOperateConstant.FAIL_FLAG,UserOperateConstant.PASSWORD_ERROR);
            loginResult = JSON.toJSONString(map);
            return loginResult;
        }
        map = MapUtil.toMap(false,UserOperateConstant.FAIL_FLAG,UserOperateConstant.USER_NOT_EXIT);
        loginResult = JSON.toJSONString(map);
        return loginResult;
    }
}
