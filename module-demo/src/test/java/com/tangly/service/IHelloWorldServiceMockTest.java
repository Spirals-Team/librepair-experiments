package com.tangly.service;

import com.tangly.DemoApplication;
import com.tangly.entity.HelloWorld;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * Mockito 隔离测试
 * @description https://www.tianmaying.com/tutorial/JunitForSpringBoot
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class IHelloWorldServiceMockTest {

    /**
     * 模拟注入一个其它模块的接口或任意对象
     */
    @Mock
    private IOtherService iOtherService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Autowired
    private IHelloWorldService iHelloWorldService;

    @Test
    @Transactional
    @Rollback
    public void testGetHelloWorld() {

        HelloWorld t = new HelloWorld("哈哈","123");

        //当这个接口执行某个方法时令其返回某个特定对象
        when(iOtherService.someOtherMethod(1)).thenReturn(t);

        HelloWorld helloWorld = iOtherService.someOtherMethod(1);
        assertEquals(t, helloWorld);
        System.out.println("从某个模拟接口中获取到的返回" + helloWorld);

        //调用HelloWorld模块的接口方法并测试是否异常
        iHelloWorldService.insert(helloWorld);
        HelloWorld myHelloWorld = iHelloWorldService.selectOne(new HelloWorld("哈哈","123"));
        assertNotNull(myHelloWorld);
        System.out.println(myHelloWorld);

    }
}