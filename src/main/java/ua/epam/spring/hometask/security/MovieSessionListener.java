package ua.epam.spring.hometask.security;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by Vladimir_Vysokomorny on 22-Jan-18.
 */
/*1st approach for changing session's timeout*/
public class MovieSessionListener implements HttpSessionListener{

    @Override
    public void sessionCreated(HttpSessionEvent event) {
//        event.getSession().setMaxInactiveInterval(20);//20 seconds
        event.getSession().setMaxInactiveInterval(20*60);//20 minutes
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        //session destroyed
    }
}
