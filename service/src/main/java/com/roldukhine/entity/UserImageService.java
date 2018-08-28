package com.roldukhine.entity;

public interface UserImageService {
    byte[] getDefaultImageUser();

    String getPhotoEmployee(Employee employee);
}
