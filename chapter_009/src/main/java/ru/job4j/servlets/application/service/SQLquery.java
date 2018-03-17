package ru.job4j.servlets.application.service;

/**
 * .
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 13.01.18;
 * @version $Id$
 * @since 0.1
 */
public class SQLquery {

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
}
