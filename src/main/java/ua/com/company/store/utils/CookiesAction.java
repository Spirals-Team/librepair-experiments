package ua.com.company.store.utils;

import org.apache.log4j.Logger;
import ua.com.company.store.model.entity.User;
import ua.com.company.store.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Владислав on 26.12.2017.
 */
public class CookiesAction {
    private static Logger logger = Logger.getRootLogger();

    public static void setCookieUserInform(HttpServletResponse response, Object userInform) {
        User user = (User) userInform;
        Cookie cookieUser = new Cookie(user.getNickname(), user.getPassword());
        cookieUser.setMaxAge(60 * 60 * 60);
        response.addCookie(cookieUser);
        logger.info("Successful added in cookie user " + user.getNickname());
        String locale = response.getLocale().toString();
        Cookie cookieLoc = new Cookie("locale", locale);
        response.addCookie(cookieLoc);
    }
    public static List<User> getUserFromCookie(HttpServletRequest request, UserService userService){
        List<User> userList = new ArrayList<>();
        Cookie[] cookies = request.getCookies();
        if(cookies.length !=0){
        for (int i=0; i<cookies.length; i++){
            Cookie cookie = cookies[i];
            User user = userService.getUserByNickName(cookie.getName());
            if (user != null){
                if (Objects.equals(user.getPassword(), cookie.getValue())){
                    userList.add(user);
                }
            }

        }
        }
        return userList;

    }
    public static void removeCookie(HttpServletRequest request,HttpServletResponse response ){
        Cookie[] cookies = request.getCookies();
        for (int i=0;i<cookies.length;i++){
            Cookie cookies1 = cookies[0];
            cookies1.setMaxAge(0);
            response.addCookie(cookies1);
        }
    }

}
