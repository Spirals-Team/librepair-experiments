/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.aliyuncs.fc.response;

import com.aliyuncs.fc.model.LogConfig;
import com.aliyuncs.fc.model.ServiceMetadata;
import com.aliyuncs.fc.http.HttpResponse;

import com.google.common.base.Preconditions;
import java.util.Map;

/**
 * TODO: add javadoc
 */
public class GetServiceResponse extends HttpResponse {

    private Map<String, String> header;
    private ServiceMetadata serviceMetadata;

    public Map<String, String> getHeader() {
        return header;
    }

    public GetServiceResponse setHeader(Map<String, String> header) {
        this.header = header;
        return this;
    }

    public String getServiceName() {
        Preconditions.checkArgument(serviceMetadata != null);
        return serviceMetadata.getServiceName();
    }

    public String getDescription() {
        Preconditions.checkArgument(serviceMetadata != null);
        return serviceMetadata.getDescription();
    }

    public String getRole() {
        Preconditions.checkArgument(serviceMetadata != null);
        return serviceMetadata.getRole();
    }

    public LogConfig getLogConfig() {
        Preconditions.checkArgument(serviceMetadata != null);
        return serviceMetadata.getLogConfig();
    }

    public String getServiceId() {
        Preconditions.checkArgument(serviceMetadata != null);
        return serviceMetadata.getServiceId();
    }

    public String getCreatedTime() {
        Preconditions.checkArgument(serviceMetadata != null);
        return serviceMetadata.getCreatedTime();
    }

    public String getLastModifiedTime() {
        Preconditions.checkArgument(serviceMetadata != null);
        return serviceMetadata.getLastModifiedTime();
    }

    public GetServiceResponse setServiceMetadata(ServiceMetadata serviceMetadata) {
        this.serviceMetadata = serviceMetadata;
        return this;
    }

    public String getRequestId() {
        return header.get("X-Fc-Request-Id");
    }

    public String getEtag() {
        return header.get("Etag");
    }

}
