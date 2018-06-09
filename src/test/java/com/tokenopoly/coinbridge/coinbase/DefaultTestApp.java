/*
 * Copyright (c) 2018  Tokenopoly Financial Technology Inc.
 * All rights not explicitly granted in the LICENSE attached to this project are hereby reserved.
 */

package com.tokenopoly.coinbridge.coinbase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

/**
 *
 */
@SpringBootApplication
@Profile({"test"})
public class DefaultTestApp {

    public static void main(String[] args) {
        SpringApplication.run(DefaultTestApp.class, args);
    }

}
