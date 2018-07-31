package tech.spring.structure.auth.config;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = { "structure.whitelist=0:0:0:0:0:0:0:1,0:0:0:0:0:0:0:2" })
public class WebSecurityConfigTest {

    @Value("${structure.whitelist:}")
    private String[] whitelist;

    @Test
    public void testWhitelistProperty() {
        assertTrue("Whitelist did not have any IP addresses!", whitelist.length > 0);
    }

}
