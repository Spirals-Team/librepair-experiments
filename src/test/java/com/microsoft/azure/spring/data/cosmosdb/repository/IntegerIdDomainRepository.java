/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.microsoft.azure.spring.data.cosmosdb.repository;

import com.microsoft.azure.spring.data.cosmosdb.domain.IntegerIdDomain;
import org.springframework.stereotype.Repository;

@Repository
public interface IntegerIdDomainRepository extends DocumentDbRepository<IntegerIdDomain, Integer> {

}
