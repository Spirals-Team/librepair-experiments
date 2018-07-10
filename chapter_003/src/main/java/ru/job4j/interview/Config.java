package ru.job4j.interview;

import org.apache.log4j.Logger;

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

    private static final Logger LOG = Logger.getLogger(Config.class);

    private static final ResourceBundle RB = ResourceBundle.getBundle("vacancy_app");
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
     * Get DB connection URL
     *
     * @return URL
     */
    public String getDBURL() {
        return RB.getString("db.url");
    }

    /**
     * Get URL of start page
     *
     * @return url
     */
    public String getStartURL() {
        return RB.getString("start.url");
    }

    /**
     * Get template for URL page with found vacancies
     *
     * @return template
     */
    public String getPageURLTemplate() {
        return RB.getString("page.url.template");
    }

    private void loadProps() {
        Path path = Paths.get(getPathToQueries(), "vacancy_sql.xml");
        try {
            prop.loadFromXML(new FileInputStream(String.valueOf(path)));
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        LOG.info(String.format("Load properties from { %s }.", String.valueOf(path)));
    }

    private String getPathToQueries() {
        return RB.getString("sql.path");
    }
}
