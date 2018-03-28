package com.d4dl.hellofib.helloworld;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Repository that implements an interface which will cause Spring Boot
 * to generate REST resources and publish them as endpoints.
 * @see HelloWorld for its attributes
 */
@RepositoryRestResource
public interface HelloWorldRepository extends CrudRepository<HelloWorld, String> {
}
