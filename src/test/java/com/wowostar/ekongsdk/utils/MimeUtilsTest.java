/**  
 * Copyright © 2017 wowostar. All rights reserved.
 *
 * @Title: MimeTest.java
 * @Project: com.wowostar.ekongsdk-0.0.1-SNAPSHOT
 * @Package: com.wowostar.ekongsdk.utils
 * @Description: MIME测试类
 * @author: ouqin
 * @date: 2017年11月8日
 * @version: V1.0  
 */

package com.wowostar.ekongsdk.utils;

import static org.junit.Assert.assertEquals;

import com.wowostar.ekongsdk.utils.MimeUtils;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * @author: ouqin
 * @date: 2017年11月8日
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MimeUtilsTest {
    
    private static ClassLoader classLoader;
    
    /**
     * 获取ClassLoader
     */
    @BeforeClass
    public static void setUpBeforeClass() {
        classLoader = Thread.currentThread().getContextClassLoader();
    }
    
    /**
     * 测试bmp文件
     */
    @Test
    public void testBmp() {
        String mime = MimeUtils.getMime(new File(classLoader.getResource("test.bmp").getFile()));
        int fileType = MimeUtils.getFileType(mime);
        
        assertEquals("image/bmp", mime);
        assertEquals(4, fileType);
    }
    
    /**
     * 测试doc文件
     */
    @Test
    public void testDoc() {
        String mime = MimeUtils.getMime(new File(classLoader.getResource("test.doc").getFile()));
        int fileType = MimeUtils.getFileType(mime);
        
        assertEquals("application/msword", mime);
        assertEquals(1, fileType);
    }
    
    /**
     * 测试docx文件
     */
    @Test
    public void testDocx() {
        String mime = MimeUtils.getMime(new File(classLoader.getResource("test.docx").getFile()));
        int fileType = MimeUtils.getFileType(mime);
        
        StringBuilder sb = new StringBuilder();
        sb.append("application/vnd.openxmlformats")
            .append("-officedocument.wordprocessingml.document");
        
        assertEquals(sb.toString(), mime);
        assertEquals(1, fileType);
    }
    
    /**
     * 测试gif文件
     */
    @Test
    public void testGif() {
        String mime = MimeUtils.getMime(new File(classLoader.getResource("test.gif").getFile()));
        int fileType = MimeUtils.getFileType(mime);
        
        assertEquals("image/gif", mime);
        assertEquals(4, fileType);
    }
    
    /**
     * 测试htm文件
     */
    @Test
    public void testHtm() {
        String mime = MimeUtils.getMime(new File(classLoader.getResource("test.htm").getFile()));
        
        assertEquals("text/html", mime);
    }
    
    /**
     * 测试html文件
     */
    @Test
    public void testHtml() {
        String result = MimeUtils.getMime(new File(classLoader.getResource("test.html").getFile()));
        
        assertEquals("text/html", result);
    }
    
    /**
     * 测试jpg文件
     */
    @Test
    public void testJpg() {
        String mime = MimeUtils.getMime(new File(classLoader.getResource("test.jpg").getFile()));
        int fileType = MimeUtils.getFileType(mime);

        assertEquals("image/jpeg", mime);
        assertEquals(4, fileType);
    }
    
    /**
     * 测试MP3文件
     */
    @Test
    public void testMp3() {
        String mime = MimeUtils.getMime(new File(classLoader.getResource("test.mp3").getFile()));
        int fileType = MimeUtils.getFileType(mime);
        
        assertEquals(12, fileType);
    }
    
    /**
     * 测试MP4文件
     */
    @Test
    public void testMp4() {
        String mime = MimeUtils.getMime(new File(classLoader.getResource("test.mp4").getFile()));
        int fileType = MimeUtils.getFileType(mime);
        
        assertEquals(11, fileType);
    }
    
    /**
     * 测试pdf文件
     */
    @Test
    public void testPdf() {
        String mime = MimeUtils.getMime(new File(classLoader.getResource("test.pdf").getFile()));
        int fileType = MimeUtils.getFileType(mime);
        
        assertEquals(6, fileType);
    }
    
    /**
     * 测试png文件
     */
    @Test
    public void testPng() {
        String mime = MimeUtils.getMime(new File(classLoader.getResource("test.png").getFile()));
        int fileType = MimeUtils.getFileType(mime);
        
        assertEquals("image/png", mime);
        assertEquals(4, fileType);
    }
    
    /**
     * 测试ppt文件
     */
    @Test
    public void testPpt() {
        String mime = MimeUtils.getMime(new File(classLoader.getResource("test.ppt").getFile()));
        int fileType = MimeUtils.getFileType(mime);
        
        assertEquals("application/vnd.ms-powerpoint", mime);
        assertEquals(2, fileType);
    }
    
    /**
     * 测试pptx文件
     */
    @Test
    public void testPPtx() {
        String mime = MimeUtils.getMime(new File(classLoader.getResource("test.pptx").getFile()));
        int fileType = MimeUtils.getFileType(mime);
        
        StringBuilder sb = new StringBuilder();
        sb.append("application/vnd.openxmlformats")
            .append("-officedocument.presentationml.presentation");
        
        assertEquals(sb.toString(), mime);
        assertEquals(2, fileType);
    }
    
    /**
     * 测试psd文件
     */
    @Test
    public void testPsd() {
        String mime = MimeUtils.getMime(new File(classLoader.getResource("test.psd").getFile()));
        int fileType = MimeUtils.getFileType(mime);
        
        assertEquals(8, fileType);
    }
    
    /**
     * 测试txt文件
     */
    @Test
    public void testTxt() {
        String result = MimeUtils.getMime(new File(classLoader.getResource("test.txt").getFile()));
        
        assertEquals("text/plain", result);
    }
    
    /**
     * 测试wav文件
     */
    @Test
    public void testWav() {
        String mime = MimeUtils.getMime(new File(classLoader.getResource("test.wav").getFile()));
        int fileType = MimeUtils.getFileType(mime);
        
        assertEquals(12, fileType);
    }
    
    /**
     * 测试wma文件
     */
    @Test
    public void testWma() {
        String mime = MimeUtils.getMime(new File(classLoader.getResource("test.wma").getFile()));
        int fileType = MimeUtils.getFileType(mime);
        
        assertEquals(12, fileType);
    }
    
    /**
     * 测试xls文件
     */
    @Test
    public void testXls() {
        String mime = MimeUtils.getMime(new File(classLoader.getResource("test.xls").getFile()));
        int fileType = MimeUtils.getFileType(mime);
        
        assertEquals("application/vnd.ms-excel", mime);
        assertEquals(3, fileType);
    }
    
    /**
     * 测试xlsx文件
     */
    @Test
    public void testXlsx() {
        String mime = MimeUtils.getMime(new File(classLoader.getResource("test.xlsx").getFile()));
        int fileType = MimeUtils.getFileType(mime);
        
        StringBuilder sb = new StringBuilder();
        sb.append("application/vnd.openxmlformats")
            .append("-officedocument.spreadsheetml.sheet");
        
        assertEquals(sb.toString(), mime);
        assertEquals(3, fileType);
    }
    
}
