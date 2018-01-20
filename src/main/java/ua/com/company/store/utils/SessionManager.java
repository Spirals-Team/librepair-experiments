package ua.com.company.store.utils;

import org.apache.log4j.Logger;
import ua.com.company.store.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Владислав on 09.12.2017.
 */
public final class SessionManager {
    private Logger logger = Logger.getRootLogger();

    public SessionManager() {
    }
    private static final class Holder{
       static SessionManager INSTANCE = new SessionManager();
    }
    public static SessionManager getSessionManager(){
        return Holder.INSTANCE;
    }
    public boolean isUserLoggedIn(HttpSession session){
        return session.getAttribute("user") != null;
    }
    public void addUserToSession(HttpSession session, User user){
        logger.info("User has logged in " + user.getNickname());
      session.setAttribute("user",user);
    }
    public boolean failedSession(HttpSession session, HttpServletResponse resp) throws IOException {
        if (session == null || session.getAttribute("user") == null ){
            PrintWriter printWriter = resp.getWriter();
            printWriter.print("Session is null");
           return  false;
            }
            return true;
    }

    public User getUserFromSession(HttpSession session){
        return (User)session.getAttribute("user");
    }
    public void invalidateSession(HttpSession session, HttpServletRequest req){
        if (session != null && session.getAttribute("user") !=null){
            User user = (User) session.getAttribute("user");
            logger.info("User has logged out " + user.getNickname());
            session.invalidate();
        }
    }
}
