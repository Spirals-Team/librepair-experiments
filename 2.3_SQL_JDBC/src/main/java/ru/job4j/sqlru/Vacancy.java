package ru.job4j.sqlru;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

/**
 * Class Vacancy.
 */
public class Vacancy {
    /**
     * @param LOGGER logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Vacancy.class);
    /**
     * @param name vacancy name
     */
    private final String name;
    /**
     * @param url vacancy url
     */
    private final String url;
    /**
     * @param date vacancy last update date
     */
    private final LocalDateTime date;

    /**
     * Constructor.
     *
     * @param name vacancy name
     * @param url  vacancy url
     * @param date vacancy last update date
     */
    public Vacancy(String name, String url, LocalDateTime date) {
        this.name = name;
        this.url = url;
        this.date = date;
    }

    /**
     * Getter for name.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for url.
     *
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Getter for date.
     *
     * @return date
     */
    public LocalDateTime getDate() {
        return date;
    }
}
