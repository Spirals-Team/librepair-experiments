package lelental.repository;

import lelental.domain.Author;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Paweł Lelental
 **/
public interface AuthorRepository {

    boolean insert(Author author1);

    void update(Author author) throws SQLException;

    Author findById(long id) throws SQLException;

    List<Author> findAll() throws SQLException;

    void delete(long id) throws SQLException;

    String sayWhoYouAre() throws SQLException;

    Author findByName(String name);

    void populateDb() throws SQLException;

    void dropAuthorTable() throws SQLException;

    Connection getConnection();

    boolean isDatabaseReady();

    void setConnection(Connection connection) throws SQLException;
}
