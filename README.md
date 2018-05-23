# Exchange Rates Notification [![Build Status](https://travis-ci.org/siddharthgoel88/xrates.svg)](https://travis-ci.org/siddharthgoel88/xrates)

## Introduction
This project is built to notify a user when the exchange rates of a particular currency to another currency for a particular exchange service provider changes significantly. It achieves this simply in the following steps :-
 - Fetches the current exchanges rates from a particular service provider and persist it to the database with the status UNPROCESSED.
 - Do the same for all the service providers.
 - Once rates are fetched, compare it with the previous date closing rate from the same service provider. If the significant difference then mark the data as NOTIFY_ALERT else mark it as PROCESSED.
 - Post processing of the same, send out e-mails to the subscribers whose data is marked as NOTIFY_ALERT. Once all the mails are sent out, mark the data as processed.
 - Repeat all step after some duration.

## Tech stack
- Java 1.8
- Spring Boot
- Hibernate as JPA provider
- Maven
- MySQL

