package com.may.apimanagementsystem.user.dao;

import com.may.apimanagementsystem.dao.InterfaceMapper;
import com.may.apimanagementsystem.po.Interfaces;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
public class InterfaceDaoTest {

    @Autowired
    private InterfaceMapper interfaceMapper;

    private Interfaces interfaces;

    @Before
    public void before() {
        interfaces = new Interfaces();
        interfaces.setInterfaceName("TestInterface");
        interfaces.setInterfaceId(1001);
        interfaces.setMethod("post");
        interfaces.setUrl("/test");
        interfaces.setProjectId(9);
        interfaces.setDescription("This is a test");
        interfaces.setJson("{\n" +
                "    \"message\":\"操作成功\"，\n" +
                "    \"status\":200,\n" +
                "    \"data\":null\n" +
                "}");
    }

    @Test
    public void getInterfaceListTest() {
        List<Interfaces> interfaces = interfaceMapper.getInterfaceList(9);
        assertEquals(interfaces, interfaces);
    }

    @Test
    public void deleteInterfaceTest() {
        boolean flag = interfaceMapper.deleteInterface(100);
        assertEquals(true, flag);
    }

    @Test
    public void insertInterfaceTest() {
        boolean flag = interfaceMapper.insertInterface(interfaces);
        assertEquals(true, flag);
    }

    @Test
    public void updateInterfaceTest() {
        interfaces = new Interfaces();
        interfaces.setInterfaceName("TestInterface");
        interfaces.setInterfaceId(100);
        interfaces.setMethod("post");
        interfaces.setUrl("/test");
        interfaces.setProjectId(9);
        interfaces.setDescription("test update");
        boolean flag = interfaceMapper.updateInterface(interfaces);
        assertEquals(true, flag);
    }

    @Test
    public void findInterfaceByInterfaceIdTest() {
        Interfaces interfaces = interfaceMapper.findInterfaceByInterfaceId(9);
        assertEquals(null, interfaces);
    }
}
