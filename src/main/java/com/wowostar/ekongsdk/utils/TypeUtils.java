/**  
 * Copyright © 2017 wowostar. All rights reserved.
 *
 * @Title: TypeUtils.java
 * @Project: com.wowostar.ekongsdk-0.0.1-SNAPSHOT
 * @Package: com.wowostar.ekongsdk.utils
 * @Description: 类型转换工具类
 * @author: ouqin
 * @date: 2017年11月8日
 * @version: V1.0  
 */

package com.wowostar.ekongsdk.utils;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Formatter;

/**
 * 类型工具类
 * @author: ouqin
 * @date: 2017年11月8日
 */
public final class TypeUtils {
    
    private TypeUtils() {
        
    }
    
    /**
     * 十六进制转字节数组
     * @param hex 十六进制字符串
     * @return 字节数组
     */
    public static byte[] hexToString(final String hex) {
        byte[] output = new byte[(hex.length() + 1) / 2];
        for (int i = hex.length() - 1; i >= 0; i -= 2) {
            int from = i - 1;
            if (from < 0) {
                from = 0;
            }
            String str = hex.substring(from, i + 1);
            output[i / 2] = (byte)Integer.parseInt(str, 16);
        }
        return output;
    }
    
    /**
     * 整数转字节数组
     * @param a 输入整数
     * @return 字节数组
     */
    public static byte[] intToBytes(int a) {
        byte[] ret = new byte[4];
        ret[0] = (byte) (a & 0xFF);   
        ret[1] = (byte) ((a >> 8) & 0xFF);   
        ret[2] = (byte) ((a >> 16) & 0xFF);   
        ret[3] = (byte) ((a >> 24) & 0xFF);
        return ret;
    }
    
    /**
     * 字符数组转换字节数组
     * @param chars 输入字符数组
     * @return 字节数组
     */
    public static byte[] getBytes(final char[] chars) {
        CharBuffer cb = CharBuffer.allocate(chars.length);
        cb.put(chars);
        cb.flip();
        final ByteBuffer bb = StandardCharsets.UTF_8.encode(cb);
        
        final byte[] bytes = bb.array();
       
        return bytes;
    }
    
    /**
     * 拼接两个字节数组
     * @param array1 源数组1
     * @param array2 源数组2
     * @return 新字节数组
     */
    public static byte[] joinBytes(final byte[] array1, final byte[] array2) {
        final byte[] ret = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, ret, 0, array1.length);
        System.arraycopy(array2, 0, ret, array1.length, array2.length);
        return ret;
    }
    
    /**
     * 字符串转字节流，忽略高位
     * @param input 输入字节
     * @return 字节数组
     */
    public static byte[] stringToBytes(final String input) {
        byte[] ret = new byte[input.length()];
        for (int i = input.length() - 1; i >= 0; i--) {
            ret[i] = (byte)input.charAt(i);
        }
        return ret;
    }
    
    /**
     * 字节数组转16进制字符串
     * @param bytes 输入字节数组
     * @return 16进制字符串
     */
    public static String bytesToHexString(final byte[] bytes) {
        Formatter formatter = new Formatter();
        
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
        
        String ret = formatter.toString();
        
        formatter.close();

        return ret;
    }
    
    /**
     * 将数组拼接成一个
     * @param first 首项
     * @param rest 剩余项
     * @return 拼接后的数组
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] concat(T[] first, T[]... rest) {
        T[] ret = null;
        
        int totalLen = first.length;
        
        if (null == rest) {
            ret = first;
        } else {
            for (T[] array : rest) {
                totalLen += array.length;
            }
            
            ret = Arrays.copyOf(first, totalLen);
            int offset = first.length;
            
            for (final T[] array : rest) {
                System.arraycopy(array, 0, ret, offset, array.length);
                offset += array.length;
            }
        }
        
        return ret;
    }

}
