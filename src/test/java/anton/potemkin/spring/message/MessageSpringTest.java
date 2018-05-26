package anton.potemkin.spring.message;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Anton Potemkin on 26/05/2018.
 */
@ContextConfiguration("classpath:spring/message-bean.xml")
@RunWith(SpringRunner.class)
public class MessageSpringTest {

    @Autowired
    @Qualifier("fromBeanMessage")
    private Message message;

    @Autowired
    @Qualifier("BeanMessageSingleton")
    private Message messageSingletonFirst;

    @Autowired
    @Qualifier("BeanMessageSingleton")
    private Message messageSingletonSecond;

    @Autowired
    @Qualifier("BeanMessagePrototype")
    private Message messagePrototypeFirst;

    @Autowired
    @Qualifier("BeanMessagePrototype")
    private Message messagePrototypeSecond;

    @Test
    public void getMessageTest() {
        Assert.assertEquals(message.getMessage(), "This is message from simple bean.");
    }

    @Test
    public void singltonTest() {
        messageSingletonFirst.setMessage("Singleton bean");
        Assert.assertEquals(messageSingletonFirst.getMessage(), "Singleton bean");
        Assert.assertEquals(messageSingletonSecond.getMessage(), "Singleton bean");
    }

    @Test
    public void prototypeTest() {
        messagePrototypeFirst.setMessage("Prototype bean");
        Assert.assertEquals(messagePrototypeFirst.getMessage(), "Prototype bean");
        Assert.assertNull(messagePrototypeSecond.getMessage());
    }
}
