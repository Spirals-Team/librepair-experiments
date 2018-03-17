package ru.job4j.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ru.job4j.model.Car;
import ru.job4j.model.Engine;
import ru.job4j.model.Gearbox;
import ru.job4j.model.Transmission;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 12.03.2018.
 * @version $Id$.
 * @since 0.1.
 */
public class HibernateUtilTest {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    @Before
    public void start() {
      setTransmission();
      setEngine();
      setGearbox();
      setCar();
    }
    @Ignore
    @Test
    public void selectItemsWithOutCar() {
        Session session = this.sessionFactory.openSession();
        session.beginTransaction();
       session.createQuery("select g from Engine g left join fetch  g.car c where c.id is null")
               .list().forEach(System.out::println);
       session.createQuery("select g from Transmission g left join fetch  g.car c where c.id is null")
               .list().forEach(System.out::println);
       session.createQuery("select g from Gearbox g left join fetch  g.car c where c.id is null")
               .list().forEach(System.out::println);
        session.getTransaction().commit();
        session.close();
    }

    public void setTransmission() {
        Transmission transmission = new Transmission("FF");
        Transmission transmission2 = new Transmission("FR");
        Transmission transmission3 = new Transmission("4x4");
        Session session = this.sessionFactory.openSession();
        session.beginTransaction();
        session.save(transmission);
        session.save(transmission2);
        session.save(transmission3);
        session.getTransaction().commit();
        session.close();
    }
    public void setEngine() {
        Engine gaz = new Engine("gaz", 1800);
        Engine disel = new Engine("disel", 1400);
        Session session = this.sessionFactory.openSession();
        session.beginTransaction();
        session.save(gaz);
        session.save(disel);
        session.getTransaction().commit();
        session.close();
    }
    public void setGearbox() {
        Gearbox gearbox = new Gearbox("auto");
        Gearbox gearbox1 = new Gearbox("manual");
        Session session = this.sessionFactory.openSession();
        session.beginTransaction();
        session.save(gearbox);
        session.save(gearbox1);
        session.getTransaction().commit();
        session.close();
    }
    public void setCar() {
        Car car = new Car();
        car.setEngine(new Engine(1));
        car.setTransmission(new Transmission(1));
        car.setGearbox(new Gearbox(1));
        car.setName("lada");
        Session session = this.sessionFactory.openSession();
        session.beginTransaction();
        session.save(car);
        session.getTransaction().commit();
        session.close();
    }
}