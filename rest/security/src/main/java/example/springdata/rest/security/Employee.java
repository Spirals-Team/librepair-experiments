/*
 * Copyright 2014-2018 the original author or authors.
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
package example.springdata.rest.security;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Domain object for an employee.
 *
 * @author Greg Turnquist
 */
@Data
@Entity
@RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee {

	private @Id @GeneratedValue Long id;
	private final String firstName, lastName, title;

	Employee() {
		this.firstName = null;
		this.lastName = null;
		this.title = null;
	}
}
