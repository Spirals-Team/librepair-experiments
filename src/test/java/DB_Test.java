import junit.framework.Assert;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.ormlayer.HibernateUtils;
import org.ormlayer.TestServiceDAOImpl;
import org.ormlayer.dto.TestEntity;

@RunWith(JUnit4.class )
public class DB_Test {
    @BeforeClass
    public static void initHibirnate() {
        HibernateUtils.ormInit();

    }

    @Test(timeout = 3000)
    public void createAndDeleteTestEntityInDb_Test(){
        TestServiceDAOImpl serviceDAO = new TestServiceDAOImpl();
        TestEntity entity = serviceDAO.create("TEST");

        Assert.assertTrue("Entity was successfully created", entity.getEmployeeId()!=null || entity.getEmployeeId()!=0);

        serviceDAO.deleteTestEntity(entity);


//        Assert.assertTrue(newEntity==null);
    }

    @AfterClass
    public static void destroyHibernate(){
        HibernateUtils.destroy();
    }

}
