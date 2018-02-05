package io.descoped.client.api.config;

import io.descoped.client.exception.APIClientException;
import io.descoped.client.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 21/11/2017
 */
public class Configuration {

    private static final Logger log = LoggerFactory.getLogger(Configuration.class);

    private static Configuration INSTANCE;
    private Map<String,String> props = new HashMap<>();

    public Configuration() {
        loadConfiguration();
    }

    private void loadConfiguration() {
        InputStream in = CommonUtil.tccl().getResourceAsStream("META-INF/security.properties");
        if (in == null) {
            log.error("Security config '{}' NOT found!", "META-INF/security.properties");
            return;
        }
        try (OutputStream out = CommonUtil.writeInputToOutputStream(in)) {
            Scanner scanner = new Scanner(out.toString());
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String key = line.substring(0, line.indexOf("="));
                String value = line.substring(line.indexOf("=")+1);
                //log.trace("{} => {}", key, value);
                props.put(key, value);
            }
            scanner.close();
        } catch (IOException e) {
            throw new APIClientException(e);
        }
    }

    private static Configuration instance() {
        if (INSTANCE == null) {
            INSTANCE = new Configuration();
        }
        return INSTANCE;
    }

    private String getProperty(String key) {
        return props.get(key);
    }

    private String getProperty(String key, String defaultValue) {
        return (props.get(key) != null ? props.get(key) : defaultValue);
    }

    public static String getDeveloperAccessToken() {
        return instance().getProperty("facebook.developer.access_token", System.getenv().get("DEV_FACEBOOK_ACCESS_TOKEN"));
    }

    public static String getGoogleApiKey() {
        return instance().getProperty("google.api.key", System.getenv().get("DEV_GOOGLE_API_KEY"));
    }
}
