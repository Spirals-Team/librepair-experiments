/**  
 * Copyright © 2017 wowostar. All rights reserved.
 *
 * @Title: FilesFilter.java
 * @Project: com.wowostar.ekongsdk
 * @Package: com.wowostar.ekongsdk.utils
 * @Description: 过滤证书文件
 * @author: ouqin
 * @date: 2017年11月21日
 * @version: V1.0
 */

package com.wowostar.ekongsdk.utils;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

/**
 * 文件过滤工具类
 * @author: ouqin
 * @date: 2017年11月21日
 */
public final class FilesFilter {
    
    private FilesFilter() {
        
    }
    
    /**
     * x509证书过滤器
     */
    public static FileFilter x509FileFilter = new FileFilter() {
        
        /**
         * 判断是否匹配
         * @param pathname 输入路径
         * @return 是否匹配
         * @see java.io.FileFilter#accept(java.io.File)
         */
        @Override
        public boolean accept(final File pathname) {
            
            if (pathname.isDirectory()) {
                return true;
            }
            
            final String fileName = pathname.getName();
            
            return fileName.matches("(?i).+crt$");
        }
    };
    
    /**
     * 过滤需要的文件
     * @param target 目标目录
     * @param filter 过滤规则
     * @param filtered 递归的列表
     * @return 过滤后的列表
     */
    public static List<File> filterCertFiles(final File target,
            final FileFilter filter, final List<File> filtered) {
        final File[] files = target.listFiles(filter);
        
        if (null != files) {
            for (final File file: files) {
                if (file.isDirectory()) {
                    filterCertFiles(file, filter, filtered);
                } else {
                    filtered.add(file);
                }
            }
        }
        
        return filtered;
    }
}
