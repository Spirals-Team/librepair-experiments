package com.justinfrasier.password;

import com.justinfrasier.logger.Log;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashPassword {

    public String get_SHA_512_SecurePassword(String passwordToHash, String salt){
         String generatedPassword = null;
         try{
             MessageDigest md = MessageDigest.getInstance("SHA-512");
             md.update(salt.getBytes("UTF-8"));
             byte[] bytes = md.digest(passwordToHash.getBytes("UTF-8"));
             StringBuilder stringBuilder = new StringBuilder();
             for (int i=0; i<bytes.length; i++){
                 stringBuilder.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
             }
             generatedPassword = stringBuilder.toString();
         }catch (NoSuchAlgorithmException|UnsupportedEncodingException e){
             Log log = new Log();
             log.writeln(e.getMessage());
             log.close();
         }
         return generatedPassword;
    }

}
