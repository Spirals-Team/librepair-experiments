/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.security.oauth2.core.accesstoken;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.expression.DefaultOAuth2Expressions;
import org.springframework.security.oauth2.core.expression.OAuth2ExpressionsBean;
import org.springframework.security.oauth2.resourceserver.authentication.OAuth2ResourceAuthenticationToken;
import org.springframework.security.test.context.annotation.SecurityTestExecutionListeners;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author Josh Cummings
 * @since 5.1
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SecurityTestExecutionListeners
public class DefaultOAuth2ExpressionsTests {

	@EnableGlobalMethodSecurity(prePostEnabled = true)
	public static class OAuth2ExpressionConfig {
		/**
		 * This would ultimately be supplied by an OAuth2 configurer
		 */
		@Bean
		public OAuth2ExpressionsBean oauth2() {
			return new OAuth2ExpressionsBean(new DefaultOAuth2Expressions());
		}
	}

	public static class MethodSecurityService {
		@PreAuthorize("@oauth2.attribute('issuer') matches '.*springframework.org'")
		public String needsIssuerEndingInSpecificDomain() {
			return "foo";
		}

		@PreAuthorize("@oauth2.attribute('scope') == 'permission'")
		public String needsExactlyPermissionScope() {
			return "foo";
		}

		@PreAuthorize("@oauth2.attribute('scope') matches 'permission.(read|write)'")
		public String needsOneOfTwoScopes()  { return "foo"; }
	}

	private AnnotationConfigApplicationContext context(Class<?>... classesToRegister) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(classesToRegister);
		context.refresh();

		return context;
	}

	private Authentication oauth2Authenticate(Map<String, Object> claims) {
		Authentication authentication =
			new OAuth2ResourceAuthenticationToken(
					"token",
					Arrays.asList(new AccessTokenAuthority(claims)));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		return authentication;
	}

	@Test
	@WithMockUser
	public void evaluateWhenNotOAuthAuthenticatedThenDenies() {
		ApplicationContext context = context(OAuth2ExpressionConfig.class, MethodSecurityService.class);

		MethodSecurityService service = context.getBean(MethodSecurityService.class);

		assertThatThrownBy(() -> service.needsExactlyPermissionScope()).isInstanceOf(AccessDeniedException.class);
	}

	@Test
	public void evaluateWhenIssuerMatchesThenAllows() {
		ApplicationContext context = context(OAuth2ExpressionConfig.class, MethodSecurityService.class);

		Map<String, Object> attributes = new HashMap<>();
		attributes.put("issuer", "www.springframework.org");

		oauth2Authenticate(attributes);

		MethodSecurityService service = context.getBean(MethodSecurityService.class);

		service.needsIssuerEndingInSpecificDomain();
	}


	@Test
	public void evaluateWhenHasRequiredScopeClaimThenPasses() {
		ApplicationContext context = context(OAuth2ExpressionConfig.class, MethodSecurityService.class);

		Map<String, Object> attributes = new HashMap<>();
		attributes.put("scope", "permission");

		oauth2Authenticate(attributes);

		MethodSecurityService service = context.getBean(MethodSecurityService.class);

		service.needsExactlyPermissionScope();
	}

	@Test
	public void evaluateWhenMissingRequiredScopeClaimThenDenies() {
		ApplicationContext context = context(OAuth2ExpressionConfig.class, MethodSecurityService.class);

		oauth2Authenticate(Collections.emptyMap());

		MethodSecurityService service = context.getBean(MethodSecurityService.class);

		assertThatThrownBy(() -> service.needsExactlyPermissionScope()).isInstanceOf(AccessDeniedException.class);
	}

	@Test
	public void evaluateWhenConfiguredForAnyScopeButRequestDoesNotHaveAnyThenDenies() {
		ApplicationContext context = context(OAuth2ExpressionConfig.class, MethodSecurityService.class);

		Map<String, Object> attributes = new HashMap<>();
		attributes.put("scope", "permission.dance");

		oauth2Authenticate(attributes);
		MethodSecurityService service = context.getBean(MethodSecurityService.class);

		assertThatThrownBy(() -> service.needsOneOfTwoScopes()).isInstanceOf(AccessDeniedException.class);
	}


	@Test
	public void evaluateWhenConfiguredForAnyScopeAndRequestHasOneThenAllows() {
		ApplicationContext context = context(OAuth2ExpressionConfig.class, MethodSecurityService.class);

		Map<String, Object> attributes = new HashMap<>();
		attributes.put("scope", "permission.read");

		oauth2Authenticate(attributes);

		MethodSecurityService service = context.getBean(MethodSecurityService.class);

		service.needsOneOfTwoScopes();
	}
}
