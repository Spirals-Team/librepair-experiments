package cn.edu.cqupt.erp.erpaccount.service;

import cn.edu.cqupt.erp.erpaccount.entity.Register;
import cn.edu.cqupt.erp.erpaccount.manager.ApprovedUserManager;
import cn.edu.cqupt.erp.erpaccount.manager.RegisterManager;
import cn.edu.cqupt.erp.erpaccount.service.Impl.RegisterServiceImpl;
import cn.edu.cqupt.erp.erpaccount.util.MapUtil;
import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class RegisterServiceImplTest {
    @Mock
    private RegisterManager registerManager;
    @Mock
    private ApprovedUserManager approvedUserManager;

    @InjectMocks
    private RegisterService registerService = new RegisterServiceImpl();

    private Register register;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        register = new Register();
        register.setUserID("123456");
        register.setClassName("123456");
        register.setName("lalala");
        register.setStudentID("123456");
        register.setPassword("123456");
    }

    @Test
    public void addRegister(){
        Map map;
        doReturn(true).when(registerManager).addRegister(anyObject());
        String result = registerService.addRegister(register);
        map = MapUtil.toMap(true,"success",null);
        String mapStr = JSON.toJSONString(map);
        Assert.assertEquals(mapStr,result);
    }
}
