/**  
 * Copyright © 2017 wowostar. All rights reserved.
 *
 * @Title: AlgorithmFactory.java
 * @Project: com.wowostar.ekongsdk
 * @Package: com.wowostar.ekongsdk.algorithm
 * @Description: 算法工厂类
 * @author: ouqin
 * @date: 2017年11月10日
 * @version: V1.0  
 */

package com.wowostar.ekongsdk.algorithm;

import java.io.ByteArrayOutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

/**
 * 加密工厂类
 * @author: ouqin
 * @date: 2017年11月10日
 */
public class AlgorithmFactory {
    /**
     * 加密
     * @param algorithm 算法名称（RC4）
     * @param key 秘钥
     * @param bytes 输入字节流
     * @return 输出字节流
     * @throws NoSuchAlgorithmException 无相应加密算法异常
     */
    public ByteArrayOutputStream encrypt(final String algorithm,
            final String key, final byte[] bytes)
            throws NoSuchAlgorithmException {
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        
        switch (algorithm.toLowerCase(Locale.CHINESE)) {
            case "aes-128-gcm":
                break;
            case "rc4":
                final RC4 rc4 = new RC4();
                out = rc4.encrypt(key, bytes);
                break;

            default:
                throw new NoSuchAlgorithmException("未找到相应的加密算法");
        }
        
        return out;
    }
}
