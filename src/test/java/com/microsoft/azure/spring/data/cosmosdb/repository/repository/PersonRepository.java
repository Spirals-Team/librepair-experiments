/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.microsoft.azure.spring.data.cosmosdb.repository.repository;

import com.microsoft.azure.spring.data.cosmosdb.domain.Person;
import com.microsoft.azure.spring.data.cosmosdb.repository.DocumentDbRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends DocumentDbRepository<Person, String> {

    List<Person> findByFirstNameContaining(String firstName);

    List<Person> findByFirstNameContainingAndLastNameContaining(String firstName, String lastName);

    List<Person> findByFirstNameEndsWith(String firstName);
}
