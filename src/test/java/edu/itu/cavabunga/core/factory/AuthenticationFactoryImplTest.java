package edu.itu.cavabunga.core.factory;

import edu.itu.cavabunga.core.entity.Authentication;
import edu.itu.cavabunga.core.entity.authentication.AuthenticationType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthenticationFactoryImplTest {
    @Autowired
    AuthenticationFactory authenticationFactory;

    @Test
    public void createAuthentication(){
        assertThat(authenticationFactory.createAuthentication(AuthenticationType.ADMIN), instanceOf(Authentication.class));
        assertThat(authenticationFactory.createAuthentication(AuthenticationType.CLIENT), instanceOf(Authentication.class));
        assertThat(authenticationFactory.createAuthentication(AuthenticationType.SDG_CLIENT), instanceOf(Authentication.class));
    }
}
