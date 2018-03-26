/**  
 * Copyright © 2017 wowostar. All rights reserved.
 *
 * @Title: EkongPolicyTest.java
 * @Project: com.wowostar.ekongsdk
 * @Package: com.wowostar.ekongsdk
 * @Description: 易控SDK策略测试用例
 * @author: ouqin
 * @date: 2017年11月14日
 * @version: V1.0
 */

package com.wowostar.ekongsdk;

import com.wowostar.ekongsdk.common.EkongException;

import java.util.Calendar;
import java.util.Date;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author: ouqin
 * @date: 2017年11月14日
 */
public class EkongPolicyTest {

    /**
     * 异常期望
     */
    @Rule
    public ExpectedException excepted = ExpectedException.none();
    
    /**
     * 查阅次数为-1
     * @throws EkongException 易控SDK异常
     */
    @Test
    public void testNegativeLimitCount() throws EkongException {
        final EkongPolicy policy = new EkongPolicy();
        
        excepted.expect(EkongException.class);
        excepted.expectMessage("查阅次数须在0-999之间");
        policy.setLimitCount(-1);
        
    }
    
    /**
     * 查阅次数为1000
     * @throws EkongException 易控SDK异常
     */
    @Test
    public void testBigLimitCount() throws EkongException {
        final EkongPolicy policy = new EkongPolicy();
        
        excepted.expect(EkongException.class);
        excepted.expectMessage("查阅次数须在0-999之间");
        policy.setLimitCount(1000);
        
    }
    
    /**
     * 失效时间早于当前时间
     * @throws EkongException 易控SDK异常
     */
    @Test
    public void testTimeBeforeNow() throws EkongException {
        final long timestamp = System.currentTimeMillis();
        int seconds = Long.valueOf(timestamp / 1000).intValue();
        seconds--;
        
        final EkongPolicy policy = new EkongPolicy();
        
        excepted.expect(EkongException.class);
        excepted.expectMessage("失效时间须晚于当前时间");
        policy.setExpireDate(seconds);
    }
    
    /**
     * 负数
     * @throws EkongException 易控SDK异常
     */
    @Test
    public void testNegativeExpireTime() throws EkongException {
        final EkongPolicy policy = new EkongPolicy();
        
        excepted.expect(EkongException.class);
        excepted.expectMessage("时间超出范围");
        policy.setExpireDate(-1);
    }
    
    /**
     * 负数
     * @throws EkongException 易控SDK异常
     */
    @Test
    public void testNegativeDate() throws EkongException {
        final EkongPolicy policy = new EkongPolicy();
        
        final Date date = new Date(-1);
        
        excepted.expect(EkongException.class);
        excepted.expectMessage("时间超出范围");
        policy.setExpireDate(Long.valueOf(date.getTime()).intValue());
    }
    
    /**
     * 2038年问题（时间戳）
     * @throws EkongException 易控SDK异常
     */
    @Test
    public void testY2038Timestamp() throws EkongException {
        final int seconds = Integer.MAX_VALUE;
        
        final EkongPolicy policy = new EkongPolicy();
        
        excepted.expect(EkongException.class);
        excepted.expectMessage("时间超出范围");
        policy.setExpireDate(seconds);
    }
    
    /**
     * 2038年问题（日历对象）
     * @throws EkongException 易控SDK异常
     */
    @Test
    public void testY2038Calendar() throws EkongException {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Integer.MAX_VALUE);
        
        final EkongPolicy policy = new EkongPolicy();
        
        excepted.expect(EkongException.class);
        excepted.expectMessage("时间超出范围");
        policy.setExpireDate(calendar);
    }
    
    /**
     * 2038年问题（日期对象）
     * @throws EkongException 易控SDK异常
     */
    @Test
    public void testY2038Date() throws EkongException {
        final Date date = new Date();
        date.setTime(Integer.MAX_VALUE);
        
        final EkongPolicy policy = new EkongPolicy();
        
        excepted.expect(EkongException.class);
        excepted.expectMessage("时间超出范围");
        policy.setExpireDate(date);
    }
    
    /**
     * 空密码
     * @throws EkongException 易控SDK异常
     */
    @Test
    public void testEmptyPassword() throws EkongException {
        final String password = "";
        
        final EkongPolicy policy = new EkongPolicy();
        
        excepted.expect(EkongException.class);
        excepted.expectMessage("密码不能为空");
        policy.setReadPassword(password);
    }
    
    /**
     * 
     * @throws EkongException 易控SDK异常
     */
    @Test
    public void testShortPassword() throws EkongException {
        final String password = "12345";
        
        final EkongPolicy policy = new EkongPolicy();
        
        excepted.expect(EkongException.class);
        excepted.expectMessage("密码长度须为6位");
        policy.setReadPassword(password);
    }
    
    /**
     * 
     * @throws EkongException 易控SDK异常
     */
    @Test
    public void testLongPassword() throws EkongException {
        final String password = "1234567";
        
        final EkongPolicy policy = new EkongPolicy();
        
        excepted.expect(EkongException.class);
        excepted.expectMessage("密码长度须为6位");
        policy.setReadPassword(password);
    }
    
    /**
     * 异常密码
     * @throws EkongException 易控SDK异常
     */
    @Test
    public void testInvalidPassword() throws EkongException {
        final String password = "€€€€€€";
        
        final EkongPolicy policy = new EkongPolicy();
        
        excepted.expect(EkongException.class);
        excepted.expectMessage("密码须由大小写字母、数字和常用符号组成。");
        policy.setReadPassword(password);
    }
    
}
