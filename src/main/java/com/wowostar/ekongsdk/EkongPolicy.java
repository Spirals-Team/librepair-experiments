/**  
 * Copyright © 2017 wowostar. All rights reserved.
 *
 * @Title: EkongPolicy.java
 * @Project: com.wowostar.ekongsdk
 * @Package: com.wowostar.ekongsdk
 * @Description: 易控策略类
 * @author: ouqin
 * @date: 2017年11月13日
 * @version: V1.0  
 */

package com.wowostar.ekongsdk;

import com.wowostar.ekongsdk.common.Constants;
import com.wowostar.ekongsdk.common.EkongException;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 易控策略类
 * @author: ouqin
 * @date: 2017年11月13日
 */
public class EkongPolicy {

    
    /**
     * 可查阅次数
     */
    private int limitCount = 0;
    
    /**
     * 失效时间（时间戳）
     */
    private int expireDate = 0;
    
    /**
     * 是否自动销毁
     */
    private boolean autoDestroy = false;
    
    /**
     * 查阅密码
     */
    private String readPassword = "";
    
    /**
     * 密码校验正则表达式
     */
    private static Pattern pattern = Pattern.compile("[\\u0020-\\u007e]{6}");
    
    /**
     * @return the autoDestroy
     */
    public boolean isAutoDestroy() {
        return autoDestroy;
    }

    /**
     * 是否自动销毁
     * @param autoDestroy the autoDestroy to set
     * @throws EkongException 易控SDK异常
     */
    public void setAutoDestroy(boolean autoDestroy) throws EkongException {
        this.autoDestroy = autoDestroy;
    }

    /**
     * @return the limitCount
     */
    public int getLimitCount() {
        return limitCount;
    }
    
    /**
     * 设置可查阅次数
     * @param limitCount 可查阅次数，范围1-999，0为无限次。
     * @throws EkongException 易控SDK异常
     */
    public void setLimitCount(int limitCount) throws EkongException {
        if (0 > limitCount || 999 < limitCount) {
            throw new EkongException("查阅次数须在0-999之间");
        }
        
        this.limitCount = limitCount;
    }

    /**
     * @return the expireDate
     */
    public int getExpireDate() {
        return expireDate;
    }
    
    /**
     * 设置失效时间
     * @param timestamp 时间戳
     * @throws EkongException 易控SDK异常
     */
    public void setExpireDate(int timestamp) throws EkongException {
        long msTime = System.currentTimeMillis();
        long seconds = msTime / 1000;
        
        if (0 != timestamp) {
            if (Integer.MAX_VALUE <= timestamp || 0 > timestamp) {
                throw new EkongException("时间超出范围");
            }
            
            if (seconds > timestamp) {
                throw new EkongException("失效时间须晚于当前时间");
            }
            
        }
        
        this.expireDate = timestamp;
        
    }
    
    /**
     * 设置失效时间
     * @param calendar 日历对象
     * @throws EkongException 易控SDK异常
     */
    public void setExpireDate(Calendar calendar) throws EkongException {
        long msTime = System.currentTimeMillis();
        long seconds = msTime / 1000;
        
        long timestamp = calendar.getTimeInMillis();
        int tsSeconds = Long.valueOf(timestamp).intValue();
        
        if (Integer.MAX_VALUE <= tsSeconds || 0 > tsSeconds) {
            throw new EkongException("时间超出范围");
        }
        
        if (seconds > tsSeconds) {
            throw new EkongException("失效时间须晚于当前时间");
        }
        
        this.expireDate = tsSeconds;
    }
    
    /**
     * 设置失效时间
     * @param date 日期对象
     * @throws EkongException 易控SDK异常
     */
    public void setExpireDate(Date date) throws EkongException {
        long msTime = System.currentTimeMillis();
        long seconds = msTime / 1000;
        
        long timestamp = date.getTime();
        int tsSeconds = Long.valueOf(timestamp).intValue();
        
        if (Integer.MAX_VALUE <= tsSeconds || 0 > tsSeconds) {
            throw new EkongException("时间超出范围");
        }
        
        if (seconds > tsSeconds) {
            throw new EkongException("失效时间须晚于当前时间");
        }
        
        this.expireDate = tsSeconds;
    }

    /**
     * @return the readPassword
     */
    public String getReadPassword() {
        return readPassword;
    }
    
    /**
     * 设置查阅密码
     * @param password 查阅密码，须为6位，由大小写字母、数字和常用符号组成。
     * @throws EkongException 易控SDK异常
     */
    public void setReadPassword(String password) throws EkongException {
        if (password.isEmpty()) {
            throw new EkongException("密码不能为空");
        }
        
        if (Constants.PASSWORD_LENGTH != password.length()) {
            throw new EkongException("密码长度须为6位");
        }
        
        Matcher matcher = pattern.matcher(password);
        
        if (!matcher.find()) {
            throw new EkongException("密码须由大小写字母、数字和常用符号组成。");
        }
        
        this.readPassword = password;
        
    }
    
}
