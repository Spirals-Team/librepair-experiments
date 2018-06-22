package com.hashmapinc.haf.models;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class User implements UserInformation, Serializable{
    private static final long serialVersionUID = -350874482962054954L;

    private UUID id;
    private String userName;
    private String tenantId;
    private String customerId;
    private String clientId;
    private String firstName;
    private String lastName;
    private List<String> authorities;
    private Map<String, String> additionalDetails;

    private boolean enabled;

    public User(){}

    public User(UUID id){
        this.id = id;
    }

    public User(User user) {
        this.id = user.getId();
        this.userName = user.getUserName();
        this.tenantId = user.getTenantId();
        this.customerId = user.getCustomerId();
        this.clientId = user.getClientId();
        this.authorities = user.getAuthorities();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.enabled = user.isEnabled();
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Map<String, String> getAdditionalDetails() {
        return additionalDetails;
    }

    public void setAdditionalDetails(Map<String, String> additionalDetails) {
        this.additionalDetails = additionalDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return isEnabled() == user.isEnabled() &&
                Objects.equals(getId(), user.getId()) &&
                Objects.equals(getUserName(), user.getUserName()) &&
                Objects.equals(getTenantId(), user.getTenantId()) &&
                Objects.equals(getCustomerId(), user.getCustomerId()) &&
                Objects.equals(getClientId(), user.getClientId()) &&
                Objects.equals(getFirstName(), user.getFirstName()) &&
                Objects.equals(getLastName(), user.getLastName()) &&
                Objects.equals(getAuthorities(), user.getAuthorities()) &&
                Objects.equals(getAdditionalDetails(), user.getAdditionalDetails());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUserName(), getTenantId(), getCustomerId(), getClientId(), getFirstName(), getLastName(), getAuthorities(), getAdditionalDetails(), isEnabled());
    }
}
