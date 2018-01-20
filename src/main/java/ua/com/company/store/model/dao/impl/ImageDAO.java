package ua.com.company.store.model.dao.impl;

import ua.com.company.store.model.dao.connection.JDBCConnectionPool;
import ua.com.company.store.model.dao.daoAbstract.AbstractDao;
import ua.com.company.store.model.entity.Image;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Владислав on 04.12.2017.
 */
public class ImageDAO extends AbstractDao<Image> {
    public ImageDAO(JDBCConnectionPool jdbcConnectionPool) {
        super(jdbcConnectionPool);
    }

    @Override
    public Image update() {
        return null;
    }

    @Override
    public String getSelectQuery() {
        return null;
    }

    @Override
    public String getCreateQuery() {
        return null;
    }

    @Override
    public String getUpdateQuery() {
        return null;
    }

    @Override
    public String getDeleteQuery() {
        return "delete from onlinestoreproject.images where id = ?";
    }

    @Override
    public String getInsertQuery() {
        return "insert into onlinestoreproject.images( id, path, data) VALUES (?,?,?)";
    }

    @Override
    protected List<Image> parseResultSet(ResultSet rs) {
        return null;
    }

    @Override
    protected void prepareStatemantForInsert(PreparedStatement statement, Image object) {
        try {
            statement.setInt(1,0);
            statement.setString(2,object.getPath());
            statement.setString(3,object.getData());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void prepareStatemantForDelete(PreparedStatement statement, Image object) {
        try {
            statement.setInt(1,object.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Image getByParameter(String parameter) {
        return null;
    }
}
