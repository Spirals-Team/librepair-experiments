package persistence.services;

public class GeneralService {

    private UserService userService;

    public void setUserService(final UserService userService){
        this.userService = userService;
    }

    public UserService getUserService(){
        return this.userService;
    }
}
