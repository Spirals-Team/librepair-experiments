package boot;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import ru.curriculum.domain.helper.UserTestFactory;

@RunWith(SpringRunner.class)
@SpringBootTest
//@ComponentScan(value = {"ru.curriculum.domain.helper"})
public abstract class IntegrationBoot extends Assert {
}
