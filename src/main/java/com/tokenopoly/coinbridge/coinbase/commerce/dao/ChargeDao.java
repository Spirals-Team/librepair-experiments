/*
 * Copyright (c) 2018  Tokenopoly Financial Technology Inc.
 * All rights not explicitly granted in the LICENSE attached to this project are hereby reserved.
 */

package com.tokenopoly.coinbridge.coinbase.commerce.dao;

import com.tokenopoly.coinbridge.coinbase.commerce.model.Charge;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 */
@SuppressWarnings("WeakerAccess")
public interface ChargeDao extends JpaRepository<Charge, String> {

}
