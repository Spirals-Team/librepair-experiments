package ua.com.company.store.service;

import org.apache.log4j.Logger;
import ua.com.company.store.utils.JDBCConnectionPoolManager;
import ua.com.company.store.model.dao.daoAbstract.AbstractDao;
import ua.com.company.store.model.dao.daoAbstract.GenericDAO;
import ua.com.company.store.model.dao.exceptions.PersistException;
import ua.com.company.store.model.dao.factory.MySqlDaoFactory;
import ua.com.company.store.model.entity.User;

import java.util.List;

/**
 * Created by Владислав on 09.12.2017.
 */
public class UserService {
    private Logger logger = Logger.getRootLogger();
    private GenericDAO genericDAO;

    public UserService(GenericDAO genericDAO) {
        this.genericDAO = genericDAO;
        logger.info("Created User Service");
    }

    private static class Holder {
        static UserService INSTANCE;

        static {
            try {
                INSTANCE = new UserService(MySqlDaoFactory.getMysqlDaoFactory(JDBCConnectionPoolManager.getInstance().getJdbcConnectionPool()).getDao(User.class));
            } catch (PersistException e) {
                e.printStackTrace();
            }
        }
    }

    public static UserService getInstance() {
        return Holder.INSTANCE;
    }

    public int addUser(User user) {
        return genericDAO.insert(user);
    }

    public boolean validationUserOnBeforeExist(User user) {
        List<User> userList = genericDAO.getAll();
        for (User userFromDB : userList) {
            if (userFromDB.equals(user)) {
                return true;
            }
        }
        return false;
    }

    public List<User> getAllUsers() {
        return genericDAO.getAll();
    }

    public User getById(int id) {
        AbstractDao abstractDao = (AbstractDao) genericDAO;
        return (User) abstractDao.getById(id);
    }

    public void deleteUser(User user) {
        AbstractDao abstractDao = (AbstractDao) genericDAO;
        abstractDao.delete(user);
    }

    public User getUserByNickName(String nickname) {
        return (User) genericDAO.getByParameter(nickname);
    }

    public void markUserAsDefaulter(User user) {
        AbstractDao abstractDao = (AbstractDao) genericDAO;
        abstractDao.markUserAsDefaulter(user);
    }

}
