package com.may.apimanagementsystem.user.service;

import com.may.apimanagementsystem.dao.InterfaceMapper;
import com.may.apimanagementsystem.po.Interfaces;
import com.may.apimanagementsystem.po.Project;
import com.may.apimanagementsystem.service.InterfaceService;
import com.may.apimanagementsystem.service.ProjectService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class InterfaceServiceTest {

    @Mock
    private InterfaceMapper interfaceMapper;

    @InjectMocks
    private InterfaceService interfaceService = new InterfaceService();

    @Before
    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getInterfaces() throws Exception {
        List<Interfaces> interfaceList = new ArrayList<>();
        Interfaces interfaces = new Interfaces();
        interfaces.setInterfaceId(1000);
        interfaces.setInterfaceName("test");
        interfaces.setMethod("post");
        interfaces.setDescription("test");
        interfaces.setUrl("/test");
        interfaces.setJson("json");
        interfaces.setProjectId(9);
        interfaces.setDelFlag(0);
        interfaceList.add(interfaces);
        doReturn(interfaceList).when(interfaceMapper).getInterfaceList(anyInt());
        List<Interfaces> interfacesList1 = interfaceService.getInterfaces(1,1,9);
        assertEquals(interfacesList1,interfaceList);
    }

    @Test
    public void addInterface() throws Exception {
        Interfaces interfaces = new Interfaces();
        interfaces.setInterfaceId(1000);
        interfaces.setInterfaceName("test");
        interfaces.setMethod("post");
        interfaces.setDescription("test");
        interfaces.setUrl("/test");
        interfaces.setJson("json");
        interfaces.setProjectId(9);
        interfaces.setDelFlag(0);
        doReturn(true).when(interfaceMapper).insertInterface(any(Interfaces.class));
        Boolean res = interfaceService.addInterface(interfaces);
        assertTrue(res);
    }

    @Test
    public void updateInterface() throws Exception{
        Interfaces interfaces = new Interfaces();
        interfaces.setInterfaceId(1000);
        interfaces.setInterfaceName("test");
        interfaces.setMethod("post");
        interfaces.setDescription("test");
        interfaces.setUrl("/test");
        interfaces.setJson("json");
        interfaces.setProjectId(9);
        interfaces.setDelFlag(0);
        doReturn(true).when(interfaceMapper).updateInterface(any(Interfaces.class));

        Interfaces interfaces1 = interfaces;
        interfaces1.setInterfaceName("updateTest");
        Boolean res = interfaceService.updateInterface(interfaces1);
        assertTrue(res);
    }

    @Test
    public void removeInterface() throws Exception{
        Interfaces interfaces = new Interfaces();
        interfaces.setInterfaceId(1000);
        interfaces.setInterfaceName("test");
        interfaces.setMethod("post");
        interfaces.setDescription("test");
        interfaces.setUrl("/test");
        interfaces.setJson("json");
        interfaces.setProjectId(9);
        interfaces.setDelFlag(0);
        doReturn(true).when(interfaceMapper).deleteInterface(1000);

        Interfaces interfaces1 = interfaces;
        interfaces1.setDelFlag(1);
        Boolean res = interfaceService.removeInterface(1000);
        assertTrue(res);
    }

    @Test
    public void getInterfaceByInterfaceId() throws Exception{
        Interfaces interfaces = new Interfaces();
        interfaces.setInterfaceId(1000);
        interfaces.setInterfaceName("test");
        interfaces.setMethod("post");
        interfaces.setDescription("test");
        interfaces.setUrl("/test");
        interfaces.setJson("json");
        interfaces.setProjectId(9);
        interfaces.setDelFlag(0);
        doReturn(interfaces).when(interfaceMapper).findInterfaceByInterfaceId(anyInt());
        Interfaces interfaces1 = interfaceService.getInterfaceByInterfaceId(1000);
        assertEquals(interfaces,interfaces1);
    }


}
