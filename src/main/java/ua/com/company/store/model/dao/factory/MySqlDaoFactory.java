package ua.com.company.store.model.dao.factory;

import org.apache.log4j.Logger;
import ua.com.company.store.model.dao.connection.JDBCConnectionPool;
import ua.com.company.store.model.dao.daoAbstract.GenericDAO;
import ua.com.company.store.model.dao.exceptions.PersistException;
import ua.com.company.store.model.dao.impl.*;
import ua.com.company.store.model.entity.Image;
import ua.com.company.store.model.entity.Order;
import ua.com.company.store.model.entity.Product;
import ua.com.company.store.model.entity.User;
import ua.com.company.store.model.entity.additional.ProductImage;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Владислав on 22.11.2017.
 */
public class MySqlDaoFactory implements FactoryDAO {
    private Map<Class, CreatorDao> creators;
    private Logger logger = Logger.getRootLogger();
    private static JDBCConnectionPool jdbcConnectionPool;
    private static MySqlDaoFactory mySqlDaoFactory;

    @Override
    public GenericDAO getDao(Class daoClass) throws PersistException {
        CreatorDao creatorDao = creators.get(daoClass);
        if (creatorDao == null) {
            logger.info("Dao creator is null");
            throw new PersistException("Dao creator is null");
        }
        return creatorDao.create();
    }

    public static synchronized MySqlDaoFactory getMysqlDaoFactory(JDBCConnectionPool jdbcConnectionPool){
        if (mySqlDaoFactory == null){
            mySqlDaoFactory = new MySqlDaoFactory(jdbcConnectionPool);
            return mySqlDaoFactory;
        }else {
            return mySqlDaoFactory;
        }
    }

    private MySqlDaoFactory(JDBCConnectionPool jdbcConnectionPool) {
        logger.info("Mysql dao factory constur");
        this.jdbcConnectionPool = jdbcConnectionPool;
        creators = new HashMap<>();
        creators.put(User.class, new CreatorDao() {
            @Override
            public GenericDAO create() {
                return new UserDAO(jdbcConnectionPool);
            }
        });
        creators.put(Product.class, new CreatorDao() {
            @Override
            public GenericDAO create() {
                return new ProductDAO(jdbcConnectionPool);
            }
        });

        creators.put(Image.class, new CreatorDao() {
            @Override
            public GenericDAO create() {
                return new ImageDAO(jdbcConnectionPool);
            }
        });
        creators.put(Order.class, new CreatorDao() {
            @Override
            public GenericDAO create() {
                return new OrderDAO(jdbcConnectionPool);
            }
        });
        creators.put(ProductImage.class, new CreatorDao() {
            @Override
            public GenericDAO create() {
                return new ProductImageDAO(jdbcConnectionPool);
            }
        });
    }
}
