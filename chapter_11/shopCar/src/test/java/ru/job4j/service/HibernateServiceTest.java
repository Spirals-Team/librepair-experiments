package ru.job4j.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.dao.UserController;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 16.03.2018.
 * @version $Id$.
 * @since 0.1.
 */
public class HibernateServiceTest {
    SessionFactory sessionFactory = HibernateService.getSessionFactoty();
    Session session;
    UserController userController;
    @Before
    public void start() {
        //session = HibernateService.getSession();
        userController = new UserController();
    }

    @Test
    public void name() {
         boolean d = userController.delete(1);
        System.out.println(d);
         userController.getAll().forEach(System.out::println);


//       User user =  new User("tesr", "testEmail", "123" );
//       user.setId(1);
//       userController.delete(2);
//       userController.getAll().forEach(System.out::println);
//        session = sessionFactory.openSession();
//        session.beginTransaction();
//        session.createQuery("from User ").list().forEach(System.out::println);
    }
}