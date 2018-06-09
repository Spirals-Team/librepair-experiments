/*
 * Copyright (c) 2018  Tokenopoly Financial Technology Inc.
 * All rights not explicitly granted in the LICENSE attached to this project are hereby reserved.
 */

/**
 * The optional data access object classes live here.
 * By not using them you should be free from of any Spring Data Rest dependencies.
 * That said, if you're using Spring, these provide a trivial way to access the data from the two
 * entities: {@link com.tokenopoly.coinbridge.coinbase.commerce.model.Charge}
 * and {@link com.tokenopoly.coinbridge.coinbase.commerce.model.Payment}, via their corresponding
 * -Dao classes.
 * <p>To make them available automatically, you will need to configure Spring to look in this
 * package for repositories, for example via:
 * </p>
 * <p>
 * {@code @EnableJpaRepositories(basePackageClasses = {ChargeDao.class})}
 * </p>
 */
package com.tokenopoly.coinbridge.coinbase.commerce.dao;