package ru.job4j.tracker.connection;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 09.12.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class  Query {
    public static final String CREATE_TRACKER_TABLE = "CREATE TABLE IF NOT EXISTS tracker(\n"
            + "  id SERIAL PRIMARY KEY NOT NULL ,\n"
            + "  name VARCHAR(50),\n"
            + "  description VARCHAR(100),\n"
            + "  create_date TIMESTAMP WITHOUT TIME ZONE\n"
            + ")";
    public static final String INSERT_NEW_ITEM = "INSERT INTO tracker(name, description, create_date) VALUES (?, ?, ?)";
    public static final String SELECT_ALL_ITEMS = "SELECT * FROM tracker";
    public static final String SELECT_BY_ID = "SELECT * FROM tracker WHERE id= ?";
    public static final String SELECT_BY_NAME = "SELECT * FROM tracker WHERE tracker.name LIKE ?";
    public static final String UPDATE_ITEM = "UPDATE tracker SET name = ?, description = ? WHERE id = ?";
    public static final String DELETE_ITEM = "DELETE  FROM tracker WHERE id = ?";

    public static final String CREATE_COMMENTS_TABLE = "CREATE TABLE IF NOT EXISTS comments(\n"
            + "  id SERIAL PRIMARY KEY ,\n"
            + "  id_item INTEGER REFERENCES tracker(id),\n"
            + "  description TEXT,\n"
            + "  data_create TIMESTAMP\n"
            + ")";
    public static final String INSERT_NEW_COMMENT = "INSERT INTO comments(id_item, description, data_create) VALUES (?, ?, ?)";
    public static final String SELECT_ALL_COMMENTS = "SELECT * FROM comments WHERE id_item =?";
    public static final String REMOVE_ALL_COMMENTS = "DELETE  FROM comments  WHERE id_item = ?";
    public static final String REMOVE_COMMENT = "DELETE FROM comments * WHERE id = ? AND id_item = ?";


}
