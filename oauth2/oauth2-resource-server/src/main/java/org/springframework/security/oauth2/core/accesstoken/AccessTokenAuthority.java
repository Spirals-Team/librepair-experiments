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

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.resourceserver.authentication.OAuth2ResourceAuthenticationToken;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * A {@link GrantedAuthority} that may be associated to an {@link OAuth2ResourceAuthenticationToken}.
 *
 * @author Josh Cummings
 * @since 5.1
 * @see OAuth2ResourceAuthenticationToken
 */
public class AccessTokenAuthority implements GrantedAuthority {
	public static final AccessTokenAuthority NO_AUTHORITY = new AccessTokenAuthority(Collections.emptyMap());

	private final Map<String, Object> attributes;
	private final String authority;

	/**
	 *
	 * @param attributes - A set of attributes as derived from, say, a JWT or from consulting an authorization server
	 *
	 */
	public AccessTokenAuthority(Map<String, Object> attributes) {
		this.authority = "ROLE_USER";
		this.attributes = attributes;
	}

	/**
	 * Retrieve a claim by its {@see name}
	 * @param name
	 * @return
	 */
	public Optional<Object> attribute(String name) {
		return Optional.ofNullable(this.attributes.get(name));
	}

	@Override
	public String getAuthority() {
		return this.authority;
	}
}
