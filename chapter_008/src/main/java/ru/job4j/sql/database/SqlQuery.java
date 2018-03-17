package ru.job4j.sql.database;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 29.12.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class SqlQuery {
    public static final String CREATE_AUTHOR_TABLE = "CREATE TABLE IF NOT EXISTS author(\n"
            + "  id  SERIAL PRIMARY KEY,\n"
            + "  name  VARCHAR(50) UNIQUE ,\n"
            + "  url VARCHAR(500))";

    public static final String CREATE_TABLE_ADVERTS = "CREATE TABLE IF NOT EXISTS adverts(\n"
            + "  id SERIAL PRIMARY KEY,\n"
            + "  title VARCHAR(200),\n"
            + "  url VARCHAR(500),\n"
            + "  text_adv TEXT ,\n"
            + "  id_autor INTEGER REFERENCES author(id),\n"
            + "  date_publish TIMESTAMP,\n"
            + "  date_create TIMESTAMP)";
    public static final String SELECT_MAX_DATE = "SELECT max(date_publish) AS max_date FROM adverts";

    public static final String CREATE_FUNCTION_ADD_AUTHOR = "CREATE OR REPLACE FUNCTION add_author"
            + "(name_t VARCHAR(50), url_t VARCHAR(500)) RETURNS INTEGER\n"
            + "AS $$\n"
            + "DECLARE\n"
            + "  v int;\n"
            + "BEGIN\n"
            + "  INSERT INTO author(name, url) VALUES (name_t, url_t) ON CONFLICT (name) DO NOTHING RETURNING id INTO v;\n"
            + "  SELECT author.id FROM author WHERE author.name = name_t INTO v;\n"
            + "  RETURN v;\n"
            + "  COMMIT;\n"
            + "END\n"
            + "$$language plpgsql";
    public static final String CREATE_FUNCTION_ADD_ADVERT = "CREATE OR REPLACE FUNCTION add_advert( author_name VARCHAR(50), author_url VARCHAR(100),\n"
            + "  adv_title VARCHAR(200), adv_url VARCHAR(100), adv_text TEXT, data_p TIMESTAMP, data_c TIMESTAMP) RETURNS INTEGER\n" + "AS $$\n"
            + "DECLARE\n"
            + "  b INTEGER;\n"
            + "  id_aut INTEGER;\n"
            + "BEGIN\n"
            + " id_aut = add_author(author_name, author_url);\n"
            + "  INSERT INTO adverts(title, url, text_adv,id_autor,date_publish, date_create)\n"
            + "  VALUES (adv_title, adv_url, adv_text, id_aut,data_p, data_c) RETURNING adverts.id INTO b;\n"
            + "  DELETE FROM adverts WHERE id NOT IN (\n"
            + "    SELECT min(adverts.id) FROM adverts\n"
            + "    GROUP BY text_adv\n"
            + "  );\n"
            + "  SELECT adverts.id FROM adverts WHERE text_adv = adv_text INTO b;\n"
            + "    RETURN b;\n"
            + "  COMMIT;\n"
            + "  END\n"
            + "$$ LANGUAGE plpgsql";
    public static final String ADD_ADVERT = "SELECT add_advert(?, ?, ?, ?, ?, ?, ?)";
}
