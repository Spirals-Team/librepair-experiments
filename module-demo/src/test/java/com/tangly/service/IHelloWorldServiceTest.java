package com.tangly.service;

import com.tangly.DemoApplication;
import com.tangly.controller.HelloWorldController;
import com.tangly.entity.HelloWorld;
import com.tangly.service.impl.HelloWorldService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Random;

/**
 * Junit单元测试
 * @author $user
 * @version 1.0
 * @since <pre>01/02/2018</pre>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class IHelloWorldServiceTest {

    @Autowired
    private IHelloWorldService iHelloWorldService;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    @Transactional
    @Rollback
    public void saveAndGet() {
        iHelloWorldService.insert(new HelloWorld("测试名字", "13800138000"));
        Assert.assertNotNull(iHelloWorldService.selectOne(new HelloWorld("测试名字", "13800138000")));
    }

    @Test
    @Transactional
    public void selectByExample() {
        Random r = new Random();
        for (int i = 1; i <= 20; i++) {
            iHelloWorldService.insert(new HelloWorld("测试名字" + String.valueOf(i), "13800138000"));
        }
        Example example = new Example(HelloWorld.class);
        example.setOrderByClause("id DESC");
        example.createCriteria().andEqualTo("phoneNum","13800138000");
        List<HelloWorld> list = iHelloWorldService.selectByExample(example);
        Assert.assertEquals(list.size(),20);
        System.out.println(list);
    }

}
