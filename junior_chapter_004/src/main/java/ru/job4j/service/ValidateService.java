package ru.job4j.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.model.User;
import ru.job4j.store.DbStore;
import ru.job4j.store.UserStore;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Vladimir Lembikov (cympak2009@mail.ru) on 13.06.2018.
 * @version 1.0.
 * @since 0.1.
 */
public class ValidateService implements Validate {
    private static final ValidateService INSTANCE = new ValidateService();
    private static final Logger LOG = LoggerFactory.getLogger(ValidateService.class);
    private final UserStore logic = DbStore.getInstance();

    public static ValidateService getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean addValidate(User user) {
        boolean resault = DbStore.getInstance().findByLoginStore(user.getLogin());
        if (!resault) {
            logic.addStore(user);
        }
        return resault;
    }

    @Override
    public boolean updateValidate(User user) {
        boolean resault = false;
        if (findByIdValidate(user.getId())) {
            logic.updateStore(user);
            resault = true;
        }
        return resault;
    }

    @Override
    public boolean deleteValidate(int id) {
        boolean resault = false;
        if (findByIdValidate(id)) {
            logic.deleteStore(id);
            resault = true;
        }
        return resault;
    }

    @Override
    public List<User> findAllValidate() {
        List<User> validateUserList = new CopyOnWriteArrayList<>();
        for (User user : logic.findAllStore()) {
            if (user != null) {
                validateUserList.add(user);
            }
        }
        return validateUserList;
    }

    @Override
    public boolean findByIdValidate(int id) {
        return logic.findByIdStore(id) == null ? false : true;
    }

    @Override
    public boolean isCredentional(String login, String password) {
        return logic.isCredentional(login, password);
    }

    @Override
    public void close() {
        logic.closePoolConnections();
    }


}
