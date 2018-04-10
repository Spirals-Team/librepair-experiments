/**  
 * Copyright © 2017 wowostar. All rights reserved.
 *
 * @Title: HttpRequest.java
 * @Project: com.wowostar.ekongsdk-0.0.1-SNAPSHOT
 * @Package: com.wowostar.ekongsdk.http
 * @Description: 通过HTTP请求接口
 * @author: ouqin
 * @date: 2017年11月9日
 * @version: V1.0  
 */

package com.wowostar.ekongsdk.http;

import com.wowostar.ekongsdk.utils.FilesFilter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.apache.log4j.Logger;

/**
 * Http请求封装
 * @author: ouqin
 * @date: 2017年11月9日
 */
public class HttpRequest {
    
    /**
     * 使用log4j记录日志
     */
    private static final Logger logger = Logger.getLogger(HttpRequest.class);
    
    private OkHttpClient client;
    private OkHttpClient.Builder clientBuilder;
    
    /**
     * 信任自签证书
     * 
     * @author: ouqin
     * @date: 2017年11月21日
     */
    public HttpRequest() {
        final X509TrustManager trustAllManager = 
                new X509TrustManager() {

                @Override
                public void checkClientTrusted(final X509Certificate[] chain, final String authType)
                        throws CertificateException {
                    // TODO Auto-generated method stub
                    
                }

                @Override
                public void checkServerTrusted(final X509Certificate[] chain, final String authType)
                        throws CertificateException {
                    // TODO Auto-generated method stub
                    
                }

                /**
                 * 从resources中获取证书
                 * @return 信任的证书列表
                 * @see javax.net.ssl.X509TrustManager#getAcceptedIssuers()
                 */
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    //添加自定义证书（包括LetsEncrypt证书）
                    
                    final String path = Thread.currentThread().getContextClassLoader()
                            .getResource(".").getFile();
                    
                    List<File> x509Files = new ArrayList<File>();
                    
                    FilesFilter.filterCertFiles(new File(path),
                            FilesFilter.x509FileFilter, x509Files);
                    
                    X509Certificate[] certificates = new X509Certificate[x509Files.size()];
                    
                    int index = 0;
                    for (File file : x509Files) {
                        try {
                            InputStream source = new FileInputStream(file);
                            
                            CertificateFactory cf = CertificateFactory.getInstance("X509");
                            X509Certificate certificate =
                                    (X509Certificate) cf.generateCertificate(source);
                            certificates[index] = certificate;
                            source.close();
                            
                        } catch (FileNotFoundException e) {
                            logger.fatal(e.getMessage(), e);
                        } catch (CertificateException e) {
                            logger.fatal(e.getMessage(), e);
                        } catch (IOException e) {
                            logger.fatal(e.getMessage(), e);
                        }
                        
                    }
                    
                    return certificates;
                }
                
            };
        
        client = new OkHttpClient();
        clientBuilder = client.newBuilder();
        SSLContext sslContext;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[] {
                trustAllManager
            }, new SecureRandom());
            
            clientBuilder.sslSocketFactory(sslContext.getSocketFactory(), trustAllManager);
            clientBuilder.hostnameVerifier(new HostnameVerifier() {
                
                @Override
                public boolean verify(final String arg0, final SSLSession arg1) {
                    //信任全部主机名
                    return true;
                }
            });
            
            client = clientBuilder.build();
        } catch (NoSuchAlgorithmException e) {
            logger.fatal(e.getMessage(), e);
        } catch (KeyManagementException e) {
            logger.fatal(e.getMessage(), e);
        }
        
    }
    
    /**
     * 
     * @param baseUrl 请求URL，不包含QueryString
     * @param params 请求的参数
     * @return 返回的内容
     */
    public String get(String baseUrl, Map<String, Object> params, Headers headers) {
        HttpUrl.Builder hb = HttpUrl.parse(baseUrl).newBuilder();
        if (null != params) {
            for (Map.Entry<String, Object> param : params.entrySet()) {
                hb.addQueryParameter(param.getKey(), param.getValue().toString());
            }
        }
        
        Request.Builder reqBuilder = new Request.Builder();
        
        reqBuilder.url(hb.build());
        if (null != headers) {
            reqBuilder.headers(headers);
        }
        Request request = reqBuilder.get().build();
        
        Response response;
        try {
            response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * 
     * @param baseUrl 请求URL，不包含请求参数
     * @param params 请求的参数
     * @return 返回的内容
     */
    public String post(String baseUrl, Map<String, Object> params, Headers headers) {
        FormBody.Builder formBuilder = new FormBody.Builder();
        
        if (null != params) {
            for (Map.Entry<String, Object> param : params.entrySet()) {
                formBuilder.add(param.getKey(), param.getValue().toString());
            }
        }
        
        Request.Builder reqBuilder = new Request.Builder();
        reqBuilder.url(baseUrl);
        if (null != headers) {
            reqBuilder.headers(headers);
        }
        
        Request request = reqBuilder.post(formBuilder.build()).build();
        
        Response response;
        try {
            response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return null;
        
    }

}
