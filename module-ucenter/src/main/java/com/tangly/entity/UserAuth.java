package com.tangly.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author tangly
 */
@Table(name = "user_auth")
@ApiModel(description = "用户登录信息实体")
public class UserAuth implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(hidden = true)
    private Long id;

    @Column(name = "user_id")
    @ApiModelProperty(hidden = true)
    private Long userId;

    private boolean available;

    @Column(name = "login_type")
    @ApiModelProperty(example = "username")
    private String loginType;

    @Column(name = "login_account")
    private String loginAccount;

    @Column(name = "login_password")
    private String loginPassword;

    @Column(name = "login_salt")
    @ApiModelProperty(hidden = true)
    private String loginSalt;

    @Transient
    private UserInfo userInfo;

    @Transient
    @ApiModelProperty(hidden = true)
    private List<SysRole> sysRoleList;


    public String getCredentialsSalt() {
        return this.loginAccount + this.loginSalt;
    }

    public UserAuth() {
    }

    public UserAuth(Long userId, boolean available, String loginType, String loginAccount, String loginPassword, String loginSalt, UserInfo userInfo) {
        this.userId = userId;
        this.available = available;
        this.loginType = loginType;
        this.loginAccount = loginAccount;
        this.loginPassword = loginPassword;
        this.loginSalt = loginSalt;
        this.userInfo = userInfo;
    }

    @Override
    public String toString() {
        return "UserAuth{" +
                "id=" + id +
                ", userId=" + userId +
                ", available=" + available +
                ", loginType='" + loginType + '\'' +
                ", loginAccount='" + loginAccount + '\'' +
                ", loginPassword='" + loginPassword + '\'' +
                ", loginSalt='" + loginSalt + '\'' +
                ", userInfo=" + userInfo +
                ", sysRoleList=" + sysRoleList +
                '}';
    }

    public List<SysRole> getSysRoleList() {
        return sysRoleList;
    }

    public void setSysRoleList(List<SysRole> sysRoleList) {
        this.sysRoleList = sysRoleList;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
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
     * @return user_id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return available
     */
    public boolean getAvailable() {
        return available;
    }

    /**
     * @param available
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }

    /**
     * @return login_type
     */
    public String getLoginType() {
        return loginType;
    }

    /**
     * @param loginType
     */
    public void setLoginType(String loginType) {
        this.loginType = loginType == null ? null : loginType.trim();
    }

    /**
     * @return login_account
     */
    public String getLoginAccount() {
        return loginAccount;
    }

    /**
     * @param loginAccount
     */
    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount == null ? null : loginAccount.trim();
    }

    /**
     * @return login_password
     */
    public String getLoginPassword() {
        return loginPassword;
    }

    /**
     * @param loginPassword
     */
    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword == null ? null : loginPassword.trim();
    }

    /**
     * @return login_salt
     */
    public String getLoginSalt() {
        return loginSalt;
    }

    /**
     * @param loginSalt
     */
    public void setLoginSalt(String loginSalt) {
        this.loginSalt = loginSalt == null ? null : loginSalt.trim();
    }

}