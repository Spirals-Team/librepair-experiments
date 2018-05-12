package com.tangly.entity;

import com.tangly.entity.HelloWorld;
import com.tangly.util.ValidateUtil;
import org.junit.Test;

/**
 * date: 2018/5/7 16:12 <br/>
 *
 * @author Administrator
 * @since JDK 1.7
 */
public class HelloWorldTest {

    /**
     * 预期抛出参数非法异常
     */
    @Test(expected = IllegalArgumentException.class)
    public void testValidate(){

        HelloWorld helloWorld2 = new HelloWorld("哈哈哈","1123");
        ValidateUtil.validate(helloWorld2);

    }

}