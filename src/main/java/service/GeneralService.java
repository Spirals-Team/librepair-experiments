package service;

/**
 * Created by mariano on 14/04/18.
 */
public class GeneralService {

    private UserService userService;


    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
