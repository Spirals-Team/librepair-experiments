package ru.job4j.service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 08.03.2018.
 * @version $Id$.
 * @since 0.1.
 */
public class HibernateUtil {
    private static final Logger LOG = LogManager.getLogger(HibernateUtil.class);
    private static SessionFactory sessionFactory = sessionFactory();

    private HibernateUtil() {
    }

    private static SessionFactory sessionFactory() {
        Configuration configuration = new Configuration().configure();
        return configuration.buildSessionFactory();
    }
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
