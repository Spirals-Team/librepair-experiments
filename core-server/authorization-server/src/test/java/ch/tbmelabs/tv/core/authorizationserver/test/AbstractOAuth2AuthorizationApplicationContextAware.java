package ch.tbmelabs.tv.core.authorizationserver.test;

import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;

import ch.tbmelabs.tv.core.authorizationserver.Application;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

@AutoConfigureMockMvc
@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({ SpringApplicationProfile.TEST, SpringApplicationProfile.NO_REDIS, SpringApplicationProfile.NO_MAIL })
@SpringBootTest(classes = { Application.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
@TestExecutionListeners({ ServletTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
    DirtiesContextBeforeModesTestExecutionListener.class, DependencyInjectionTestExecutionListener.class,
    WithSecurityContextTestExecutionListener.class })
public class AbstractOAuth2AuthorizationApplicationContextAware {
}