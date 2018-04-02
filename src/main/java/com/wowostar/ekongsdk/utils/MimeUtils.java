/**  
 * Copyright © 2017 wowostar. All rights reserved.
 *
 * @Title: Mime.java
 * @Project: com.wowostar.ekongsdk-0.0.1-SNAPSHOT
 * @Package: com.wowostar.ekongsdk.common
 * @Description: 通过MIME获取文件类型
 * @author: ouqin
 * @date: 2017年11月8日
 * @version: V1.0  
 */

package com.wowostar.ekongsdk.utils;

import com.wowostar.ekongsdk.common.Constants;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

/**
 * Mime type 检测工具类
 * @author: ouqin
 * @date: 2017年11月8日
 */
public class MimeUtils {

    /**
     * 拼接支持的类型
     */
    private static final String[] combined = TypeUtils.concat(Constants.DOC_MIMES, 
        Constants.XLS_MIMES,
        Constants.PPT_MIMES,
        Constants.IMAGES_MIMES,
        Constants.TEXT_MIMES,
        Constants.HTML_MIMES,
        Constants.PDF_MIMES,
        Constants.CAD_MIMES,
        Constants.PSD_MIMES,
        Constants.ILLUSTRATOR_MIMES,
        Constants.CORELDRAW_MIMES,
        Constants.VIDEO_MIMES,
        Constants.AUDIO_MIMES
    );
    
    /**
     * 预定义格式表
     * @param ext 扩展名
     * @return MIME字符串
     */
    public static final String[] getMimesByExt(final String ext) {
        switch (ext) {
        
            // 图像图形类
            case "ai":
                return Constants.ILLUSTRATOR_MIMES;
            case "cdr":
                return Constants.CORELDRAW_MIMES;
            case "dwg":
                return Constants.CAD_MIMES;
            case "3ds":
                return Constants.THREEDSMAX_MIMES;
            case "psd":
                return Constants.PSD_MIMES;
            // 源代码类
            case "htm":
            case "html":
                return Constants.HTML_MIMES;
            case "java":
                return Constants.JAVA_MIMES;
            case "php":
                return Constants.PHP_MIMES;
            case "py":
                return Constants.PYTHON_TIMES;
            default:
                return null;
        }
    }
    
    /**
    * 通过文件识别MIME
    * @param file FILE 输入文件
    * @return String 结果
    */
    public static String getMime(final File file) {
        Logger logger = Logger.getLogger(MimeUtils.class);
        
        String mimeString = "application/octet-stream";
        
        try {
            String detectedMime = Files.probeContentType(file.toPath());
            
            if (null != detectedMime && !detectedMime.isEmpty()) {
                if (Arrays.asList(combined).contains(detectedMime)) {
                    mimeString = detectedMime;
                }
                
            } else {
                String definedMime = getMimesByExt(FilenameUtils.getExtension(file.getName()))[0];
                if (null != definedMime && !definedMime.isEmpty()) {
                    mimeString = definedMime;
                }
            }
        } catch (IOException e) {
            logger.fatal(e.getMessage(), e);
        }
        
        return mimeString;
    }
    
    /**
     * 通过MIME判断字典表中的序号
     * @param mime 要判断的MIME
     * @return 结果序号
     */
    public static int getFileType(final String mime) {
        final Iterator<Map.Entry<Integer, String[]>> iter =
                Constants.SUPPORTED_MIMES.entrySet().iterator();
        while (iter.hasNext()) {
            final Map.Entry<Integer, String[]> entry = iter.next();
            
            final String[] value = entry.getValue();
            
            if (null != value) {
                if (Arrays.asList(value).contains(mime)) {
                    return entry.getKey();
                }
            }
            
        }
        return 0;
        
    }

}
