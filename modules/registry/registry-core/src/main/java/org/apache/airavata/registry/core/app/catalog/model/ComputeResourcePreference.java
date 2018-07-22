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
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "COMPUTE_RESOURCE_PREFERENCE")
@IdClass(ComputeResourcePreferencePK.class)
public class ComputeResourcePreference {
    @Id
    @Column(name = "GATEWAY_ID")
    private String gatewayId;
    @Id
    @Column(name = "RESOURCE_ID")
    private String resourceId;
    @Column(name = "OVERRIDE_BY_AIRAVATA")
    private boolean overrideByAiravata;
    @Column(name = "PREFERED_JOB_SUB_PROTOCOL")
    private String preferedJobSubmissionProtocol;
    @Column(name = "PREFERED_DATA_MOVE_PROTOCOL")
    private String preferedDataMoveProtocol;
    @Column(name = "PREFERED_BATCH_QUEUE")
    private String batchQueue;
    @Column(name = "SCRATCH_LOCATION")
    private String scratchLocation;
    @Column(name = "ALLOCATION_PROJECT_NUMBER")
    private String projectNumber;
    @Column(name = "LOGIN_USERNAME")
    private String loginUserName;
    @Column(name = "RESOURCE_CS_TOKEN")
    private String computeResourceCSToken;
    @Column(name = "USAGE_REPORTING_GATEWAY_ID")
    private String usageReportingGWId;
    @Column(name = "QUALITY_OF_SERVICE")
    private String qualityOfService;
    @Column(name = "RESERVATION")
    private String reservation;
    @Column(name = "RESERVATION_START_TIME")
    private Timestamp reservationStartTime;
    @Column(name = "RESERVATION_END_TIME")
    private Timestamp reservationEndTime;
    @Column(name = "SSH_ACCOUNT_PROVISIONER")
    private String sshAccountProvisioner;
    @Column(name = "SSH_ACCOUNT_PROVISIONER_ADDITIONAL_INFO")
    private String sshAccountProvisionerAdditionalInfo;


    @ManyToOne(cascade= CascadeType.MERGE)
    @JoinColumn(name = "RESOURCE_ID")
    private ComputeResource computeHostResource;

    @ManyToOne(cascade= CascadeType.MERGE)
    @JoinColumn(name = "GATEWAY_ID")
    private GatewayProfile gatewayProfile;

    @OneToMany(mappedBy = "computeResourcePreference", cascade = CascadeType.ALL, orphanRemoval = true)
    Collection<SSHAccountProvisionerConfiguration> sshAccountProvisionerConfigurations;


    public String getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(String gatewayId) {
        this.gatewayId = gatewayId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public boolean isOverrideByAiravata() {
        return overrideByAiravata;
    }

    public void setOverrideByAiravata(boolean overrideByAiravata) {
        this.overrideByAiravata = overrideByAiravata;
    }

    public String getPreferedJobSubmissionProtocol() {
        return preferedJobSubmissionProtocol;
    }

    public void setPreferedJobSubmissionProtocol(String preferedJobSubmissionProtocol) {
        this.preferedJobSubmissionProtocol = preferedJobSubmissionProtocol;
    }

    public String getPreferedDataMoveProtocol() {
        return preferedDataMoveProtocol;
    }

    public void setPreferedDataMoveProtocol(String preferedDataMoveProtocol) {
        this.preferedDataMoveProtocol = preferedDataMoveProtocol;
    }

    public String getBatchQueue() {
        return batchQueue;
    }

    public void setBatchQueue(String batchQueue) {
        this.batchQueue = batchQueue;
    }

    public String getScratchLocation() {
        return scratchLocation;
    }

    public void setScratchLocation(String scratchLocation) {
        this.scratchLocation = scratchLocation;
    }

    public String getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }

    public ComputeResource getComputeHostResource() {
        return computeHostResource;
    }

    public void setComputeHostResource(ComputeResource computeHostResource) {
        this.computeHostResource = computeHostResource;
    }

    public GatewayProfile getGatewayProfile() {
        return gatewayProfile;
    }

    public void setGatewayProfile(GatewayProfile gatewayProfile) {
        this.gatewayProfile = gatewayProfile;
    }

    public String getLoginUserName() {
        return loginUserName;
    }

    public void setLoginUserName(String loginUserName) {
        this.loginUserName = loginUserName;
    }

    public String getComputeResourceCSToken() {
        return computeResourceCSToken;
    }

    public void setComputeResourceCSToken(String computeResourceCSToken) {
        this.computeResourceCSToken = computeResourceCSToken;
    }

    public String getUsageReportingGWId() {
        return usageReportingGWId;
    }

    public void setUsageReportingGWId(String usageReportingGWId) {
        this.usageReportingGWId = usageReportingGWId;
    }

    public String getQualityOfService() {
        return qualityOfService;
    }

    public void setQualityOfService(String qualityOfService) {
        this.qualityOfService = qualityOfService;
    }

    public String getReservation() {
        return reservation;
    }

    public void setReservation(String reservation) {
        this.reservation = reservation;
    }

    public Timestamp getReservationStartTime() {
        return reservationStartTime;
    }

    public void setReservationStartTime(Timestamp reservationStratTime) {
        this.reservationStartTime = reservationStratTime;
    }

    public Timestamp getReservationEndTime() {
        return reservationEndTime;
    }

    public void setReservationEndTime(Timestamp reservationEndTime) {
        this.reservationEndTime = reservationEndTime;
    }

    public String getSshAccountProvisioner() {
        return sshAccountProvisioner;
    }

    public void setSshAccountProvisioner(String sshAccountProvisioner) {
        this.sshAccountProvisioner = sshAccountProvisioner;
    }

    public Collection<SSHAccountProvisionerConfiguration> getSshAccountProvisionerConfigurations() {
        return sshAccountProvisionerConfigurations;
    }

    public void setSshAccountProvisionerConfigurations(Collection<SSHAccountProvisionerConfiguration> sshAccountProvisionerConfigurations) {
        this.sshAccountProvisionerConfigurations = sshAccountProvisionerConfigurations;
    }

    public String getSshAccountProvisionerAdditionalInfo() {
        return sshAccountProvisionerAdditionalInfo;
    }

    public void setSshAccountProvisionerAdditionalInfo(String sshAccountProvisionerAdditionalInfo) {
        this.sshAccountProvisionerAdditionalInfo = sshAccountProvisionerAdditionalInfo;
    }
}
