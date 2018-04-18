/**  
 * Copyright © 2017 wowostar. All rights reserved.
 *
 * @Title: SignatureUtils.java
 * @Project: com.wowostar.ekongsdk
 * @Package: com.wowostar.ekongsdk.utils
 * @Description: 签名工具类
 * @author: ouqin
 * @date: 2017年11月13日
 * @version: V1.0  
 */

package com.wowostar.ekongsdk.utils;

import com.wowostar.ekongsdk.common.Constants;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

/**
 * 签名工具类
 * @author: ouqin
 * @date: 2017年11月13日
 */
public final class SignatureUtils {
    
    /**
     * 使用log4j记录日志
     */
    private static final Logger logger = Logger.getLogger(SignatureUtils.class);
    
    private SignatureUtils() {
        
    }
    
    private static String calcHmacSha1(final String key, final String data) {
        final SecretKeySpec keySpec =
                new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA1");
        
        String ret = null;
        try {
            final Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(keySpec);
            
            final byte[] hmacBytes = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            
            ret = TypeUtils.bytesToHexString(hmacBytes);
        } catch (NoSuchAlgorithmException e) {
            logger.fatal(e.getMessage(), e);
        } catch (InvalidKeyException e) {
            logger.fatal(e.getMessage(), e);
        }
        return ret;
        
    }
    
    private static byte[] randomBytes(int length) {
        
        final byte[] bytes = new byte[length];
        SecureRandom sr = null;
        try {
            sr = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            logger.fatal(e.getMessage(), e);
        }
        sr.nextBytes(bytes);
        
        return bytes;
    }
    
    private static String paramReduce(final Map<String, Object> params) {
        final TreeMap<String, Object> sortedParams = new TreeMap<>(params);
        
        final ArrayList<String> emptyKeys = new ArrayList<String>();
        for (final Map.Entry<String, Object> entry: sortedParams.entrySet()) {
            if (entry.getValue().equals("")) {
                emptyKeys.add(entry.getKey());
            }
        }
        
        for (final String key : emptyKeys) {
            sortedParams.remove(key);
        }
        
        //Encoder encoder = Base64.getEncoder();
        
        final String param = sortedParams.toString()
                .substring(1, sortedParams.toString().length() - 1)
                .replace(", ", "&");
        
        //return encoder.encodeToString(param.getBytes(StandardCharsets.UTF_8));
        return Base64.encodeBase64String(param.getBytes(StandardCharsets.UTF_8));
        
    }
    
    /**
     * 生成签名
     * @param params 参数键值对
     * @return 签名字符串
     */
    public static String sign(final Map<String, Object> params,
            final String appId, final String appKey) {
        final String paramsString = paramReduce(params);
        
        final long msTime = System.currentTimeMillis();
        final Long seconds = msTime / 1000;
        
        final String nonce = TypeUtils.bytesToHexString(randomBytes(5));
        
        final StringBuffer hmacData = new StringBuffer();
        hmacData.append(appId).append(paramsString).append(seconds.toString())
            .append(nonce).append(appKey);
        
        final String sign = calcHmacSha1(Constants.HMAC_KEY, hmacData.toString());
        final StringBuffer authorize = new StringBuffer();
        authorize.append("GPP ")
            .append(appId).append(':')
            .append(seconds.toString()).append(':')
            .append(nonce).append(':')
            .append(sign);
        
        return authorize.toString();
        
    }
}
