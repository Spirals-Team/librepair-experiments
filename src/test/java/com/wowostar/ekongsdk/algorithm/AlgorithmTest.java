/**  
 * Copyright © 2017 wowostar. All rights reserved.
 *
 * @Title: RC4Test.java
 * @Project: com.wowostar.ekongsdk
 * @Package: com.wowostar.ekongsdk.algorithm
 * @Description: RC4测试类
 * @author: ouqin
 * @date: 2017年11月10日
 * @version: V1.0  
 */

package com.wowostar.ekongsdk.algorithm;

import static org.junit.Assert.assertTrue;

import com.wowostar.ekongsdk.algorithm.AlgorithmFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author: ouqin
 * @date: 2017年11月10日
 */
public class AlgorithmTest {
    
    private static ClassLoader cl;
    
    private static ByteArrayInputStream parseOutputStream(OutputStream source) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos = (ByteArrayOutputStream) source;
        
        return new ByteArrayInputStream(baos.toByteArray());
        
    }
    
    /**
     * 比较两个流是否相同
     * @param i1 输入流1
     * @param i2 输入流2
     * @return 比较结果
     * @throws IOException IO异常
     */
    private static boolean isEqual(final InputStream source1, final InputStream source2)
            throws IOException {

        if (null != source1 && null != source2) {
            final ReadableByteChannel ch1 = Channels.newChannel(source1);
            final ReadableByteChannel ch2 = Channels.newChannel(source2);

            final ByteBuffer buf1 = ByteBuffer.allocateDirect(1024);
            final ByteBuffer buf2 = ByteBuffer.allocateDirect(1024);

            try {
                while (true) {
                    final int char1 = ch1.read(buf1);
                    final int char2 = ch2.read(buf2);

                    if (char1 == -1 || char2 == -1) {
                        return char1 == char2;
                    }

                    buf1.flip();
                    buf2.flip();

                    for (int i = 0; i < Math.min(char1, char2); i++) {
                        if (buf1.get() != buf2.get()) {
                            return false;
                        }
                    }

                    buf1.compact();
                    buf2.compact();
                }

            } finally {
                source1.close();
                source2.close();
            }
        } else {
            return false;
        }
        
    }
    
    /**
     * 获取ClassLoader
     */
    @BeforeClass
    public static void setUpBeforeClass() {
        cl = Thread.currentThread().getContextClassLoader();
    }
    
    /**
     * 测试RC4
     */
    @Test
    public void testRc4() {
        final AlgorithmFactory factory = new AlgorithmFactory();
        
        byte[] inBytes;
        try {
            inBytes = FileUtils.readFileToByteArray(
                    new File(cl.getResource("chinese.txt").getFile())
            );
            
            ByteArrayOutputStream output = factory.encrypt("RC4", "888888", inBytes);
            
            FileOutputStream temp = new FileOutputStream(cl.getResource("").getFile()
                    + File.separator + "temp.txt");
            temp.write(output.toByteArray());
            temp.close();
            
            final InputStream input = new FileInputStream(cl.getResource("").getFile() 
                    + File.separator + "encrypted.txt");
            
            assertTrue(isEqual(parseOutputStream(output), input));
            
            output.close();
            input.close();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
