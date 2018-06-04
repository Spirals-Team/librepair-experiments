package cn.edu.cqupt.erp.erpaccount.service;

public interface UserManagerService {
    /**
     * 查找所有已注册用户信息
     * @return
     */
    String findAllRegister();

    String passRegisterUser(String registerId);

    String findAllApproveduser();
}
