package livelessons.custom;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Optional;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
@Configuration
class XAuthenticationConfiguration {

		@Bean
		XAuthTokenFilter tokenFilter(UserDetailsService uds) {
				return new XAuthTokenFilter(uds);
		}

		@Configuration
		@EnableWebSecurity
		public static class WebConfig extends WebSecurityConfigurerAdapter {

				private final Optional<XAuthTokenFilter> filter;

				WebConfig(Optional<XAuthTokenFilter> filter) {
						this.filter = filter;
				}

				@Bean
				@Override
				public AuthenticationManager authenticationManagerBean() throws Exception {
						return super.authenticationManagerBean();
				}

				@Override
				protected void configure(HttpSecurity http) throws Exception {
						http
							.httpBasic()
							.and()
							.csrf().disable();
						this.filter.ifPresent(f -> http.addFilterBefore(f, UsernamePasswordAuthenticationFilter.class));
				}
		}

}
