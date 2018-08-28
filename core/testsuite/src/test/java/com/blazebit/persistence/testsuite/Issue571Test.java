package com.blazebit.persistence.testsuite;

import com.blazebit.persistence.CTE;
import com.blazebit.persistence.CriteriaBuilder;
import com.blazebit.persistence.impl.query.CustomSQLTypedQuery;
import com.blazebit.persistence.testsuite.tx.TxVoidWork;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class Issue571Test extends AbstractCoreTest {


    @Override
    protected Class<?>[] getEntityClasses() {
        return new Class<?>[] { Cte.class, MyEntity.class };
    }

    @Before
    public void setUp() throws Exception {
        transactional(new TxVoidWork() {
            @Override
            public void work(EntityManager em) {
                MyEntity myEntity = new MyEntity();
                myEntity.setAttribute("attr");
                em.persist(myEntity);
            }
        });
    }


    @Test
    public void testImplicitJoinAttributeOfCteAssociation() {
        transactional(new TxVoidWork() {
            @Override
            public void work(EntityManager em) {
                CriteriaBuilder<MyEntity> select = cbf.create(em, MyEntity.class)
                        .withRecursive(Cte.class)
                        .from(MyEntity.class, "a")
                        .bind("myEntity").select("a")
                        .bind("id").select("1")
                        .unionAll()
                        .from(MyEntity.class, "a")
                        .where("a.attribute").notIn().from(Cte.class, "b").select("b.myEntity.attribute").end()
                        .bind("myEntity").select("a")
                        .bind("id").select("1")
                        .end()
                        .from(Cte.class)
                        .select("myEntity");

                List<MyEntity> resultList = select
                        .getResultList();

                String sql = ((CustomSQLTypedQuery) select.getQuery()).getQuerySpecification().getSql();
                String cteBase = sql.substring(sql.indexOf("\nselect") + 1, sql.lastIndexOf("UNION ALL") - 1);
                String cteRecursivePart = sql.substring(sql.indexOf("UNION ALL")+10, sql.lastIndexOf("select")-3);
                String queryPart = sql.substring(sql.lastIndexOf("select"));

                assertEquals("Recursive base part should project 2 columns separated by a single comma",
                        cteBase.indexOf(","), cteBase.lastIndexOf(","));

                assertEquals("Recursive part should project 2 columns separated by a single comma",
                        cteRecursivePart.indexOf(","), cteRecursivePart.lastIndexOf(","));

                assertEquals("Final query should project 2 columns separated by a single comma",
                        queryPart.indexOf(","), queryPart.lastIndexOf(","));
            }
        });
    }

    @Test
    public void testBindingCteAssociationToEntity() {
        transactional(new TxVoidWork() {
            @Override
            public void work(EntityManager em) {
                CriteriaBuilder<MyEntity> select = cbf.create(em, MyEntity.class)
                        .withRecursive(Cte.class)
                        .from(MyEntity.class, "a")
                        .bind("myEntity").select("a")
                        .bind("id").select("1")
                        .unionAll()
                        .from(MyEntity.class, "a")
                        .where("a.attribute").eq("bogus")
                        .bind("myEntity").select("a")
                        .bind("id").select("1")
                        .end()
                        .from(Cte.class)
                        .select("myEntity");

                List<MyEntity> resultList = select
                        .getResultList();

                String sql = ((CustomSQLTypedQuery) select.getQuery()).getQuerySpecification().getSql();
                String cteBase = sql.substring(sql.indexOf("\nselect") + 1, sql.lastIndexOf("UNION ALL") - 1);
                String cteRecursivePart = sql.substring(sql.indexOf("UNION ALL")+10, sql.lastIndexOf("select")-3);
                String queryPart = sql.substring(sql.lastIndexOf("select"));

                assertEquals("Recursive base part should project 2 columns separated by a single comma",
                        cteBase.indexOf(","), cteBase.lastIndexOf(","));

                assertEquals("Recursive part should project 2 columns separated by a single comma",
                        cteRecursivePart.indexOf(","), cteRecursivePart.lastIndexOf(","));

                assertEquals("Final query should project 2 columns separated by a single comma",
                        queryPart.indexOf(","), queryPart.lastIndexOf(","));
            }
        });
    }

    @Test
    public void testBindingCteAssociationToEntityId() {
        transactional(new TxVoidWork() {
            @Override
            public void work(EntityManager em) {
                CriteriaBuilder<MyEntity> select = cbf.create(em, MyEntity.class)
                        .withRecursive(Cte.class)
                        .from(MyEntity.class, "a")
                        .bind("myEntity").select("a.id")
                        .bind("id").select("1")
                        .unionAll()
                        .from(MyEntity.class, "a")
                        .where("a.attribute").eq("bogus")
                        .bind("myEntity").select("a.id")
                        .bind("id").select("1")
                        .end()
                        .from(Cte.class)
                        .select("myEntity");

                List<MyEntity> resultList = select
                        .getResultList();

                String sql = ((CustomSQLTypedQuery) select.getQuery()).getQuerySpecification().getSql();
                String cteBase = sql.substring(sql.indexOf("\nselect") + 1, sql.lastIndexOf("UNION ALL") - 1);
                String cteRecursivePart = sql.substring(sql.indexOf("UNION ALL")+10, sql.lastIndexOf("select")-3);
                String queryPart = sql.substring(sql.lastIndexOf("select"));

                assertEquals("Recursive base part should project 2 columns separated by a single comma",
                        cteBase.indexOf(","), cteBase.lastIndexOf(","));

                assertEquals("Recursive part should project 2 columns separated by a single comma",
                        cteRecursivePart.indexOf(","), cteRecursivePart.lastIndexOf(","));

                assertEquals("Final query should project 2 columns separated by a single comma",
                        queryPart.indexOf(","), queryPart.lastIndexOf(","));

            }
        });
    }

    @CTE
    @Entity
    @IdClass(Cte.CteIdClass.class)
    public static class Cte {

        @Id private Long id;
        @Id @ManyToOne private MyEntity myEntity;

        public MyEntity getMyEntity() {
            return myEntity;
        }

        public void setMyEntity(MyEntity myEntity) {
            this.myEntity = myEntity;
        }

        public static class CteIdClass implements Serializable {

            Long id;
            Long myEntity;
        }

    }

    @Entity
    public static class MyEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        private String attribute;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getAttribute() {
            return attribute;
        }

        public void setAttribute(String attribute) {
            this.attribute = attribute;
        }

    }

}