/**  
 * Copyright © 2017 wowostar. All rights reserved.
 *
 * @Title: EkongSdk.java
 * @Project: com.wowostar.ekongsdk
 * @Package: com.wowostar.ekongsdk
 * @Description: 易控SDK类
 * @author: ouqin
 * @date: 2017年11月13日
 * @version: V1.0  
 */

package com.wowostar.ekongsdk;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wowostar.ekongsdk.algorithm.AlgorithmFactory;
import com.wowostar.ekongsdk.common.Constants;
import com.wowostar.ekongsdk.common.EkongException;
import com.wowostar.ekongsdk.http.HttpRequest;
import com.wowostar.ekongsdk.utils.MimeUtils;
import com.wowostar.ekongsdk.utils.SignatureUtils;
import com.wowostar.ekongsdk.utils.TypeUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import okhttp3.Headers;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

/**
 * 易控SDK类
 * @author: ouqin
 * @date: 2017年11月13日
 */
public class EkongSdk {

    private String apiUrl;
    private String appId;
    private String appKey;
    
    /**
     * 通过读取配置文件设置appId和appKey
     * @throws EkongException 易控SDK异常
     * @author: ouqin
     * @date: 2017年11月20日 下午4:16:25
     */
    public EkongSdk() throws EkongException {
        Properties properties = new Properties();
        /*InputStream in;
        try {
            URL path = this.getClass().getResource(".");
            in = new BufferedInputStream(new FileInputStream(path.getPath() 
                    + File.separator
                    + "ekongsdk.properties"));
            properties.load(in);
        } catch (FileNotFoundException e) {
            throw new EkongException(e, "配置文件ekongsdk.properties不存在，请检查。");
        } catch (IOException e) {
            throw new EkongException(e, "配置文件ekongsdk.properties读取错误，请检查。");
        }*/
        
        properties = getPros("ekongsdk.properties", null, null);
        
        apiUrl = properties.getProperty("ekongsdk.api.url", Constants.SDK_URL);
        
        if (apiUrl.trim().isEmpty()) {
            throw new EkongException("配置文件ekongsdk.properties中URL为空，请检查。");
        }
        
        this.appId = properties.getProperty("ekongsdk.api.appid");
        this.appKey = properties.getProperty("ekongsdk.api.appkey");
        
        if (null == this.appId 
                || null == this.appKey
                || this.appId.trim().isEmpty()
                || this.appKey.trim().isEmpty()
            ) {
            throw new EkongException("AppID或AppKey为空，请检查。");
        }
        
    }
    
    /**
     * 通过参数设置appId和appKey
     * @param inAppId API后台生成的AppID
     * @param inAppKey API后台生成的AppKey
     * @author: ouqin
     * @date: 2017年11月13日
     */
    public EkongSdk(String inAppId, String inAppKey) throws EkongException {
        Properties properties = new Properties();
        /*InputStream in;
        try {
            URL path = this.getClass().getResource(".");
            in = new BufferedInputStream(new FileInputStream(path.getPath() 
                    + File.separator
                    + "ekongsdk.properties"));
            properties.load(in);
        } catch (FileNotFoundException e) {
            throw new EkongException(e, "配置文件ekongsdk.properties不存在，请检查。");
        } catch (IOException e) {
            throw new EkongException(e, "配置文件ekongsdk.properties读取错误，请检查。");
        }*/
        
        properties = getPros("ekongsdk.properties", null, null);
        
        apiUrl = properties.getProperty("ekongsdk.api.url", Constants.SDK_URL);
        
        if (apiUrl.trim().isEmpty()) {
            throw new EkongException("配置文件ekongsdk.properties中URL为空，请检查。");
        }
        
        this.appId = inAppId;
        this.appKey = inAppKey;
        
        if (null == this.appId 
                || null == this.appKey
                || this.appId.trim().isEmpty()
                || this.appKey.trim().isEmpty()
            ) {
            throw new EkongException("AppID或AppKey为空，请检查。");
        }
    }
    
