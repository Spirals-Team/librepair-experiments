package cn.edu.cqupt.erp.erpaccount.dao;

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
public class RegisterDaoTest {
    @Autowired
    private RegisterDao registerDao;

    @Test
    public void addRegister(){
        Register register =  new Register();
        register.setUserID("2016211036");
        register.setName("chenmeiling");
        register.setPassword("123456");
        register.setStudentID("2016211036");
        register.setClassName("03011603");

        int rows = registerDao.addRegister(register);
        Assert.assertTrue(1 == rows);
    }

    @Test
    public void findRegisterByUserId(){
        Register register = registerDao.findRegisterByUserId("Zane");
        String name = register.getName();
        Assert.assertEquals("徐志", name);
    }

    @Test
    public void updateRegister(){
        Register register = new Register();
        register.setUserID("Zane");
        register.setClassName("012345");

        int result = registerDao.updateRegister(register);
        Assert.assertTrue(1 == result);
    }

    @Test
    public void deleteRegisterById(){
        int rows = registerDao.deleteRegisterById("2016211036");
        Assert.assertTrue(1 == rows);
    }

    @Test
    public void findAllRegister(){
        List<Register> registers = registerDao.findAllRegister();
        Assert.assertTrue(!registers.isEmpty());
    }
}
