package ru.job4j.sqlru.bd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Vladimir Lembikov (cympak2009@mail.ru) on 18.04.2018.
 * @version 1.0.
 * @since 0.1.
 */
public class Query {
    private static final Logger LOG = LoggerFactory.getLogger(Query.class);

    public static final String CREATE_TABLE_VACANCY = "CREATE TABLE IF NOT EXISTS vacancy (\n"
            + "  id    SERIAL PRIMARY KEY,\n"
            + "  title VARCHAR(200),\n"
            + "  url   VARCHAR(500),\n"
            + "  text  TEXT,\n"
            + "  autor VARCHAR(100),\n"
            + "  date  TIMESTAMP\n"
            + ")";

    public static final String SELECT_MAX_DATE = "SELECT max(date) AS max_date FROM vacancy";

}
