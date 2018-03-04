package edu.itu.cavabunga.core.repository;

import edu.itu.cavabunga.core.entity.Authentication;
import edu.itu.cavabunga.core.entity.authentication.AuthenticationType;
import edu.itu.cavabunga.core.factory.AuthenticationFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AuthenticationRepositoryTest {
    @Autowired
    AuthenticationRepository authenticationRepository;

    @Autowired
    AuthenticationFactory authenticationFactory;

    private Authentication testAuthenticaiton;

    @Before
    public void setUp(){
        Authentication testAuth = authenticationFactory.createAuthentication(AuthenticationType.ADMIN);
        testAuth.setAuthenticationKey("TEST_KEY");
        authenticationRepository.save(testAuth);
        this.testAuthenticaiton = authenticationRepository.findByAuthenticationKey("TEST_KEY");
    }

    @Test
    public void findByAuthenticationKeyTest(){
        assertEquals(this.testAuthenticaiton, authenticationRepository.findByAuthenticationKey("TEST_KEY"));
    }

    @Test
    public void findByAuthenticationTypeTest(){
        assertThat(authenticationRepository.findByAuthenticationType(AuthenticationType.ADMIN), contains(instanceOf(Authentication.class)));
    }
}
