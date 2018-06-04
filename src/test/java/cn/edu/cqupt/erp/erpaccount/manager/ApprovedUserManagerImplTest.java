package cn.edu.cqupt.erp.erpaccount.manager;

import cn.edu.cqupt.erp.erpaccount.dao.ApproveduserDao;
import cn.edu.cqupt.erp.erpaccount.entity.ApprovedUser;
import cn.edu.cqupt.erp.erpaccount.entity.Register;
import cn.edu.cqupt.erp.erpaccount.manager.impl.ApprovedUserManagerImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class ApprovedUserManagerImplTest {
    @Mock
    private ApproveduserDao approveduserDao;

    @InjectMocks
    ApprovedUserManager approvedUserManager = new ApprovedUserManagerImpl();

    private ApprovedUser approvedUser;
    private Register register;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        approvedUser = new ApprovedUser();
        approvedUser.setUserID("111");
        approvedUser.setName("hello");
        approvedUser.setStudentID("111");
        approvedUser.setPassword("1234");

        register = new Register();
        register.setUserID("123");
        register.setPassword("111");
        register.setStudentID("123");
        register.setName("world");
    }

    @Test
    public void updateApproveduser(){
        doReturn(1).when(approveduserDao).updateApproveduser(anyObject());
        register.setPassword("123");
        register.setMajor("aaa");
        Boolean result = approvedUserManager.updateApproveduser(register);
        Assert.assertTrue(result);
    }

    @Test
    public void findApproveduserById(){
        doReturn(approvedUser).when(approveduserDao).findApproveduserById(anyString());
        ApprovedUser approvedUser = approvedUserManager.findApproveduserById("111");
        Assert.assertEquals("hello",approvedUser.getName());
    }

    @Test
    public void findAllApproveduser(){
        List<ApprovedUser> list = new LinkedList<>();
        doReturn(list).when(approveduserDao).findAllApproveduser();
        List<ApprovedUser> approvedUsers = approvedUserManager.findAllApproveduser();
        Assert.assertEquals(list, approvedUsers);
    }
}
