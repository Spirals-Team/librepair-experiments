package cn.edu.cqupt.erp.erpaccount.manager;

import cn.edu.cqupt.erp.erpaccount.entity.Register;

import java.util.List;

public interface RegisterManager {
    boolean addRegister(Register register);

    boolean updateRegister(Register register);

    Register findRegisterByUserId(String registerId);

    List<Register> findAllRegister();

    boolean deleteRegisterById(String registerId);
}
