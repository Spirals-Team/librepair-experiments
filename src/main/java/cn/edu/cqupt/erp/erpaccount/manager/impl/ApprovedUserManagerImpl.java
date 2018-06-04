package cn.edu.cqupt.erp.erpaccount.manager.impl;

import cn.edu.cqupt.erp.erpaccount.dao.ApproveduserDao;
import cn.edu.cqupt.erp.erpaccount.entity.ApprovedUser;
import cn.edu.cqupt.erp.erpaccount.entity.Register;
import cn.edu.cqupt.erp.erpaccount.manager.ApprovedUserManager;
import cn.edu.cqupt.erp.erpaccount.manager.RegisterManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class ApprovedUserManagerImpl implements ApprovedUserManager {
    @Resource
    ApproveduserDao approveduserDao;

    @Resource
    RegisterManager registerManager;

    @Override
    public boolean updateApproveduser(Register register) {
        int result = approveduserDao.updateApproveduser(register);
        return result == 1;
    }

    @Override
    public ApprovedUser findApproveduserById(String approveduserId) {
        return approveduserDao.findApproveduserById(approveduserId);
    }

    @Override
    public boolean insertApprovedUser(String registerId) {
        Register register = registerManager.findRegisterByUserId(registerId);
        if (register == null){
            return false;
        }
        int addSuccess = approveduserDao.addApproveduser(register);
        Boolean deleteSuccess = registerManager.deleteRegisterById(registerId);
        if (addSuccess == 1 && deleteSuccess) {
            return true;
        }
        return false;
    }

    @Override
    public List<ApprovedUser> findAllApproveduser() {
        return approveduserDao.findAllApproveduser();
    }
}
