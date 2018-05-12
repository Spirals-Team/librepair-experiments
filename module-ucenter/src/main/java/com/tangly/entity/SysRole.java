package com.tangly.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Table(name = "sys_role")
public class SysRole implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean available;

    private String description;

    private String role;

    @Transient
    private List<SysPermission> sysPermissionList;

    public List<SysPermission> getSysPermissionList() {
        return sysPermissionList;
    }

    public void setSysPermissionList(List<SysPermission> sysPermissionList) {
        this.sysPermissionList = sysPermissionList;
    }

    @Override
    public String toString() {
        return "SysRole{" +
                "id=" + id +
                ", available=" + available +
                ", description='" + description + '\'' +
                ", role='" + role + '\'' +
                ", sysPermissionList=" + sysPermissionList +
                '}';
    }

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return available
     */
    public Boolean getAvailable() {
        return available;
    }

    /**
     * @param available
     */
    public void setAvailable(Boolean available) {
        this.available = available;
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * @return role
     */
    public String getRole() {
        return role;
    }

    /**
     * @param role
     */
    public void setRole(String role) {
        this.role = role == null ? null : role.trim();
    }
}