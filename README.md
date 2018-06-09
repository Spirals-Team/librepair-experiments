[![Build Status](https://travis-ci.org/tokenopoly/coinbase-commerce-core-jpa.svg?branch=develop)](https://travis-ci.org/tokenopoly/coinbase-commerce-core-jpa)  [![codecov](https://codecov.io/gh/tokenopoly/coinbase-commerce-core-jpa/branch/develop/graph/badge.svg)](https://codecov.io/gh/tokenopoly/coinbase-commerce-core-jpa)


# coinbase-commerce-core

This module provides a Java domain model for
[Coinbase Commerce](https://commerce.coinbase.com/).

It has been tested and handles the webhook callbacks as well as other interactions with the service's API.

That said, it is being shared from an existing project and its current version keeps those trappings - notably use of Spring Boot.  But, those dependencies are not added transitively, allowing an a la carte approach fo those who choose to leverage this package.  If, for example, you choose to persist the entities with JPA, then you will want to include the JPA dependencies to do so - whether that be via a Spring Boot starter, via Hibernate, or via a different JPA implementaion is your choice. 

The code does rely on annotations and, in particular JSON mapping annotations from Jackson.

There are hibernate mapping annotations as well, but those can be easily overridden via .hbm.xml mapping files.

If you do a full independent mapping that way, please feel free to contribute that back to the project.

###  Sample usage in a Spring project

* First expose the CoinbaseUtils as a Spring bean via the `@Component` annotation.

```java
package com.tokenopoly.coinbridge.coinbase.spring;

import com.tokenopoly.coinbridge.coinbase.commerce.util.CoinbaseUtils;
import com.tokenopoly.coinbridge.config.AppConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 *
 */
@Component
public class CoinbaseComponent extends CoinbaseUtils {

    @Autowired
    public CoinbaseComponent(final AppConfig appConfig) throws NoSuchAlgorithmException, InvalidKeyException {
        super(appConfig.getCoinbase().getCommerce().getSharedSecret());
    }
    
}
```

* Then implement a webhook handler that extracts the `Charge` payload object and process as needed.

```java
@RestController
@RequestMapping("/coinbase/handle-webhook")
@Slf4j
public class CoinbaseController {

    private final AppConfig appConfig;
    private final ObjectMapper objectMapper;
    private final CoinbaseUtils coinbaseUtil;

    @Autowired
    public CoinbaseController(final AppConfig appConfig, final ObjectMapper objectMapper,
                              final CoinbaseUtils coinbase) {
        this.appConfig = appConfig;
        this.objectMapper = objectMapper;
        this.coinbaseUtil = coinbase;
    }

    @PostMapping
    public void handleEvent(@RequestHeader("X-CC-Webhook-Signature") final String signature,
                            @RequestBody final String payload) {

        // Immediately validate the signature; only process valid requests.
        if (!coinbaseUtil.isValidSignature(signature, payload)) {
            log.error("Signature in X-CC-Webhook-Signature Header does not match computed signature of payload.");
            log.info("body: {}", payload);
            return;
        }

        try {
            final Webhook invocation = objectMapper.readValue(payload, Webhook.class);
            final Event event = invocation.getEvent();
            if (event == null) {
                log.error("Null event! [webhook: {}]", invocation.getId());
                return;
            }
            final Charge charge = event.getData();
            
            // Implement your logic here ...

        } catch (IOException e) {
            log.warn("body: {}", payload);
            log.error("Unable to deserialize request body as Webhook.", e);

        } 
    }

}

```

### Setting up the database

By default the package uses [Flyway](https://flywaydb.org/) to initialize a database structure for the domain objects.  You are, however, free to use whatever pesistence back end you please, including none at all.  If you use a RDBMS, then you again can opt for the default (Flyway) or use any other mechanism that suits your needs and your fancy.

If you choose to map the objects as-is, you will find corresponding `.sql` files in the `src/main/resources/db/migration` folder - the default for Flyway.  Note that these are in the source code but are excluded from the `.jar` file archive to avoid hassles from potential conflicts with a project that uses this one and also uses Flyway for DB migrations.

The tests include an example of how to run Flyway "automagically" via Spring at application startup.  You are free to do so, but for production we prefer the approach of explicitly migrating the DB and have included the [Flyway Maven Plugin](https://flywaydb.org/documentation/maven/) as a convenient means of doing so.

