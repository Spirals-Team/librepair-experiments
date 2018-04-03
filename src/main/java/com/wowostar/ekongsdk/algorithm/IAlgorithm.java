/**  
 * Copyright © 2017 wowostar. All rights reserved.
 *
 * @Title: IAlgorithm.java
 * @Project: com.wowostar.ekongsdk
 * @Package: com.wowostar.ekongsdk.algorithm
 * @Description: 加密算法接口
 * @author: ouqin
 * @date: 2017年11月10日
 * @version: V1.0  
 */

package com.wowostar.ekongsdk.algorithm;

import java.io.ByteArrayOutputStream;
import java.security.NoSuchAlgorithmException;

/**
 * 加密接口
 * @author: ouqin
 * @date: 2017年11月10日
 */
public interface IAlgorithm {
    
    /**
     * 加密接口
     * @param key 秘钥
     * @param bytes 输入字节流
     * @return 输出字节流
     * @throws NoSuchAlgorithmException 无相应算法异常
     */
    public abstract ByteArrayOutputStream encrypt(final String key,
            final byte[] bytes) throws NoSuchAlgorithmException;
}
