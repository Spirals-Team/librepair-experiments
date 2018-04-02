/**  
 * Copyright © 2017 wowostar. All rights reserved.
 *
 * @Title: EkongSdkTest.java
 * @Project: com.wowostar.ekongsdk
 * @Package: com.wowostar.ekongsdk
 * @Description: 易控SDK测试用例
 * @author: ouqin
 * @date: 2017年11月14日
 * @version: V1.0
 */

package com.wowostar.ekongsdk;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsMapContaining.hasKey;

import com.wowostar.ekongsdk.common.EkongException;
import com.wowostar.ekongsdk.common.TestConstants;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

/**
 * @author: ouqin
 * @date: 2017年11月14日
 */
public class EkongSdkTest {

    /**
     * ClassLoader
     */
    private static ClassLoader classLoader;
    
    /**
     * 临时目录
     */
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();
    
    /**
     * 异常期望
     */
    @Rule
    public ExpectedException excepted = ExpectedException.none();
    
    /**
     * 获取ClassLoader
     */
    @BeforeClass
    public static void setUpBeforeClass() {
        classLoader = Thread.currentThread().getContextClassLoader();
    }
    
    /**
     * 测试AppId和AppKey构造函数
     * @throws EkongException 易控SDK异常
     */
    @Test
    public void testWithIdKey() throws EkongException {
        final EkongPolicy policy = new EkongPolicy();
        
        try {
            policy.setLimitCount(0);
            policy.setExpireDate(0);
            policy.setAutoDestroy(false);
            policy.setReadPassword("888888");
        } catch (EkongException e) {
            e.printStackTrace();
        }
            
        try {
            final EkongSdk ekongSdk = new EkongSdk(TestConstants.APPID, TestConstants.APPKEY);
            
            final File outFile = temporaryFolder.newFile();
            if (!outFile.exists()) {
                outFile.createNewFile();
            }
            
            final ByteArrayOutputStream baos = (ByteArrayOutputStream) ekongSdk.encrypt(
                    policy, 
                    new File(classLoader.getResource("test.doc").getFile()
                )
            );
            
            final FileOutputStream fos = new FileOutputStream(outFile);
            
            baos.writeTo(fos);
            baos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 测试AppId和AppKey构造函数
     * @throws EkongException 易控SDK异常
     */
    @Test
    public void testWithoutIdKey() throws EkongException {
        final EkongPolicy policy = new EkongPolicy();
        
        try {
            policy.setLimitCount(0);
            policy.setExpireDate(0);
            policy.setAutoDestroy(false);
            policy.setReadPassword("888888");
        } catch (EkongException e) {
            e.printStackTrace();
        }
            
        try {
            final EkongSdk ekongSdk = new EkongSdk();
            
            final File outFile = temporaryFolder.newFile();
            if (!outFile.exists()) {
                outFile.createNewFile();
            }
            
            final ByteArrayOutputStream baos = (ByteArrayOutputStream) ekongSdk.encrypt(
                    policy, 
                    new File(classLoader.getResource("test.doc").getFile()
                )
            );
            
            final FileOutputStream fos = new FileOutputStream(outFile);
            
            baos.writeTo(fos);
            baos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 测试获取Token
     * @throws EkongException 易控SDK异常
     */
    @Test
    public void testToken() throws EkongException {
        final EkongSdk ekongSdk = new EkongSdk(TestConstants.APPID, TestConstants.APPKEY);
        
        final Map<String, String> ret = ekongSdk.token(TestConstants.UUID);
        
        assertThat(ret, allOf(hasKey("token"), hasKey("ts")));
    }
    
}
