/*
 * Copyright 2017 the original author or authors.
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

package org.springframework.hateoas.mvc;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import org.hamcrest.Matchers;
import org.junit.Test;

import org.springframework.hateoas.Identifiable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.SimpleResourceAssembler;
import org.springframework.hateoas.core.EvoInflectorRelProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Greg Turnquist
 */
public class SimpleIdentifiableResourceAssemblerTest {

	@Test
	public void convertingToResourceShouldWork() {

		TestResourceAssembler assembler = new TestResourceAssembler();
		Resource<Employee> resource = assembler.toResource(new Employee(1, "Frodo"));

		assertThat(resource.getContent().getName(), is("Frodo"));
	}

	@Test
	public void convertingToResourcesShouldWork() {

		TestResourceAssembler assembler = new TestResourceAssembler();
		Resources<Resource<Employee>> resources = assembler.toResources(Arrays.asList(new Employee(1, "Frodo")));

		assertThat(resources.getContent(), hasSize(1));
		assertThat(resources.getContent(), Matchers.<Resource<Employee>>contains(new Resource(new Employee(1, "Frodo"))));
		assertThat(resources.getLinks(), is(Matchers.<Link>empty()));

		assertThat(resources.getContent().iterator().next(), is(new Resource(new Employee(1, "Frodo"))));
	}

	@Test
	public void convertingToResourceWithCustomLinksShouldWork() {

		ResourceAssemblerWithCustomLink assembler = new ResourceAssemblerWithCustomLink();
		Resource<Employee> resource = assembler.toResource(new Employee(1, "Frodo"));

		assertThat(resource.getContent().getName(), is("Frodo"));
		assertThat(resource.getLinks(), hasSize(1));
		assertThat(resource.getLinks(), hasItem(new Link("/employees").withRel("employees")));
	}

	@Test
	public void convertingToResourcesWithCustomLinksShouldWork() {

		ResourceAssemblerWithCustomLink assembler = new ResourceAssemblerWithCustomLink();
		Resources<Resource<Employee>> resources = assembler.toResources(Arrays.asList(new Employee(1, "Frodo")));

		assertThat(resources.getContent(), hasSize(1));
		assertThat(resources.getContent(),
			Matchers.<Resource<Employee>>contains(new Resource(new Employee(1, "Frodo"), new Link("/employees").withRel("employees"))));
		assertThat(resources.getLinks(), is(Matchers.<Link>empty()));

		assertThat(resources.getContent().iterator().next(),
			is(new Resource(new Employee(1, "Frodo"), new Link("/employees").withRel("employees"))));
	}


	class TestResourceAssembler extends SimpleIdentifiableResourceAssembler<Employee> {

		public TestResourceAssembler() {
			super(EmployeeController.class, new EvoInflectorRelProvider());
		}
	}

	class ResourceAssemblerWithCustomLink extends SimpleResourceAssembler<Employee> {

		@Override
		protected void addLinks(Resource<Employee> resource) {
			resource.add(new Link("/employees").withRel("employees"));
		}
	}

	@Data
	static class Employee implements Identifiable<Integer> {
		private final Integer id;
		private final String name;
	}

	@Controller
	static class EmployeeController {

		final static Map<Integer, Employee> EMPLOYEES = new HashMap<Integer, Employee>() {{
			put(1, new Employee(1, "Frodo"));
			put(2, new Employee(2, "Bilbo"));
		}};

		@GetMapping("/employees")
		Collection<Employee> employees() {
			return EMPLOYEES.values();
		}

		@GetMapping("/employees/{id}")
		Employee employee(@PathVariable int id) {
			return EMPLOYEES.get(id);
		}
	}

}