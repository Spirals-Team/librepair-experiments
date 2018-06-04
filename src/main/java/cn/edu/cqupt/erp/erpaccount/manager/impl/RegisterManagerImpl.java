package cn.edu.cqupt.erp.erpaccount.manager.impl;

import cn.edu.cqupt.erp.erpaccount.dao.ApproveduserDao;
import cn.edu.cqupt.erp.erpaccount.dao.RegisterDao;
import cn.edu.cqupt.erp.erpaccount.entity.ApprovedUser;
import cn.edu.cqupt.erp.erpaccount.entity.Register;
import cn.edu.cqupt.erp.erpaccount.manager.RegisterManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class RegisterManagerImpl implements RegisterManager {
    @Resource
    private RegisterDao registerDao;

    @Resource
    private ApproveduserDao approveduserDao;

    @Override
    public boolean addRegister(Register register) {
        Register register1 = registerDao.findRegisterByUserId(register.getUserID());
        if (register1 != null) {
            return false;
        }
        ApprovedUser approvedUser = approveduserDao.findApproveduserById(register.getUserID());
        if (approvedUser != null){
            return false;
        }
        int result = registerDao.addRegister(register);
        return result == 1;
    }

    @Override
    public boolean updateRegister(Register register) {
        int result = registerDao.updateRegister(register);
        return result == 1;
    }

    @Override
    public Register findRegisterByUserId(String registerId) {
        return registerDao.findRegisterByUserId(registerId);
    }

    @Override
    public List<Register> findAllRegister() {
        return registerDao.findAllRegister();
    }

    @Override
    public boolean deleteRegisterById(String registerId) {
        int result = registerDao.deleteRegisterById(registerId);
        return result == 1;
    }
}
