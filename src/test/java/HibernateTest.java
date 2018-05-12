import model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import service.UserService;

import java.util.List;

/**
 * Created by mariano on 17/04/18.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/META-INF/spring-persistence-context.xml", "/META-INF/spring-services-context.xml" })
public class HibernateTest {

    @Autowired
    private UserService userService;


    @Test
    @Transactional
    public void testSave() {
        Assert.assertEquals(0,this.userService.count());
        User user = new User("20320231680", "Lalo", "Landa", "cuco 234", "bla@yahoo.com");
        this.userService.save(user);
        Assert.assertEquals(1, this.userService.count());
    }
    @Test
    @Transactional
    public void testDelete(){
        User user = new User("20320231680", "Lalo", "Landa", "cuco 234", "bla@yahoo.com");
        this.userService.save(user);
        Assert.assertEquals(1, this.userService.count());
        this.userService.delete(user);
        Assert.assertEquals(0, this.userService.count());
    }
    @Test
    @Transactional
    public void testUpdate(){
        User user = new User("20320231680", "Lalo", "Landa", "cuco 234", "bla@yahoo.com");
        this.userService.save(user);
        List<User> users = this.userService.retriveAll();
        Assert.assertEquals(user.getEmail(),users.get(0).getEmail());
        user.setEmail("lapandilla@yahoo.com");
        this.userService.update(user);
        users = this.userService.retriveAll();
        Assert.assertEquals("lapandilla@yahoo.com",users.get(0).getEmail());
    }
    @Test
    @Transactional
    public void testIdUser(){
        User lalo = new User("20320231680", "Lalo", "Landa", "cuco 234", "bla@yahoo.com");
        User marco = new User("20320231682", "Marco", "Landa", "cuco 234", "ha@yahoo.com");
        this.userService.save(lalo);
        this.userService.save(marco);
        User dbMarco=this.userService.findById("20320231682");
        Assert.assertEquals(marco,dbMarco);
        Assert.assertNotEquals(lalo,dbMarco);
    }

    @Transactional
    @Test
    public void testCount(){
        User lalo = new User("20320231680", "Lalo", "Landa", "cuco 234", "bla@yahoo.com");
        User marco = new User("20320231682", "Marco", "Landa", "cuco 234", "ha@yahoo.com");
        this.userService.save(lalo);
        Assert.assertEquals(1,this.userService.count());
        this.userService.save(marco);
        Assert.assertEquals(2,this.userService.count());
    }


}
