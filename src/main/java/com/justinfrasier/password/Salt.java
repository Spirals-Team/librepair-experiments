package com.justinfrasier.password;

import com.justinfrasier.logger.Log;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Salt{

    public String generate(){
        String hash = null;
        try {
            long num;
            Random random = new Random();
            num = random.nextLong();
            String toHash  = String.valueOf(num);
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] bytes = md.digest(toHash.getBytes("UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
            for(byte i : bytes)
                stringBuilder.append(Integer.toString((i & 0xff) + 0x100, 16).substring(1));
            hash = stringBuilder.toString();
        }catch(NoSuchAlgorithmException| UnsupportedEncodingException e){
            Log log = new Log();
            log.writeln(e.getMessage());
            log.close();
        }
        return hash;
    }
}
