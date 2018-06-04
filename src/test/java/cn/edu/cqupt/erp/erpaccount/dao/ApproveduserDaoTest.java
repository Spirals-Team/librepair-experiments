package cn.edu.cqupt.erp.erpaccount.dao;

import cn.edu.cqupt.erp.erpaccount.entity.ApprovedUser;
import cn.edu.cqupt.erp.erpaccount.entity.Register;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApproveduserDaoTest {
    @Autowired
    private ApproveduserDao approveduserDao;

    @Test
    public void findApproveduserById(){
        ApprovedUser approvedUser = approveduserDao.findApproveduserById("0101311");
        String name = approvedUser.getName();
        Assert.assertEquals("王永",name);
    }

    @Test
    public void updateApproveduser(){
        Register register = new Register();
        register.setUserID("0101311");
        register.setClassName("2");
        register.setMajor("工商");

        int result = approveduserDao.updateApproveduser(register);
        Assert.assertTrue(1 == result);
    }

    @Test
    public void addApproveduser(){
        Register register = new Register();
        register.setUserID("123");
        register.setName("hello");
        register.setPassword("111");
        register.setStudentID("123");

        int result = approveduserDao.addApproveduser(register);
        Assert.assertTrue(1 == result);
    }

    @Test
    public void findAllApproveduser(){
        List<ApprovedUser> approvedUsers = approveduserDao.findAllApproveduser();
        Assert.assertTrue(!approvedUsers.isEmpty());
    }
}
