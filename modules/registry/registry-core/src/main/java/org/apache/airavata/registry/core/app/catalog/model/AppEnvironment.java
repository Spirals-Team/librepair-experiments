/**
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.airavata.registry.core.app.catalog.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "APP_ENVIRONMENT")
@IdClass(AppEnvironment_PK.class)
public class AppEnvironment implements Serializable {
    @Id
    @Column(name = "DEPLOYMENT_ID")
    private String deploymentID;
    @Id
    @Column(name = "NAME")
    private String name;

    @Column(name = "VALUE")
    private String value;

    @Column(name = "ENV_ORDER")
    private Integer order;

    @ManyToOne(cascade= CascadeType.MERGE)
    @JoinColumn(name = "DEPLOYMENT_ID")
    private ApplicationDeployment applicationDeployment;

    public String getDeploymentID() {
        return deploymentID;
    }

    public void setDeploymentID(String deploymentID) {
        this.deploymentID = deploymentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ApplicationDeployment getApplicationDeployment() {
        return applicationDeployment;
    }

    public void setApplicationDeployment(ApplicationDeployment applicationDeployment) {
        this.applicationDeployment = applicationDeployment;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
