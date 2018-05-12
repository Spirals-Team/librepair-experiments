package com.tangly.mapper;

import com.tangly.entity.HelloWorld;
import org.apache.shiro.util.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Transient;
import java.util.List;

/**
 * Created by tangly on 2018/4/15.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloWorldMapperTest {

    @Test
    @Transient
    @Rollback
    public void addHelloWorld() throws Exception {
        helloWorldMapper.insert(new HelloWorld("你好世界"));
    }

    @Autowired
    HelloWorldMapper helloWorldMapper;

    @Test
    @Transient
    @Rollback
    public void getAll() throws Exception {
        helloWorldMapper.insert(new HelloWorld("你好世界"));

        List<HelloWorld> helloWordlList = helloWorldMapper.selectAll();
        Assert.notNull(helloWordlList);
        System.out.println("helloWorld:"+ helloWordlList);
    }

}