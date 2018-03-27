package ru.job4j.html.service;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 04.02.2018.
 * @version $Id$.
 * @since 0.1.
 */
public class SQLQuery {
    public final static String CREATE_TABLE_TOWNS = "CREATE TABLE IF NOT EXISTS towns("
            +            "  id SERIAL PRIMARY KEY,"
            +            "  name VARCHAR(100),"
            +            "  country_id INT REFERENCES country(id)"
            +            ")";
    public final static String CREATE_TABLE_COUNTRIES = "CREATE TABLE IF NOT EXISTS country("
            +            "  id SERIAL PRIMARY KEY ,"
            +            "  country VARCHAR(50)"
            +            ")";
    public final static String GET_ALL_COUNTRIES = "SELECT * FROM country";
    public static final String CREATE_TABLE_USERS = "CREATE TABLE IF NOT EXISTS users("
            + "  id SERIAL PRIMARY KEY ,"
            + "  login VARCHAR(100)  UNIQUE,"
            + "  name VARCHAR(100),"
            + "  email VARCHAR(100),"
            + "  date TIMESTAMP,"
            + "  role INT REFERENCES role(id),"
            + "   password VARCHAR(20)"
            + ")";
    public static final String CREATE_TABLE_ROLE = "CREATE TABLE IF NOT EXISTS role("
            + "  id SERIAL PRIMARY KEY ,"
            + "  role VARCHAR(20)"
            + ")";
    public static final String ADD_NEW_USER = "INSERT INTO users (login, name, email, date, password, role)"
            + "VALUES (?,?,?,?,?,(Select role.id FROM role WHERE role.role=?))";
    public static final String GET_USER_BY_LOGIN = "SELECT users.id as id, name, login,email, date, role.role as role, password "
            + "FROM users LEFT JOIN role ON users.role=role.id WHERE users.login = ?";
    public static final String UPDATE_USER = "UPDATE users SET name = ?, login = ?, email = ?, password = ? WHERE login = ?";
    public static final String DELETE_USER = "DELETE FROM users WHERE login = ?";
    public static final String SELECT_ALL_USERS = "SELECT users.id as id, name, login,email, date, role.role as role, password"
            + " FROM users LEFT JOIN role ON users.role=role.id ORDER BY users.id";
    public static final String CREATE_ROLES = "INSERT INTO role (role)  VALUES ('user'), ('admin')";
    public static final String CREATE_ROOT_USER = "INSERT INTO users(login, role, password, date) VALUES ('root', 2, 'root' ,now())";
    public static final String CHECK_IS_USER_A_ADMIN = "SELECT role.role as role FROM users LEFT JOIN role ON users.role = role.id WHERE users.login = ?";

    public static final String GET_ROLES = "SELECT role.role FROM role";
    public static final String ADD_COUNTIES = "INSERT INTO country(country) VALUES ('Russia'),"
            + "  ('USA'),"
            + "  ('Canada'),"
            + "  ('Moldova')";
    public static final String ADD_TOWNS = "INSERT INTO towns(name, country_id) VALUES ('New-York', (SELECT id FROM country WHERE country.country = 'USA')),"
            +            "  ('Texas', (SELECT id FROM country WHERE country.country='USA')),"
            +            "  ('Los-Angeles', (SELECT id FROM country WHERE country.country='USA')),"
            +            "  ('Moscou', (SELECT id FROM country WHERE country.country='Russia')),"
            +            "  ('Magadan', (SELECT id FROM country WHERE country.country='Russia')),"
            +            "  ('Voronej', (SELECT id FROM country WHERE country.country='Russia')),"
            +            " ('Chisinau', (SELECT id FROM country WHERE country.country='Moldova')),"
            +            "  ('Cahul', (SELECT id FROM country WHERE country.country='Moldova')),"
            +            "  ('Montreal',(SELECT id FROM country WHERE country.country='Canada')),"
            +            "  ('Toronto',(SELECT id FROM country WHERE country.country='Canada')),"
            +            "  ('Ottava',(SELECT id FROM country WHERE country.country='Canada')),"
            +            "  ('Victoria',(SELECT id FROM country WHERE country.country='Canada'))";
    public static final String SELECT_TOWNS_BY_ID = "SELECT  towns.id as id, towns.name as name FROM towns WHERE towns.country_id=?";
}
