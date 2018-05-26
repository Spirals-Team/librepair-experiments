package anton.potemkin.spring.message;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by Anton Potemkin on 26/05/2018.
 */
public class MessageXmlBeanFactoryTest {
    private static Message message;

    @BeforeClass
    public static void beforeClass() {
        XmlBeanFactory xmlBeanFactory = new XmlBeanFactory(new ClassPathResource("spring/message-bean.xml"));
        message = (Message) xmlBeanFactory.getBean("fromBeanMessage");
    }

    @Test
    public void getMessageTest() {
        Assert.assertEquals(message.getMessage(), "This is message from simple bean.");
    }

}
