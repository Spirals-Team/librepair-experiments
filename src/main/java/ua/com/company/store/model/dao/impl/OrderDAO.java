package ua.com.company.store.model.dao.impl;

import org.apache.log4j.Logger;
import ua.com.company.store.model.dao.connection.JDBCConnectionPool;
import ua.com.company.store.model.dao.daoAbstract.AbstractDao;
import ua.com.company.store.model.entity.Order;
import ua.com.company.store.model.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Владислав on 19.12.2017.
 */
public class OrderDAO extends AbstractDao<Order> {
private Logger logger = Logger.getRootLogger();
    public OrderDAO(JDBCConnectionPool jdbcConnectionPool) {
        super(jdbcConnectionPool);
    }

    @Override
    public Order update() {
        return null;
    }

    @Override
    public String getSelectQuery() {
        return "select * from onlinestoreproject.orders";
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
        return " delete from onlinestoreproject.orders where id = ? ";
    }

    @Override
    public String getInsertQuery() {
        return "insert into onlinestoreproject.orders(id,product_id,entity_id,date) VALUES (?,?,?,?)";
    }

    @Override
    protected List<Order> parseResultSet(ResultSet rs) {
        List<Order> list = new ArrayList<>();
        try {
            while (rs.next()){
                Order order = new Order.OrderBuilder()
                        .setId(rs.getInt("id"))
                        .setEntityId(rs.getInt("entity_id"))
                        .setProductId(rs.getInt("product_id"))
                        .setData(rs.getDate("date"))
                        .setPaid(rs.getBoolean("successful_paid"))
                        .build();

                list.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    protected void prepareStatemantForInsert(PreparedStatement statement, Order object) {
        try {
            statement.setInt(1,object.getId());
            statement.setInt(2,object.getProductId());
            statement.setInt(3,object.getEntityId());
            statement.setDate(4, (Date) object.getDate());
            statement.setBoolean(5, object.isSuccessful_paid());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void prepareStatemantForDelete(PreparedStatement statement, Order object) {
        try {
            statement.setInt(1,object.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Order getByParameter(String parameter) {
        List<Order> list = null;
        String query = getSelectQuery() + " where entity_id = ?";
        Connection connection= null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
       try {
            connection = getConnectionFromPool();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, Integer.parseInt(parameter));
            resultSet = preparedStatement.executeQuery();
            list = parseResultSet(resultSet);
            if (list ==null || list.size()>1){
                logger.error("Cant search users with nickname " + parameter);
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Cant get all admins " + e);
        }
        finally {
            closeResources(resultSet,preparedStatement,connection);
        }
        return list.get(0);
    }
}
