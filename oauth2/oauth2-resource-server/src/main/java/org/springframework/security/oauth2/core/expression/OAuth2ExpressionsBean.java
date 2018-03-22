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
package org.springframework.security.oauth2.core.expression;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * A convenience wrapper for {@link OAuth2Expressions} to provide the {@link Authentication}
 * object to each method invocation
 *
 * @author Josh Cummings
 * @since 5.1
 */
public class OAuth2ExpressionsBean {
	private final OAuth2Expressions expressions;

	public OAuth2ExpressionsBean(OAuth2Expressions expressions) {
		this.expressions = expressions;
	}

	public Object attribute(String name) {
		return this.expressions.attribute(authentication(), name);
	}

	public boolean isOAuth() {
		return this.expressions.isOAuth(authentication());
	}

	protected Authentication authentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
}
