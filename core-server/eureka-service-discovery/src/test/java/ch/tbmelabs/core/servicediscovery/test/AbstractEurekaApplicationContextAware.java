package ch.tbmelabs.core.servicediscovery.test;

import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;

import ch.tbmelabs.tv.core.servicediscovery.Application;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

@AutoConfigureMockMvc
@ContextConfiguration
@ActiveProfiles({ SpringApplicationProfile.TEST })
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { Application.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
@TestExecutionListeners({ ServletTestExecutionListener.class, DirtiesContextBeforeModesTestExecutionListener.class,
    DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class })
public class AbstractEurekaApplicationContextAware {
}