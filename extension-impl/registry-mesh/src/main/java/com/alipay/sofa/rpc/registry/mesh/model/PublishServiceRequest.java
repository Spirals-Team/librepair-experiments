/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alipay.sofa.rpc.registry.mesh.model;

/**
 * @author bystander
 * @version $Id: PublishServiceRequest.java, v 0.1 2018年04月03日 11:27 AM bystander Exp $
 */
public class PublishServiceRequest {

    private String           serviceName;

    //timeout/appName/serialize/p
    private ProviderMetaInfo providerMetaInfo;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public ProviderMetaInfo getProviderMetaInfo() {
        return providerMetaInfo;
    }

    public void setProviderMetaInfo(ProviderMetaInfo providerMetaInfo) {
        this.providerMetaInfo = providerMetaInfo;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PublishServiceRequest{");
        sb.append("serviceName='").append(serviceName).append('\'');
        sb.append(", providerMetaInfo=").append(providerMetaInfo);
        sb.append('}');
        return sb.toString();
    }
}