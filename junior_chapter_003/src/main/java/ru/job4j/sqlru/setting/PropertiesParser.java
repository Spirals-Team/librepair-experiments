package ru.job4j.sqlru.setting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

/**
 * @author Vladimir Lembikov (cympak2009@mail.ru) on 20.04.2018.
 * @version 1.0.
 * @since 0.1.
 */
public class PropertiesParser {
    private static final Logger LOG = LoggerFactory.getLogger(PropertiesParser.class);

    private final Properties properties = new Properties();
    private final File file;

    public PropertiesParser(String name) {
        this(new File(name));
    }

    public PropertiesParser(File file) {
        this.file = file;
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            try (InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, Charset.defaultCharset())) {
                properties.load(inputStreamReader);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private String getValue(String key) {
        String value = properties.getProperty(key);
        return value != null ? value.trim() : null;
    }

    public String getString(String key, String defaultValue) {
        String value = getValue(key);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }
}
