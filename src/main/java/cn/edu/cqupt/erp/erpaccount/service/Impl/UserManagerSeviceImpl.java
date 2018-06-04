package cn.edu.cqupt.erp.erpaccount.service.Impl;

import cn.edu.cqupt.erp.erpaccount.constant.UserOperateConstant;
import cn.edu.cqupt.erp.erpaccount.entity.ApprovedUser;
import cn.edu.cqupt.erp.erpaccount.entity.Register;
import cn.edu.cqupt.erp.erpaccount.manager.ApprovedUserManager;
import cn.edu.cqupt.erp.erpaccount.manager.RegisterManager;
import cn.edu.cqupt.erp.erpaccount.service.UserManagerService;
import cn.edu.cqupt.erp.erpaccount.util.MapUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/userManager")
public class UserManagerSeviceImpl implements UserManagerService {
    @Resource
    private RegisterManager registerManager;
    @Resource
    private ApprovedUserManager approvedUserManager;

    @Override
    @RequestMapping(value = "/findAllRegister",method = RequestMethod.GET)
    public String findAllRegister() {
        List<Register> registers = registerManager.findAllRegister();
        Map map;
        if (!registers.isEmpty()){
            map = MapUtil.toMap(true, UserOperateConstant.SUCCESS_FLAG,registers);
        } else {
            map = MapUtil.toMap(false,UserOperateConstant.FAIL_FLAG,null);
        }
        String result = JSON.toJSONString(map);
        return result;
    }

    @Override
    @RequestMapping(value = "/passRegisterUser", method = RequestMethod.POST)
    public String passRegisterUser(@RequestBody String registerId) {
        Boolean passSuccess = approvedUserManager.insertApprovedUser(registerId);
        Map map;
        if (passSuccess){
            map = MapUtil.toMap(true,UserOperateConstant.SUCCESS_FLAG,null);
        } else {
            map = MapUtil.toMap(false,UserOperateConstant.FAIL_FLAG,null);
        }
        String result = JSON.toJSONString(map);
        return result;
    }

    @Override
    @RequestMapping(value = "/findAllApproveduser", method = RequestMethod.GET)
    public String findAllApproveduser() {
        List<ApprovedUser> approvedUsers = approvedUserManager.findAllApproveduser();
        Map map;
        if (!approvedUsers.isEmpty()){
            map = MapUtil.toMap(true, UserOperateConstant.SUCCESS_FLAG,approvedUsers);
        } else {
            map = MapUtil.toMap(false,UserOperateConstant.FAIL_FLAG,null);
        }
        String result = JSON.toJSONString(map);
        return result;
    }
}
