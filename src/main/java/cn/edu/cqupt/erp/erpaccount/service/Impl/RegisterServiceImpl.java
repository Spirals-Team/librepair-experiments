package cn.edu.cqupt.erp.erpaccount.service.Impl;

import cn.edu.cqupt.erp.erpaccount.constant.UserOperateConstant;
import cn.edu.cqupt.erp.erpaccount.entity.ApprovedUser;
import cn.edu.cqupt.erp.erpaccount.entity.Register;
import cn.edu.cqupt.erp.erpaccount.manager.ApprovedUserManager;
import cn.edu.cqupt.erp.erpaccount.manager.RegisterManager;
import cn.edu.cqupt.erp.erpaccount.service.RegisterService;
import cn.edu.cqupt.erp.erpaccount.util.MapUtil;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/register")
public class RegisterServiceImpl implements RegisterService{

    @Resource
    private RegisterManager registerManager;
    @Resource
    private ApprovedUserManager approvedUserManager;

    @Override
    @RequestMapping(value = "/addRegister", method = RequestMethod.POST)
    public String addRegister(@RequestBody Register register){
        if (!StringUtils.isNotBlank(register.getUserID()) || !StringUtils.isNotBlank(register.getName()) || !StringUtils.isNotBlank(register.getPassword()) || !StringUtils.isNotBlank(register.getStudentID())){
            return UserOperateConstant.FAIL_FLAG;
        }
        register.setStatus(UserOperateConstant.REGISTER_STATUS);
        boolean addSuccess = registerManager.addRegister(register);
        Map map;
        if (addSuccess){
            map = MapUtil.toMap(true,UserOperateConstant.SUCCESS_FLAG,null);
        }else {
            map = MapUtil.toMap(false,UserOperateConstant.FAIL_FLAG,null);
        }
        String result = JSON.toJSONString(map);
        return result;
    }

    @Override
    @RequestMapping(value = "/modifyUserInfo", method = RequestMethod.POST)
    public String modifyUserInfo(@RequestBody Register register) {
        Register register1 = registerManager.findRegisterByUserId(register.getUserID());
        Map map;
        String result;
        if (register1 != null){
            registerManager.updateRegister(register);
            map = MapUtil.toMap(true,UserOperateConstant.SUCCESS_FLAG,null);
            result = JSON.toJSONString(map);
            return result;
        }
        ApprovedUser approvedUser = approvedUserManager.findApproveduserById(register.getUserID());
        if (approvedUser != null){
            approvedUserManager.updateApproveduser(register);
            map = MapUtil.toMap(true,UserOperateConstant.SUCCESS_FLAG,null);
            result = JSON.toJSONString(map);
            return result;
        }
        map = MapUtil.toMap(false,UserOperateConstant.FAIL_FLAG,null);
        result = JSON.toJSONString(map);
        return result;
    }

}
