package cn.edu.cqupt.erp.erpaccount.manager;

import cn.edu.cqupt.erp.erpaccount.entity.ApprovedUser;
import cn.edu.cqupt.erp.erpaccount.entity.Register;

import java.util.List;

public interface ApprovedUserManager {
    boolean updateApproveduser(Register register);

    ApprovedUser findApproveduserById(String approveduserId);

    boolean insertApprovedUser(String registerId);

    List<ApprovedUser> findAllApproveduser();
}
