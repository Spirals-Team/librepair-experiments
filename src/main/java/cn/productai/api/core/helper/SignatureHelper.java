package cn.productai.api.core.helper;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

/**
 * Created by Thinkpad on 2017/7/3.
 */
public class SignatureHelper {
    public static String signature(String secretKey, HashMap<String, String> paras) throws Exception {
        ArrayList<String> excludeKeys = new ArrayList<>();
        excludeKeys.add("x-ca-signature");
        excludeKeys.add("x-ca-file-md5");

        ArrayList<String> res = new ArrayList<>();

        //exclude keys
        HashMap<String, String> fields = new HashMap<>();
        for (String key : paras.keySet()) {
            if (excludeKeys.contains(key)) {
                continue;
            }

            fields.put(key, paras.get(key));
        }

        //sort
        List<Map.Entry<String, String>> _fields = new ArrayList<>(fields.entrySet());
        Collections.sort(_fields, new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });

        //combine
        for (Map.Entry<String, String> keyValuePair : _fields) {
            res.add(String.format("%s=%s", keyValuePair.getKey(), keyValuePair.getValue()));
        }

        String urlPairs = String.join("&", res);

        //Mac
        Mac mac = Mac.getInstance("HmacSHA1");
        byte[] keyBytes = secretKey.getBytes("UTF-8");
        SecretKey secretKey1 = new SecretKeySpec(keyBytes, "HmacSHA1");
        mac.init(secretKey1);
        byte[] textBytes = urlPairs.getBytes("UTF-8");

        return Base64.getEncoder().encodeToString(mac.doFinal(textBytes));

    }
}
