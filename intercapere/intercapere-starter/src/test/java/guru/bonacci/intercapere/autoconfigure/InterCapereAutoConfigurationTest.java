package guru.bonacci.intercapere.autoconfigure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.boot.test.util.EnvironmentTestUtils;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import guru.bonacci.intercapere.InterCapereService;
import guru.bonacci.intercapere.SurroundService;

public class InterCapereAutoConfigurationTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Rule
	public OutputCapture output = new OutputCapture();

	private ConfigurableApplicationContext context;

	@After
	public void closeContext() {
		if (this.context != null) {
			this.context.close();
		}
	}

	@Test
	public void defaultServiceIsAutoConfigured() {
		load(EmptyConfiguration.class, "inter.capere.prefix=Test", "inter.capere.suffix=**");
		InterCapereService bean = this.context.getBean(InterCapereService.class);
		bean.take("World");
		this.output.expect(containsString("TestWorld**"));
	}

	@Test
	public void defaultServiceBacksOff() {
		load(UserConfiguration.class);
		InterCapereService bean = this.context.getBean(InterCapereService.class);
		bean.take("Works");
		this.output.expect(containsString("MineWorks*"));
	}

	@Test
	public void defaultServiceIsNotAutoConfiguredIfPrefixIsMissing() {
		load(EmptyConfiguration.class);
		assertThat(this.context.getBeansOfType(InterCapereService.class)).isEmpty();
	}

	private void load(Class<?> config, String... environment) {
		AnnotationConfigApplicationContext ctx =
				new AnnotationConfigApplicationContext();
		ctx.register(config);
		EnvironmentTestUtils.addEnvironment(ctx, environment);
		ctx.refresh();
		this.context = ctx;
	}

	@Configuration
	@ImportAutoConfiguration(InterCapereAutoConfiguration.class)
	static class EmptyConfiguration {

	}

	@Configuration
	@Import(EmptyConfiguration.class)
	static class UserConfiguration {

		@Bean
		public InterCapereService myHelloService() {
			return new SurroundService("Mine", "*");
		}
	}

}