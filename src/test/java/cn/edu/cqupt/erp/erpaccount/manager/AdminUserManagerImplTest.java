package cn.edu.cqupt.erp.erpaccount.manager;

import cn.edu.cqupt.erp.erpaccount.dao.AdminUserDao;
import cn.edu.cqupt.erp.erpaccount.entity.AdminUser;
import cn.edu.cqupt.erp.erpaccount.manager.impl.AdminUserManagerImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class AdminUserManagerImplTest {
    @Mock
    private AdminUserDao adminUserDao;

    @InjectMocks
    AdminUserManager adminUserManager = new AdminUserManagerImpl();

    private AdminUser adminUser;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        adminUser = new AdminUser();
        adminUser.setAdminId("111");
        adminUser.setAdminName("hello");
        adminUser.setPassword("123");
    }

    @Test
    public void findAdminuserByAdminID(){
        doReturn(adminUser).when(adminUserDao).findAdminuserByAdminID(anyString());
        AdminUser adminUser = adminUserManager.findAdminuserByAdminID("111");
        Assert.assertEquals("hello",adminUser.getAdminName());
    }
}
