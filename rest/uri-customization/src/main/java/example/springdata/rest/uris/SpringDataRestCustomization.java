/*
 * Copyright 2015-2018 the original author or authors.
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
package example.springdata.rest.uris;

import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.stereotype.Component;

/**
 * Spring Data {@link RepositoryRestConfiguration} to customize the identifier mapping for {@link User}s.
 *
 * @author Oliver Gierke
 */
@Component
public class SpringDataRestCustomization extends RepositoryRestConfigurerAdapter {

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter#configureRepositoryRestConfiguration(org.springframework.data.rest.core.config.RepositoryRestConfiguration)
	 */
	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {

		config.withEntityLookup().//
				forRepository(UserRepository.class, User::getUsername, UserRepository::findByUsername);
	}
}
