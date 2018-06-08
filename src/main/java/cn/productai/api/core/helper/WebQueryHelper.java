package cn.productai.api.core.helper;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by Thinkpad on 2017/7/3.
 */
public class WebQueryHelper {
    public static String urlEncode(String para) throws UnsupportedEncodingException {
        if (para != null && !para.isEmpty())
            return URLEncoder.encode(para, "UTF-8");
        return "";
    }

    public static String urlDecode(String encodedUrl) throws UnsupportedEncodingException {
        if (encodedUrl != null && !encodedUrl.isEmpty())
            return URLDecoder.decode(encodedUrl, "UTF-8");
        return "";
    }
}
