package ua.epam.spring.hometask.web.rest.assured;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class ResourceUtil {
    public static String loadFrom(String filePath) throws IOException {
        InputStream is = ResourceUtil.class.getResourceAsStream(filePath);
        return IOUtils.toString(is);
    }
}
