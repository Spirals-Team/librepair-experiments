package lelental.repository;

import lelental.domain.Author;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Paweł Lelental
 **/
public interface AuthorRepository {

    void insert(Author author1) throws SQLException;


    void update(Author author) throws SQLException;

    Author findById(int id) throws SQLException;

    List<Author> findAll() throws SQLException;

    void delete(Author author) throws SQLException;

    String sayWhoYouAre() throws SQLException;

    Author findByName(String name);

    void populateDb() throws SQLException;

    void dropAuthorTable() throws SQLException;

    Connection getConnection();

    void setConnection(Connection connection) throws SQLException;
}
