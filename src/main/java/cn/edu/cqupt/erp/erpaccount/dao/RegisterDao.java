package cn.edu.cqupt.erp.erpaccount.dao;

import cn.edu.cqupt.erp.erpaccount.entity.Register;

import java.util.List;

public interface RegisterDao {
    int addRegister(Register register);

    int  updateRegister(Register register);

    Register findRegisterByUserId(String userID);

    List<Register> findAllRegister();

    int deleteRegisterById(String userID);

}
