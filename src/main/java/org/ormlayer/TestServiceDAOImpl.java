package org.ormlayer;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.ormlayer.dto.TestEntity;

public class TestServiceDAOImpl {

    public TestEntity create(String msg) {
        TestEntity entity = new TestEntity(msg);

        Session session = HibernateUtils.sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();

        session.persist(entity);

        transaction.commit();
        session.close();

        return entity;
    }

    public TestEntity readTestEntity(int id) {
        TestEntity entity;

        Session session = HibernateUtils.sessionFactory.openSession();
        entity = (TestEntity) session.load(TestEntity.class, id);
        session.close();

        return entity;
    }

    public void deleteTestEntity(TestEntity entity) {
        Session session = HibernateUtils.sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();

        session.delete(entity);

        transaction.commit();
        session.close();
    }

}
