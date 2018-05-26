package anton.potemkin.spring.message;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Anton Potemkin on 26/05/2018.
 */
public class MessageClassPathXmlApplicationContextTest {
    private static Message message;

    @BeforeClass
    public static void beforeClass() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/message-bean.xml");
        message = (Message) context.getBean("fromBeanMessage");
    }

    @Test
    public void getMessageTest() {
        Assert.assertEquals(message.getMessage(), "This is message from simple bean.");
    }


}
