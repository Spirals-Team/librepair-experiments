/**  
 * Copyright © 2017 wowostar. All rights reserved.
 *
 * @Title: HttpRequestTest.java
 * @Project: com.wowostar.ekongsdk
 * @Package: com.wowostar.ekongsdk.http
 * @Description: HTTP客户端测试
 * @author: ouqin
 * @date: 2017年11月7日
 * @version: V1.0  
 */

package com.wowostar.ekongsdk.http;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasKey;

import static org.junit.Assert.assertNotNull;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wowostar.ekongsdk.common.EkongException;

import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

/**
 * @author: ouqin
 * @date: 2017年11月7日
 */
public final class HttpRequestTest {
    
    /**
     * 用httpbin.org进行请求头测试
     */
    private static final String HTTPBIN_ORG_URL = "https://httpbin.org";
    
    /**
     * 用letsencrypt.org进行证书兼容性测试
     */
    private static final String LETSENCRYPT_URL = "https://helloworld.letsencrypt.org";
    
    /**
     * 测试用参数
     */
    private static final Map<String, Object> params = new TreeMap<String, Object>() {
        
        /**
         * @fieldName: serialVersionUID
         * @fieldType: long
         * @author: ouqin
         * @date: 2017年11月20日
         */
        private static final long serialVersionUID = 3366828381412264654L;

        {
            put("key1", "value1");
            put("键二", "值二");
        }
        
    };
    
    
    /**
     * 测试包含QueryString的GET
     * @throws EkongException 易控SDK异常
     */
    @Test
    public void testGetWithParams() throws EkongException {
        final HttpRequest request = new HttpRequest();
        
        final String ret = request.get(HTTPBIN_ORG_URL + "/get", params, null);
        
        final JSONObject jsonObj = JSON.parseObject(ret);
        
        assertNotNull(ret);
        assertThat(jsonObj, allOf(
                hasKey("args"),
                hasKey("headers")
            )
        );
        
        final JSONObject argsObj = jsonObj.getJSONObject("args");
        final Map<String, String> argsMap = JSON.toJavaObject(argsObj, Map.class);
        
        assertNotNull(argsMap);
        assertThat(argsMap, allOf(hasEntry("key1", "value1"), hasEntry("键二", "值二")));
        
    }
    
    /**
     * 测试包含FormData的Post
     * @throws EkongException 易控SDK异常
     */
    @Test
    public void testPostWithParams() {
        final HttpRequest request = new HttpRequest();
        
        final String ret = request.post(HTTPBIN_ORG_URL + "/post", params, null);
        
        final JSONObject jsonObj = JSON.parseObject(ret);
        
        assertNotNull(ret);
        assertThat(jsonObj, allOf(
                hasKey("args"),
                hasKey("headers")
            )
        );
        
        final JSONObject formObj = jsonObj.getJSONObject("form");
        final Map<String, String> formMap = JSON.toJavaObject(formObj, Map.class);
        
        assertNotNull(formMap);
        assertThat(formMap, allOf(hasEntry("key1", "value1"), hasEntry("键二", "值二")));
    }
    
    /**
     * 通过LetsEncrypt站点判断证书是否可信。
     */
    @Test
    public void testLetsEncrypt() {
        final HttpRequest request = new HttpRequest();
        
        final String ret = request.get(LETSENCRYPT_URL, null, null);
        
        assertNotNull(ret);
    }
}
