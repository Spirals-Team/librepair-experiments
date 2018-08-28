package com.roldukhine.jdbc;

import com.roldukhine.api.CrudDao;
import com.roldukhine.entity.BaseEntity;
import com.roldukhine.exception.DaoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Repository("abstractDao")
public abstract class AbstractDaoJdbcImpl<T extends BaseEntity> implements CrudDao<T> {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    protected abstract String getTableName();

    public abstract void update(T entity);

    public abstract void insert(T entity);

    protected abstract T createInstanceFromResult(ResultSet resultSet) throws SQLException;

    protected String getSelectAllStatement() {
        String query = "SELECT * FROM " + getTableName();
        logger.debug("getSelectAllStatementQuery: " + query);
        return query;
    }

    protected String getSelectByIdStatement() {
        String query = getSelectAllStatement() + " WHERE id = ?";
        logger.debug("selectByIdStatementQuery: " + query);
        return query;
    }

    protected String getDeleteByIdStatement() {
        String query = "DELETE FROM " + getTableName() + " WHERE id = ?";
        logger.debug("getDeleteByIdStatementQuery: " + query);
        return query;
    }

    public Connection getConnectionFromDataSource() {
        logger.trace("getConnectionFromDataSource()");
        try {
            Connection connection = dataSource.getConnection();
            logger.debug("return connection: ", connection);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.debug("return null connection");
        return null;
    }

    @Override
    public T get(long id) {
        logger.trace("get() input param: id ", id);
        try (Connection connection = getConnectionFromDataSource()) {
            logger.debug("getConnectionFromDataSource : " + connection);
            String selectByIdStatement = getSelectByIdStatement();
            logger.debug("selectByIdStatement: " + selectByIdStatement);
            try (PreparedStatement prepareStatement = connection.prepareStatement(selectByIdStatement)) {
                logger.debug("prepareStatement after create: " + prepareStatement);
                prepareStatement.setLong(1, id);
                try (ResultSet resultSet = prepareStatement.executeQuery()) {
                    logger.debug("resultSet after create " + resultSet);
                    if (resultSet.next()) {
                        logger.debug("createInstanceFromResult");
                        return createInstanceFromResult(resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Exception from get()", e);
            throw new DaoException(e);
        }

        return null;
    }

    @Override
    public void delete(T entity) {
        logger.trace("delete Entity: " + entity);
        long id = entity.getId();
        delete(id);
    }

    public void delete(long id) {
        logger.trace("delete entity by id : " + id);
        String deleteByIdStatementQuery = getDeleteByIdStatement();
        logger.debug("deleteByIdStatementQuery: " + deleteByIdStatementQuery);
        this.jdbcTemplate.update(deleteByIdStatementQuery, id);
    }

    @Override
    public List<T> getAll() {
        logger.trace("getAll");
        return this.jdbcTemplate.query(getSelectAllStatement(), (resultSet, index) -> {
            logger.trace("mapRow: resultSet, index" + resultSet + index);
            return createInstanceFromResult(resultSet);
        });
    }
}
