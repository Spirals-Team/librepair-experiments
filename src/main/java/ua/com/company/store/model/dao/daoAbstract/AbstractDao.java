package ua.com.company.store.model.dao.daoAbstract;

import org.apache.log4j.Logger;
import ua.com.company.store.model.dao.connection.ConnectionPoolDataSource;
import ua.com.company.store.model.dao.connection.JDBCConnectionPool;
import ua.com.company.store.model.entity.Image;
import ua.com.company.store.model.entity.Product;
import ua.com.company.store.model.entity.User;

import java.sql.*;
import java.util.List;

/**
 * Created by Владислав on 22.1 1.2017.
 */
public abstract class AbstractDao<T> implements GenericDAO<T> {
    private Logger logger = Logger.getRootLogger();
    private ConnectionPoolDataSource connectionPoolDataSource;
    private JDBCConnectionPool jdbcConnectionPool;


    public AbstractDao(JDBCConnectionPool jdbcConnectionPool) {
        this.jdbcConnectionPool = jdbcConnectionPool;
        }

    /**
     * @return returns sql statement as string for getting all elements 'SELECT * FROM [TABLE]'
     */
    public abstract String getSelectQuery();

    /**
     * @return returns sql statement as string for insert new note 'insert into [table] (column ...) values (?...)'
     */
    public abstract String getCreateQuery();

    /**
     * @return returns sql statement as string for updating elements 'update [table] set [column = ? ..,.] where id =?'
     */
    public abstract String getUpdateQuery();

    /**
     * @return returns sql statement as string for deleting rows'SELECT * FROM [TABLE]'
     */
    public abstract String getDeleteQuery();


    /**
     * @return returns sql statement as string for insert rows
     */
    public abstract String getInsertQuery();

    protected abstract List<T> parseResultSet(ResultSet rs);

    protected abstract void prepareStatemantForInsert(PreparedStatement statement, T object);


    protected abstract void prepareStatemantForDelete(PreparedStatement statement, T object);

    @Override
    public int insert(T object) {
        int id = 0;
        String query = getInsertQuery();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;
        try {
            connection = getConnectionFromPool();
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            prepareStatemantForInsert(preparedStatement, object);
            preparedStatement.executeUpdate();
            System.out.println(id);
            logger.info("Insert into db new object " + object.toString());
             generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        finally {
            closeResources(generatedKeys,preparedStatement,connection);
        }
        return id;
    }

  public void markUserAsDefaulter(User user){

  }
    @Override
    public T getById(int key) {
        List<T> list = null;
        String sql_query = getSelectQuery();
        sql_query += " where id = ? ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnectionFromPool();
            preparedStatement = connection.prepareStatement(sql_query);
            preparedStatement.setInt(1, key);
            resultSet = preparedStatement.executeQuery();
            list = parseResultSet(resultSet);

            if (list == null) return null;
            if (list.size() > 1) return null;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Cant search admin by Id " + key);
        }
        finally {
            closeResources(resultSet,preparedStatement,connection);
        }
        return list.iterator().next();
    }

    public List<T> getAll() {
        List<T> list = null;
        String sql = getSelectQuery();
        Connection connection= null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnectionFromPool();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            list = parseResultSet(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Cant get all admins " + e);
        }
        finally {
            closeResources(resultSet,preparedStatement,connection);
        }
        return list;
    }
    public abstract T getByParameter(String parameter);

    /**
     * Method which persist object
     *
     * @param object
     * @return
     */
    public void delete(T object) {
        Connection connection = null;
        try {
            connection = getConnectionFromPool();
            String sql = getDeleteQuery();
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                prepareStatemantForDelete(statement,object);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnectionFromPool() throws SQLException{
        return jdbcConnectionPool.getConnection();

       }

    protected void closeResources(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection){
        try {
            if(resultSet != null){
                resultSet.close();

            }
            if (preparedStatement != null) {
                preparedStatement.close();
   }
            if (connection !=null) {
                connection.close();
   }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void closeResources(PreparedStatement preparedStatement, Connection connection){
        try {
            if (preparedStatement !=null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
  }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public int[] insertImageAndProduct(Image image, Product product){
        return null;
    };
}
