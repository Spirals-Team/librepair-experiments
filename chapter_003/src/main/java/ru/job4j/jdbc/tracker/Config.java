package ru.job4j.jdbc.tracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Config class load properties.
 *
 * @version $Id$
 * @since 0.1
 */
public class Config {

    private static final Logger LOG = LoggerFactory.getLogger(Config.class);

    private static final ResourceBundle RB = ResourceBundle.getBundle("app");
    private Properties prop = new Properties();

    public Config() {
        loadProps();
    }

    /**
     * Get SQL query
     *
     * @param key key
     * @return sql query
     */
    public String getQuery(final String key) {
        return prop.getProperty(key);
    }

    /**
     * Get connection URL
     *
     * @return URL
     */
    public String getURL() {
        return RB.getString("db.url");
    }

    private void loadProps() {
        Path path = Paths.get(getPathToQueries(), "sql.xml");
        try {
            prop.loadFromXML(new FileInputStream(String.valueOf(path)));
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        LOG.info("Load properties from { {} }.", path);
    }

    private String getPathToQueries() {
        return RB.getString("sql.path");
    }
}