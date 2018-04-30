import model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
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
    public void testSave() {
        User user = new User("20320231680", "Lalo","Landa", "cuco 234", "bla@yahoo.com");
        userService.save(user);
        //List<User> users = this.userService.retriveAll();
        //Assert.assertEquals(1, users.size());
    }

}
