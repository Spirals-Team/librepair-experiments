/**  
 * Copyright © 2017 wowostar. All rights reserved.
 *
 * @Title: RC4.java
 * @Project: com.wowostar.ekongsdk
 * @Package: com.wowostar.ekongsdk.algorithm
 * @Description: 自定义RC4加密算法
 * @author: ouqin
 * @date: 2017年11月10日
 * @version: V1.0  
 */

package com.wowostar.ekongsdk.algorithm;

import com.wowostar.ekongsdk.common.Constants;
import com.wowostar.ekongsdk.common.EkongException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

/**
 * RC4加密类
 * @author: ouqin
 * @date: 2017年11月10日
 */
public class RC4 implements IAlgorithm {
    
    /**
     * 预定义表
     */
    private final List<Byte> state;
    
    /**
     * 通过log4j记录日志
     */
    private static final Logger logger = Logger.getLogger(RC4.class);
    
    /**
     * 在构造函数中初始化
     * 
     * @author: ouqin
     * @date: 2017年11月10日
     */
    public RC4() {
        state = new ArrayList<Byte>();
        
        for (int i = 0; i < 256; i++) {
            state.add((byte) i);
        }
    }
    
    /**
     * 加密方法
     * @param key 秘钥
     * @param bytes 输入字节数组
     * @return 输出字节流
     * @throws NoSuchAlgorithmException 无相关算法异常
     */
    
    @Override
    public ByteArrayOutputStream encrypt(final String key, final byte[] bytes)
            throws NoSuchAlgorithmException {
        
        byte[] buf = new byte[Constants.ENCRYPTION_THUNK_SIZE];
        
        final ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
        final ByteArrayOutputStream out =
                new ByteArrayOutputStream(Constants.ENCRYPTION_THUNK_SIZE);
        
        int readSize = 0;
        
        while (0 < (readSize = stream.read(buf, 0, Constants.ENCRYPTION_THUNK_SIZE))) {
            try {
                buf = rc4(key, buf);
                out.write(buf, 0, readSize);
                //buf = new byte[Constants.ENCRYPTION_THUNK_SIZE];
            } catch (EkongException e) {
                logger.fatal(e.getMessage(), e);
            }
            
        }
        
        return out;
    }
    
    private static byte[] rc4(String key, byte[] data) throws EkongException {
        byte[] out = null;
        
        try {
            final Cipher cipher = Cipher.getInstance("RC4");
            
            final int maxKeyLength = Cipher.getMaxAllowedKeyLength("RC4");
            
            if (maxKeyLength < key.getBytes().length) {
                throw new EkongException("秘钥过长，请检查。");
            }
            final SecretKeySpec keySpec = 
                    new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "RC4");
            
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            
            out = cipher.update(data);
        } catch (NoSuchAlgorithmException e) {
            logger.fatal(e.getMessage(), e);
        } catch (NoSuchPaddingException e) {
            logger.fatal(e.getMessage(), e);
        } catch (InvalidKeyException e) {
            logger.fatal(e.getMessage(), e);
        }
        
        return out;
    }
    
    /*private byte[] oldRC4(String keyString, byte[] dataStream) {
        
        ArrayList<Byte> key = new ArrayList<>();
        ArrayList<Byte> data = new ArrayList<>();
        
        if (1 > keyString.length() || 256 < keyString.length()) {
            throw new IllegalArgumentException("秘钥长度需在1-256范围");
        }
        
        for (int i = 0; i < keyString.length(); i++) {
            key.add((byte) keyString.charAt(i));
        }
        for (int i = 0; i < dataStream.length; i++) {
            if (0 > (int) dataStream[i]) {
                data.add((byte) (dataStream[i] & 0xff));
            } else {
                data.add((byte) (dataStream[i]));
            }
            
        }
        
        int len = key.size();
        int index1 = 0;
        int index2 = 0;
        byte tmp;
        
        for (int counter = 0; counter < 256; counter++) {
            index2 = ((key.get(index1) + state.get(counter) + index2) & 0xff) % 256;
            tmp = state.get(counter);
            state.set(counter, state.get(index2));
            state.set(index2, tmp);
            index1 = (index1 + 1) % len;
        }
        
        len = data.size();
        int x = 0;
        int y = 0;
        int k;
        int l;
        for (int counter = 0; counter < len; counter++) {
            x = ((x + 1) & 0xff) % 256;
            y = ((state.get(x) + y) & 0xff) % 256;
            tmp = state.get(x);
            state.set(x, state.get(y));
            state.set(y, tmp);
            l = ((state.get(x) + state.get(y)) & 0xff) % 256;
            k = state.get(l);
            
            data.set(counter, (byte) (data.get(counter) ^ k));
        }
        
        char[] dataChars = null;
        byte[] dataBytes = null;
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        
        for (int i = 0; i < len; i++) {
            out.write(data.get(i).intValue());
        }
        return out.toByteArray();
        
    }*/
    
}
