package com.aliyuncs.fc.model;

public class VPCConfig {
    private String[] vSwitchIds;

    private String securityGroupId;

    public VPCConfig(String securityGroupId, String[] vSwitchIds) {
        this.securityGroupId = securityGroupId;
        this.vSwitchIds = vSwitchIds;
    }

    public String[] getvSwitchIds() {
        return vSwitchIds;
    }

    public String getSecurityGroupId() {
        return securityGroupId;
    }

    public void setvSwitchIds(String[] vSwitchIds) {
        this.vSwitchIds = vSwitchIds;
    }

    public void setSecurityGroupId(String securityGroupId) {
        this.securityGroupId = securityGroupId;
    }
}
