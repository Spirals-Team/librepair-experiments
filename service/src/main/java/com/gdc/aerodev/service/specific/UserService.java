package com.gdc.aerodev.service.specific;

import com.gdc.aerodev.dao.exception.DaoException;
import com.gdc.aerodev.dao.specific.UserDao;
import com.gdc.aerodev.model.User;
import com.gdc.aerodev.service.GenericService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class UserService extends GenericService{

    private final UserDao dao;

    public UserService() {
        this.dao = new UserDao(new JdbcTemplate(), getTableName("user.table"));
    }

    public UserService(DataSource testDb, String tableName){
        this.dao = new UserDao(new JdbcTemplate(testDb), tableName);
    }

    /**
     * Inserts {@code User} into database configured by input parameters.
     *
     * @param userName name of new {@code User}
     * @param userPassword password of {@code User}
     * @param userEmail email of current {@code Project}
     * @return (0) {@param userId} of inserted {@code User}
     *         (1) or {@code null} in cause of problems
     */
    public Long createUser(String userName, String userPassword, String userEmail){
        if (userName.equals("") || userPassword.equals("") || userEmail.equals("")){
            return null;
        }
        if (isExistentName(userName)){
            return null;
            //TODO: plug in logger
//            return "User with name '" + userName + "' is already exists.";
        }
        String emailOwner = dao.existentEmail(userEmail);
        if (emailOwner != null){
            return null;
//            return "This email is already used by '" + emailOwner + "'.";
        }
        try {
            return dao.save(new User(userName, userPassword, userEmail));
//            return "Successful created user '" + userName + "' with id " + id + ".";
        } catch (DaoException e){
            return null;
        }
    }

    /**
     * Updates existent {@code User} chosen by {@param userId} with input parameters. If there is no need to
     * change some parameter, it should be left as empty ones.
     *
     * @param userId ID of updating {@code User}
     * @param userName new name of updating {@code User}
     * @param userPassword new password of updating {@code User}
     * @param userEmail new email for {@code User}
     * @param userLevel new user level
     * @return (0) {@param userId} of updated {@code User}
     *         (1) or {@code null} in cause of problems
     */
    public Long updateUser(Long userId, String userName, String userPassword, String userEmail, short userLevel){
        User user = dao.getById(userId);
        if (!userName.equals("")){
            if (isExistentName(userName)){
                return null;
//                return "User with name '" + userName + "' is already exists.";
            }
            user.setUserName(userName);
        } else if (userPassword.equals("") && userEmail.equals("") && user.getUserLevel() == userLevel){
            return null;
        }
        if (!userPassword.equals("")){
            user.setUserPassword(userPassword);
        }
        if (!userEmail.equals("")){
            String emailOwner = dao.existentEmail(userEmail);
            if (emailOwner != null){
                return null;
//                return "This email is already used by '" + emailOwner + "'.";
            }
            user.setUserEmail(userEmail);
        }
        user.setUserLevel(userLevel);
        try {
             return dao.save(user);
        } catch (DaoException e){
            return null;
        }
    }

    public UserDao getDao(){
        return dao;
    }

    private boolean isExistentName(String userName){
        return dao.getByName(userName) != null;
    }

}