    /**
     * 查找Properties文件
     * @param file 文件名
     * @param dir 目录名
     * @param clazz 类
     * @return 配置
     * @throws EkongException 易控SDK异常
     */
    private static Properties getPros(final String file, final String dir, final Class<?> clazz)
            throws EkongException {
        if (null == file || file.isEmpty()) {
            throw new EkongException("参数异常，请检查。");
        }
        
        String dirName;
        Class<?> classObj;
        
        if (null == dir) {
            dirName = ".";
        } else {
            dirName = dir;
        }
        
        if (null == clazz) {
            classObj = EkongSdk.class;
        } else {
            classObj = clazz;
        }
        
        final String path = dirName.concat(File.separator).concat(file);
        
        final Properties properties = new Properties();
        
        final URL url = classObj.getClassLoader().getResource(path.replace(File.separator, "/"));
        if (null != url && !url.getFile().isEmpty()) {
            File prosFile = new File(url.getFile());
            if (prosFile.exists() && prosFile.isFile()) {
                try {
                    properties.load(new BufferedInputStream(new FileInputStream(url.getFile())));
                } catch (IOException e) {
                    throw new EkongException(e);
                }
            } else {
                URL classLocation = classObj.getProtectionDomain().getCodeSource().getLocation();
                if (classLocation.toString().endsWith(".jar")) {
                    File parent = new File(classLocation.getPath()).getParentFile().getParentFile();
                    if (null != parent) {
                        prosFile = new File(parent, path);
                        if (prosFile.exists() && prosFile.isFile()) {
                            try {
                                properties.load(new BufferedInputStream(
                                        new FileInputStream(prosFile)));
                            } catch (IOException e) {
                                throw new EkongException(e);
                            }
                        }
                        
                    }
                }
            }
        }
        
        return properties;
    }
    
    /**
     * 分离整数
     * @param input 输入的整数
     * @return 分离高低位的整数
     */
    public ArrayList<Integer> splitTwoInt(final long input) {
        ArrayList<Integer> result = new ArrayList<>();
        final Integer lower = Long.valueOf(input % Constants.BIGINT_DIVIDER).intValue();
        final Integer upper = Long.valueOf((input - lower) / Constants.BIGINT_DIVIDER).intValue();
        
        result.add(lower);
        result.add(upper);
        
        return result;
    }
    
