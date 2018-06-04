package cn.edu.cqupt.erp.erpaccount.service;

import cn.edu.cqupt.erp.erpaccount.entity.Register;

public interface RegisterService {
    String addRegister(Register register);

    String modifyUserInfo(Register register);
}
