package ru.job4j.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Singleton Hibernate instance.
 */
public class HibernateUtil {

    private static final Logger LOG =
        LoggerFactory.getLogger(HibernateUtil.class);
    private static final SessionFactory SESSION_FACTORY = buildSessionFactory();

    private HibernateUtil() {
    }

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration();
            return configuration.configure().buildSessionFactory();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new RuntimeException(
                "There was an error building the factory");
        }
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }
}
