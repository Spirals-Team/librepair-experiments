package com.gdc.aerodev.model;

public class Avatar {

    private Long avatarId;
    private Long avatarOwner;
    private byte[] avatarData;
    private String contentType;

    public Avatar() {
    }

    public Avatar(Long avatarOwner, byte[] avatarData, String contentType) {
        this.avatarOwner = avatarOwner;
        this.avatarData = avatarData;
        this.contentType = contentType;
    }

    public Avatar(Long avatarId, Long avatarOwner, byte[] avatarData, String contentType) {
        this.avatarId = avatarId;
        this.avatarOwner = avatarOwner;
        this.avatarData = avatarData;
        this.contentType = contentType;
    }

    public Long getAvatarId() {
        return avatarId;
    }

    public Long getAvatarOwner() {
        return avatarOwner;
    }

    public byte[] getAvatarData() {
        return avatarData;
    }

    public String getContentType() {
        return contentType;
    }

    public void setAvatarId(Long avatarId) {
        this.avatarId = avatarId;
    }

    public void setAvatarOwner(Long avatarOwner) {
        this.avatarOwner = avatarOwner;
    }

    public void setAvatarData(byte[] avatarData) {
        this.avatarData = avatarData;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
