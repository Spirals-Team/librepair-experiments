/*
 *  Copyright 2016 http://www.hswebframework.org
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  
 */
package org.hswebframework.web.entity.oauth2.client;

import org.hswebframework.web.commons.entity.SimpleGenericEntity;

/**
 * OAuth2服务配置
 *
 * @author hsweb-generator-online
 */
public class SimpleOAuth2ServerConfigEntity extends SimpleGenericEntity<String> implements OAuth2ServerConfigEntity {
    //服务名称
    private String  name;
    //备注
    private String  describe;
    //api根地址
    private String  apiBaseUrl;
    //认证地址
    private String  authUrl;
    //token获取地址
    private String  accessTokenUrl;
    //客户端id
    private String  clientId;
    //客户端密钥
    private String  clientSecret;
    //是否启用
    private Boolean enabled;
    //重定向地址
    private String  redirectUri;

    //服务提供商
    private String provider;

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    @Override
    public String getRedirectUri() {
        return redirectUri;
    }

    @Override
    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    /**
     * @return 服务名称
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置 服务名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return 备注
     */
    public String getDescribe() {
        return this.describe;
    }

    /**
     * 设置 备注
     */
    public void setDescribe(String describe) {
        this.describe = describe;
    }

    /**
     * @return api根地址
     */
    public String getApiBaseUrl() {
        return this.apiBaseUrl;
    }

    /**
     * 设置 api根地址
     */
    public void setApiBaseUrl(String apiBaseUrl) {
        this.apiBaseUrl = apiBaseUrl;
    }

    /**
     * @return 认证地址
     */
    public String getAuthUrl() {
        return this.authUrl;
    }

    /**
     * 设置 认证地址
     */
    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    /**
     * @return token获取地址
     */
    public String getAccessTokenUrl() {
        return this.accessTokenUrl;
    }

    /**
     * 设置 token获取地址
     */
    public void setAccessTokenUrl(String accessTokenUrl) {
        this.accessTokenUrl = accessTokenUrl;
    }

    /**
     * @return 客户端id
     */
    public String getClientId() {
        return this.clientId;
    }

    /**
     * 设置 客户端id
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * @return 客户端密钥
     */
    public String getClientSecret() {
        return this.clientSecret;
    }

    /**
     * 设置 客户端密钥
     */
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    /**
     * @return 是否启用
     */
    public Boolean isEnabled() {
        return this.enabled;
    }

    /**
     * 设置 是否启用
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}