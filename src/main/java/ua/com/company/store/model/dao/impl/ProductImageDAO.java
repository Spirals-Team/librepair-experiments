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
 * Created by Владислав on 19.12.2017.
 */
public class ProductImageDAO extends AbstractDao<ProductImage> {

    public ProductImageDAO(JDBCConnectionPool jdbcConnectionPool) {
        super(jdbcConnectionPool);
    }

    @Override
    public ProductImage update() {
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
        return null;
    }

    @Override
    public String getInsertQuery() {
        return "insert into onlinestoreproject.images( id, path, data) VALUES (?,?,?)";
    }

    @Override
    protected List<ProductImage> parseResultSet(ResultSet rs) {
        return null;
    }

    @Override
    protected void prepareStatemantForInsert(PreparedStatement statement, ProductImage object) {
        try {
            statement.setInt(1, 0);
            statement.setString(2, object.getTitle());
            statement.setString(3, object.getDescription());
            statement.setInt(4, object.getPrice());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void prepareStatemantForInsertForProduct(PreparedStatement statement, Product object) {
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
    protected void prepareStatemantForDelete(PreparedStatement statement, ProductImage object) {

    }

    @Override
    public ProductImage getByParameter(String parameter) {
        List<ProductImage> productImageList = getAllProductsWithImage();
        for (ProductImage productImage: productImageList){
            return productImage.getId()==Integer.parseInt(parameter) ? productImage: null ;
        }
        return null;
    }

    @Override
    public int[] insertImageAndProduct(Image image, Product product) {
        Connection connection = null;
        ResultSet resultSetImage = null;
        ResultSet resultSetProduct = null;
        PreparedStatement preparedStatementImageInsert = null;
        PreparedStatement preparedStatementProductInsert = null;
        String imageInser = getInsertQuery();
        int rowAffected = 0;
        int imageId = 0;
        int productId = 0;
        try {
            connection = getConnectionFromPool();
            connection.setAutoCommit(false);
            preparedStatementImageInsert = connection.prepareStatement(imageInser, Statement.RETURN_GENERATED_KEYS);
            preparedStatementImageInsert.setInt(1, 0);
            preparedStatementImageInsert.setString(2, image.getPath());
            preparedStatementImageInsert.setString(3, image.getData());
            rowAffected = preparedStatementImageInsert.executeUpdate();
            resultSetImage = preparedStatementImageInsert.getGeneratedKeys();
            if (resultSetImage.next()) {
                imageId = resultSetImage.getInt(1);
            }
            if (rowAffected != 1) {
                connection.rollback();
            }
            preparedStatementProductInsert = connection.prepareStatement("INSERT INTO onlinestoreproject.products (id,title,description,price,image_id) values(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            prepareStatemantForInsertForProduct(preparedStatementProductInsert, product);
            preparedStatementProductInsert.setInt(5, imageId);
            preparedStatementProductInsert.executeUpdate();
            resultSetProduct = preparedStatementProductInsert.getGeneratedKeys();
            if (resultSetProduct.next()) {
                productId = resultSetProduct.getInt(1);
            }

            connection.commit();

        } catch (SQLException e) {
            try {
                if (connection != null)
                    connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            closeResources(resultSetImage, preparedStatementImageInsert, connection);
            try {
                preparedStatementProductInsert.close();
                resultSetProduct.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return new int[]{imageId, productId};
    }

    public List<ProductImage> getAllProductsWithImage() {
        List<ProductImage> productImageList = new ArrayList<>();
        String query = "select * from onlinestoreproject.products " + "LEFT JOIN onlinestoreproject.images on onlinestoreproject.products.image_id =onlinestoreproject.images.id  ";
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try (Connection connection = JDBCConnectionPool.getInstanceConnectionPool().getConnection()) {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            productImageList = parsingResultSetForProductImage(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productImageList;
    }

    private List<ProductImage> parsingResultSetForProductImage(ResultSet resultSet) throws SQLException {
        List<ProductImage> list = new ArrayList<>();
        if (resultSet == null) return null;
        while (resultSet.next()) {
            ProductImage productImage = new ProductImage();
            productImage.setId(resultSet.getInt("id"));
            productImage.setTitle(resultSet.getString("title"));
            productImage.setDescription(resultSet.getString("description"));
            productImage.setPrice(resultSet.getInt("price"));
            productImage.setPathImage("/resources/images/" + resultSet.getString("data"));
            list.add(productImage);
        }
        return list;
    }
}
