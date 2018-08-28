package com.roldukhine.entity;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
@Transactional(readOnly = true)
public class UserImageServiceImpl implements UserImageService {

    private static final String BASE64_FIRST_PART_STRING = "data:image/png;base64,";

    private static final String DEFAULT_USER_IMAGE = "defaultUserImage.png";

    public byte[] getDefaultImageUser() {
        logger.debug("getDefaultImageUser");
        try {
            InputStream in = getClass().getClassLoader().getResourceAsStream(DEFAULT_USER_IMAGE);
            return IOUtils.toByteArray(in);
        } catch (IOException e) {
            logger.error("cannot find default user image. ", e);
        }
        logger.debug("cannot find default image user");
        return null;
    }

    @Override
    public String getPhotoEmployee(Employee employee) {
        byte[] photo = employee.getPhoto();
        if (photo == null) {
            photo = getDefaultImageUser();
        }
        String photoBase64 = new Base64().encodeAsString(photo);
        String sendByteArrayInBase64 = BASE64_FIRST_PART_STRING + photoBase64;
        logger.debug("sendByteArrayInBase64 {}", sendByteArrayInBase64);
        return sendByteArrayInBase64;
    }
}
