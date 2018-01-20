package ua.com.company.store.service;

import ua.com.company.store.utils.JDBCConnectionPoolManager;
import ua.com.company.store.model.dao.daoAbstract.GenericDAO;
import ua.com.company.store.model.dao.exceptions.PersistException;
import ua.com.company.store.model.dao.factory.MySqlDaoFactory;
import ua.com.company.store.model.entity.Image;
import ua.com.company.store.model.entity.User;

/**
 * Created by Владислав on 13.12.2017.
 */
public class ImageService {
    private GenericDAO genericDAO;

    public ImageService(GenericDAO genericDAO) {
        this.genericDAO = genericDAO;

    }
    private static class Holder{
        static UserService INSTANCE;

        static {
            try {
                INSTANCE = new UserService(MySqlDaoFactory.getMysqlDaoFactory(JDBCConnectionPoolManager.getInstance().getJdbcConnectionPool()).getDao(User.class));
            } catch (PersistException e) {
                e.printStackTrace();
            }
        }
    }
    public int addImageForProduct(Image image){
        return genericDAO.insert(image);
    }


}