    /**
     * 加密文件
     * @param policy 加密策略
     * @param file 需加密的文件
     * @return 加密后的流
     * @throws EkongException 易控SDK异常
     */
    public OutputStream encrypt(final EkongPolicy policy, final File file) throws EkongException {
        
        if (!file.exists() || !file.isFile()) {
            throw new EkongException("文件不存在或读取异常，请检查。");
        }
        
        if (0 == file.length()) {
            throw new EkongException("文件内容为空，请检查。");
        }
        
        byte[] writeBuf = null;
        
        // 文件头
        @SuppressWarnings("unused")
        int offset = 0;
        final String magic = Constants.MAGIC;
        final byte[] magicHeader = TypeUtils.hexToString(magic);
        writeBuf = magicHeader;
        offset += 4;
        
        // 版本号
        final int version = 1;
        final byte[] versionHeader = TypeUtils.intToBytes(version);
        writeBuf = TypeUtils.joinBytes(writeBuf, versionHeader);
        offset += 4;
        
        final String mime = MimeUtils.getMime(file);
        final int fileType = MimeUtils.getFileType(mime);
        
        if (0 == fileType) {
            throw new EkongException("不支持当前文件类型");
        }
        
        final byte[] fileTypeHeader = TypeUtils.intToBytes(fileType);
        writeBuf = TypeUtils.joinBytes(writeBuf, fileTypeHeader);
        offset += 4;
        
        
        
        Map<String, Object> params = new TreeMap<String, Object>();
        //Encoder encoder = Base64.getEncoder();
        
        final byte[] secret = new byte[8];
        
        final SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(secret);
        
        final String key = TypeUtils.bytesToHexString(secret);
        
        //params.put("key", encoder.encodeToString(key.getBytes()));
        params.put("key", Base64.encodeBase64String(key.getBytes()));
        params.put("iv", "");
        params.put("type", fileType);
        
        final String fileName = file.getName();
        params.put("name", fileName);
        
        final int fileNameLength = fileName.getBytes(StandardCharsets.UTF_8).length;
        final int optionHeaderSize = Constants.OPTION_SIZE + Constants.UUID_SIZE + fileNameLength;
        final int headerSize = Constants.FIXED_SIZE + optionHeaderSize;
        
        final long fileSize = file.length();
        final long encryptSize = fileSize;
        final long totalSize = headerSize + encryptSize;
        params.put("size", totalSize);
        params.put("encryptionSize", encryptSize);
        params.put("contentSize", fileSize);
        params.put("policy", JSON.toJSON(policy));
        
        final String sign = SignatureUtils.sign(params, appId, appKey);
        final Headers.Builder builder = new Headers.Builder();
        final Headers headers = builder.add("Authorization", sign).build();
        
        final HttpRequest req = new HttpRequest();
        final String res = req.post(apiUrl + "/open/encryption/create", params, headers);
        
        final JSONObject json = JSON.parseObject(res);
        
        if (0 != Integer.valueOf(json.get("code").toString())) {
            throw new EkongException(res);
        }
        
        final ArrayList<Integer> totalSizeList = splitTwoInt(totalSize);
        final byte[] lowerHeader = TypeUtils.intToBytes(totalSizeList.get(0).intValue());
        final byte[] upperHeader = TypeUtils.intToBytes(totalSizeList.get(1).intValue());
        writeBuf = TypeUtils.joinBytes(writeBuf, lowerHeader);
        writeBuf = TypeUtils.joinBytes(writeBuf, upperHeader);
        offset += 8;
        
        final byte[] optionHeader = TypeUtils.intToBytes(optionHeaderSize);
        writeBuf = TypeUtils.joinBytes(writeBuf, optionHeader);
        offset += 4;
        
        final int uuidOffset = Constants.FIXED_SIZE + Constants.OPTION_SIZE;
        final byte[] uuidOffsetHeader = TypeUtils.intToBytes(uuidOffset);
        writeBuf = TypeUtils.joinBytes(writeBuf, uuidOffsetHeader);
        offset += 4;
        
        final byte[] uuidSizeHeader = TypeUtils.intToBytes(Constants.UUID_SIZE);
        writeBuf = TypeUtils.joinBytes(writeBuf, uuidSizeHeader);
        offset += 4;
        
        final int nameOffset = uuidOffset + Constants.UUID_SIZE;
        final byte[] nameOffsetHeader = TypeUtils.intToBytes(nameOffset);
        writeBuf = TypeUtils.joinBytes(writeBuf, nameOffsetHeader);
        offset += 4;
        
        final byte[] nameLengthHeader = TypeUtils.intToBytes(fileNameLength);
        writeBuf = TypeUtils.joinBytes(writeBuf, nameLengthHeader);
        offset += 4;
        
        final int dataOffset = nameOffset + fileNameLength;
        final byte[] dataOffsetHeader = TypeUtils.intToBytes(dataOffset);
        writeBuf = TypeUtils.joinBytes(writeBuf, dataOffsetHeader);
        offset += 4;
        
        final ArrayList<Integer> encryptSizeList = splitTwoInt(encryptSize);
        final byte[] lowerHeader2 = TypeUtils.intToBytes(encryptSizeList.get(0).intValue());
        final byte[] upperHeader2 = TypeUtils.intToBytes(encryptSizeList.get(1).intValue());
        writeBuf = TypeUtils.joinBytes(writeBuf, lowerHeader2);
        writeBuf = TypeUtils.joinBytes(writeBuf, upperHeader2);
        offset += 8;
        
        final ArrayList<Integer> fileSizeList = splitTwoInt(fileSize);
        final byte[] lowerHeader3 = TypeUtils.intToBytes(fileSizeList.get(0).intValue());
        final byte[] upperHeader3 = TypeUtils.intToBytes(fileSizeList.get(1).intValue());
        writeBuf = TypeUtils.joinBytes(writeBuf, lowerHeader3);
        writeBuf = TypeUtils.joinBytes(writeBuf, upperHeader3);
        offset += 8;
        
        final String uuid = json.getJSONObject("data").getString("uuid");
        
        final byte[] uuidHeader = uuid.getBytes(StandardCharsets.UTF_8);
        writeBuf = TypeUtils.joinBytes(writeBuf, uuidHeader);
        offset += uuidOffset;
        
        final byte[] fileNameHeader = file.getName().getBytes(StandardCharsets.UTF_8);
        writeBuf = TypeUtils.joinBytes(writeBuf, fileNameHeader);
        offset += fileNameLength;
        
        final AlgorithmFactory factory = new AlgorithmFactory();
        
        byte[] readIn;
        ByteArrayOutputStream encrypted = null;
        
        try {
            readIn = FileUtils.readFileToByteArray(file);
            encrypted = factory.encrypt("RC4", key, readIn);
        } catch (IOException e) {
            throw new EkongException(e, "文件读取错误");
        } catch (NoSuchAlgorithmException e) {
            throw new EkongException(e, "无相应算法");
        }
        
        if (null != encrypted) {
            writeBuf = TypeUtils.joinBytes(writeBuf, encrypted.toByteArray());
        }
        
        ByteArrayOutputStream out = null;
        
        try {
            out = new ByteArrayOutputStream();
            out.write(writeBuf);
        } catch (IOException e) {
            throw new EkongException(e, "流输出错误");
        }
        
        return out;
        
    }
    
