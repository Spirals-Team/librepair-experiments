package com.gdc.aerodev.service.security;

import com.gdc.aerodev.service.exception.ServiceException;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {

    public static String hash(String target){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(target.getBytes());
            byte[] digest = md.digest();
            return DatatypeConverter.printHexBinary(digest).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            throw new ServiceException("Error encrypting password: ", e);
        }
    }

}
