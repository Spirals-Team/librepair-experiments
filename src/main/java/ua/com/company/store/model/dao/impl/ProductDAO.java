package ua.com.company.store.model.dao.impl;

import ua.com.company.store.model.dao.connection.JDBCConnectionPool;
import ua.com.company.store.model.dao.daoAbstract.AbstractDao;
import ua.com.company.store.model.entity.Image;
import ua.com.company.store.model.entity.Product;
import ua.com.company.store.model.entity.User;
import ua.com.company.store.model.entity.additional.ProductImage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Владислав on 04.12.2017.
 */
public class ProductDAO extends AbstractDao<Product> {
    public ProductDAO(JDBCConnectionPool jdbcConnectionPool) {
        super(jdbcConnectionPool);
    }

    @Override
    public Product update() {
        return null;
    }

    @Override
    public String getSelectQuery() {
        return "select * from onlinestoreproject.products ";
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
        return "delete from onlinestoreproject.products where id = ? ";
    }

    @Override
    public String getInsertQuery() {
        return "insert into onlinestoreproject.products(id,title,description,price,image_id) values(?,?,?,?,?)";
    }

    @Override
    protected List<Product> parseResultSet(ResultSet rs) {
        List<Product> list = new ArrayList<>();
        try {
            while (rs.next()){
                Product product = new Product.ProductBuilder().setId(rs.getInt("id"))
                        .setTitle(rs.getString("title"))
                        .setDescr(rs.getString("description"))
                        .setPrice(rs.getInt("price"))
                        .setImgId(rs.getInt("image_id"))
                        .build();
                list.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    protected void prepareStatemantForInsert(PreparedStatement statement, Product object) {
        try {
            statement.setInt(1, 0);
            statement.setString(2, object.getTitle());
            statement.setString(3, object.getDescription());
            statement.setInt(4, object.getPrice());

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void prepareStatemantForDelete(PreparedStatement statement, Product object) {
        try {
            statement.setInt(1, object.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Product getByParameter(String parameter) {
        for (Product product: getAllProducts()){
            if (product.getId() == Integer.parseInt(parameter)){
                return product;
            }
            else {
                return null;
            }
        }
        return null;
    }

    public List<Product> getAllProducts() {
        List<Product> productImageList = new ArrayList<>();
        String query = getSelectQuery();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try (Connection connection = JDBCConnectionPool.getInstanceConnectionPool().getConnection()) {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            productImageList = parseResultSet(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productImageList;
    }






}