    /**
     * 获取文件Token
     * @param uuid 文件UUID
     * @return Token信息
     * @throws EkongException 易控SDK异常
     */
    @SuppressWarnings("unchecked")
    public Map<String, String> token(String uuid) throws EkongException {
        Map<String, String> ret = null;
        
        Map<String, Object> params = new TreeMap<String, Object>();
        params.put("uuid", uuid);
        
        String sign = SignatureUtils.sign(params, appId, appKey);
        Headers.Builder builder = new Headers.Builder();
        Headers headers = builder.add("Authorization", sign).build();
        
        HttpRequest req = new HttpRequest();
        String res = req.post(apiUrl + "/open/token", params, headers);
        
        JSONObject json = JSON.parseObject(res);
        
        if (0 != (Integer.valueOf(String.valueOf(json.get("code"))))) {
            throw new EkongException(res);
        }
        
        JSONObject dataObj = json.getJSONObject("data");
        
        if (null != dataObj) {
            ret = JSONObject.toJavaObject(dataObj, Map.class);
        }
        
        return ret;
    }
    
    /**
     * 获取文件信息
     * @param uuid 文件UUID
     * @return 文件信息
     * @throws EkongException 易控SDK异常
     */
    @SuppressWarnings("unchecked")
    public Map<String, String> detail(String uuid) throws EkongException {
        Map<String, String> ret = null;
        
        Map<String, Object> params = new TreeMap<String, Object>();
        params.put("uuid", uuid);
        
        String sign = SignatureUtils.sign(params, appId, appKey);
        Headers.Builder builder = new Headers.Builder();
        Headers headers = builder.add("Authorization", sign).build();
        
        HttpRequest req = new HttpRequest();
        String res = req.get(apiUrl + "/open/encryption/detail", params, headers);
        
        JSONObject json = JSON.parseObject(res);
        
        if (0 != (Integer.valueOf(json.get("code").toString()))) {
            throw new EkongException(res);
        }
        
        JSONObject dataObj = json.getJSONObject("data");
        
        if (null != dataObj) {
            ret = JSONObject.toJavaObject(dataObj, Map.class);
        }
        
        return ret;
    }
    
    /**
     * 设置文件策略
     * @param uuid 文件UUID
     * @param policy 策略
     * @return 设置结果
     * @throws EkongException 易控SDK异常
     */
    @SuppressWarnings("unchecked")
    public Map<String, String> setPolicy(String uuid, EkongPolicy policy) throws EkongException {
        Map<String, String> ret = null;
        
        Map<String, Object> params = new TreeMap<String, Object>();
        params.put("uuid", uuid);
        params.put("policy", JSON.toJSON(policy));
        
        String sign = SignatureUtils.sign(params, appId, appKey);
        Headers.Builder builder = new Headers.Builder();
        Headers headers = builder.add("Authorization", sign).build();
        
        HttpRequest req = new HttpRequest();
        String res = req.post(apiUrl + "/open/encryption/save", params, headers);
        
        JSONObject json = JSON.parseObject(res);
        
        if (0 != (Integer.valueOf(json.get("code").toString()))) {
            throw new EkongException(res);
        }
        
        JSONObject dataObj = json.getJSONObject("data");
        
        if (null != dataObj) {
            ret = JSONObject.toJavaObject(dataObj, Map.class);
        }
        
        return ret;
    }
    
}
