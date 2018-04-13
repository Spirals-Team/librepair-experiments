package com.wowostar.ekongsdk.common;

import java.util.HashMap;

/**
 * 
 * @author: ouqin
 * @date: 2017年11月7日
 */
public final class Constants {

    /**
     * 默认接口地址
     */
    
    public static final String SDK_URL = "https://api.open.ekong366.com";
    
    /**
     * 版本号
     */
    public static final String VERSION = "0.1.0";
    
    /**
     * magic头
     */
    public static final String MAGIC = "677070CC";
    
    /**
     * 连接超时时间，单位秒，默认30秒
     */
    public static final int CONNECT_TIMEOUT = 30;
    
    /**
     * 读超时时间，单位秒，默认30秒
     */
    public static final int READ_TIMEOUT = 30;
    
    /**
     * 写超时时间，单位秒，默认0，即不超时
     */
    public static final int WRITE_TIMEOUT = 0;
    
    /**
     * 连接池复用连接对象的最大空闲数量，默认32
     */
    public static final int CONNECTION_POOL_MAX_IDLE_COUNT = 32;
    
    /**
     * 连接池复用连接对象的回收周期，单位分钟，默认5分钟
     */
    public static final int CONNECTION_POOL_MAX_IDLE_MINUTES = 5;
    
    /**
     * 加密块大小，单位字节，当前为4K
     */
    public static final int ENCRYPTION_THUNK_SIZE = 4096;
    
    /**
     * 分割位
     */
    public static final int BIGINT_DIVIDER = Integer.MAX_VALUE + 1;
    
    /**
     * 头部固定空间大小
     */
    public static final int FIXED_SIZE = 24;
    
    /**
     * 配置项空间大小
     */
    public static final int OPTION_SIZE = 36;
    
    /**
     * 文件UUID空间大小
     */
    public static final int UUID_SIZE = 24;
    
    /**
     * HMAC加密秘钥
     */
    public static final String HMAC_KEY = "gpp";
    
    /**
     * AppID最大长度
     */
    public static final int APPID_MAX_LENGTH = 16;
    
    /**
     * AppKey最大长度
     */
    public static final int APPKEY_MAX_LENGTH = 16;
    
    /**
     * 密码最大长度
     */
    public static final int PASSWORD_LENGTH = 6;
    
    /**
     * Word文档 Mime types
     */
    public static final String[] DOC_MIMES = {
        "application/msword",
        "application/vnd.openxmlformats"
            + "-officedocument.wordprocessingml.document"
    };
    
    /**
     * Excel文档 Mime types
     */
    public static final String[] XLS_MIMES = {
        "application/vnd.ms-powerpoint",
        "application/vnd.openxmlformats"
            + "-officedocument.presentationml.presentation"
    };
    
    /**
     * PowerPrint文档 Mime types
     */
    public static final String[] PPT_MIMES = {
        "application/vnd.ms-excel",
        "application/vnd.openxmlformats"
            + "-officedocument.spreadsheetml.sheet"
    };
    
    /**
     * 图片类文件 Mime types
     */
    public static final String[] IMAGES_MIMES = {
        "image/bmp",
        "image/gif",
        "image/png",
        "image/jpg",
        "image/jpeg",
        "image/webp"
    };
    
    /**
     * Java 源文件 Mime types
     */
    public static final String[] JAVA_MIMES = {
        "text/x-java",
        "text/x-java-source"
    };
    
    /**
     * PHP 源文件 Mime types
     */
    public static final String[] PHP_MIMES = {
        "application/x-php"
    };
    
    /**
     * Python 源文件 Mime types
     */
    public static final String[] PYTHON_TIMES = {
        "text/x-python"
    };
    
    /**
     * 文本文件 Mime types
     */
    public static final String[] TEXT_MIMES = {
        "text/plain"
    };
    
    /**
     * XML 文件 Mime types
     */
    public static final String[] HTML_MIMES = {
        "text/html"
    };
    
    /**
     * PDF 文件 Mime types
     */
    public static final String[] PDF_MIMES = {
        "application/pdf",
        "application/x-pdf"
    };
    
    /**
     * AutoCAD 文件 Mime types
     */
    public static final String[] CAD_MIMES = {
        "application/acad"
    };
    
    /**
     * 3ds Max 文件 Mime types
     */
    public static final String[] THREEDSMAX_MIMES = {
        "application/x-3ds"
    };
    
    /**
     * Photoshop 文件 Mime types
     */
    public static final String[] PSD_MIMES = {
        "application/photoshop",
        "application/x-photoshop",
        "image/photoshop",
        "image/x-photoshop",
        "image/psd",
        "image/vnd.adobe.photoshop"
    };
    
    /**
     * illustrator 文件 Mime types
     */
    public static final String[] ILLUSTRATOR_MIMES = {
        "application/illustrator",
        "application/x-illustrator",
        "application/vnd.adobe.illustrator"
    };
    
    /**
     * CorelDraw 文件 Mime types
     */
    public static final String[] CORELDRAW_MIMES = {
        "application/coreldraw",
        "application/x-coreldraw",
        "application/cdr",
        "image/cdr"
    };
    
    /**
     * 视频文件 Mime types
     */
    public static final String[] VIDEO_MIMES = {
        "video/mp4",
        "video/3gpp",
        "video/vnd.rn-realvideo",
        "video/mpg",
        "video/avi",
        "video/flv"
    };
    
    /**
     * 音频文件 Mime types
     */
    public static final String[] AUDIO_MIMES = {
        "audio/mp3",
        "audio/mpeg",
        "audio/wav",
        "audio/x-wav",
        "audio/x-ms-wma",
        "application/x-flac"
    };
    
    /**
     * 支持的文件类型表
     */
    public static final HashMap<Integer, String[]> SUPPORTED_MIMES = 
            new HashMap<Integer, String[]>();
        
    static {
        SUPPORTED_MIMES.put(1, DOC_MIMES);
        SUPPORTED_MIMES.put(2, XLS_MIMES);
        SUPPORTED_MIMES.put(3, PPT_MIMES);
        SUPPORTED_MIMES.put(4, IMAGES_MIMES);
        SUPPORTED_MIMES.put(5, TEXT_MIMES);
        SUPPORTED_MIMES.put(6, PDF_MIMES);
        SUPPORTED_MIMES.put(7, CAD_MIMES);
        SUPPORTED_MIMES.put(8, PSD_MIMES);
        SUPPORTED_MIMES.put(9, ILLUSTRATOR_MIMES);
        SUPPORTED_MIMES.put(10, CORELDRAW_MIMES);
        SUPPORTED_MIMES.put(11, VIDEO_MIMES);
        SUPPORTED_MIMES.put(12, AUDIO_MIMES);
    }
    
}
