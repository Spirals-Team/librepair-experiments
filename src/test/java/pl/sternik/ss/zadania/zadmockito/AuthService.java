package pl.sternik.ss.zadania.zadmockito;

public class AuthService {
    private UserService userService;


    public AuthService(UserService userService){this.userService = userService;}




public boolean checkCredentials(String name, String password){
        String user = userService.findByName(name);
        if (user != null && !"null".equals(user)){
            return true;
        }
            return false;
}
}
