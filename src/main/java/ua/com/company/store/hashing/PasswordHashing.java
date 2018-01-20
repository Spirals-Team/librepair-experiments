package ua.com.company.store.hashing;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Properties;

/**
 * Created by Владислав on 22.12.2017.
 */
public class PasswordHashing {
    private Logger logger =Logger.getRootLogger();

    private static final String HASHING_SALT_FILE ="/hashing.properties";
    private static final String WEB_SITE_KEY ="website.solt";
    private static   String WEBSITE_SALT;

    private PasswordHashing() {
        try {
            InputStream inputStream = PasswordHashing.class.getResourceAsStream(HASHING_SALT_FILE);
            Properties dbProps = new Properties();
            dbProps.load(inputStream);
            WEBSITE_SALT = dbProps.getProperty(WEB_SITE_KEY);
        } catch (IOException e) {
            logger.error("Can't load local salt form properties file", e);
        }
    }
    private static class Holder {
        static final PasswordHashing INSTANCE = new PasswordHashing();
    }

    public static PasswordHashing getInstance() {
        return Holder.INSTANCE;
    }

    public String hashingPassword(String password) throws NoSuchAlgorithmException {
        password += WEBSITE_SALT;
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }


}
