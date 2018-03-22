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

import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;

/**
 * A contract that indicates that the implementer's state may be authorized by
 * an OAuth2 Access Token.
 *
 *
 * @author Josh Cummings
 * @since 5.1
 * @see org.springframework.security.oauth2.resourceserver.authentication.OAuth2ResourceAuthenticationToken
 */
public interface AccessTokenAuthorizeable {
	/**
	 * The {@link GrantedAuthority} that represents the OAuth2 Access Token that authorizes
	 * the implementation's state
	 *
	 * @return
	 */
	AccessTokenAuthority getAccessTokenAuthority();
}
