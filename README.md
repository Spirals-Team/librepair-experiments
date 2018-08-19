Movie Theater Manager
=====================

[![Build Status](https://travis-ci.org/unlimit1984/movie-manager.svg?branch=master)](https://travis-ci.org/unlimit1984/movie-manager)
[![Coverage Status](https://coveralls.io/repos/github/unlimit1984/movie-manager/badge.svg)](https://coveralls.io/github/unlimit1984/movie-manager)

* [Short description](#short-description)
* [How to download](#how-to-download)
* [How to test](#how-to-test)
* [How to run](#how-to-run)
* [Spring core HW](#spring-core)
* [Spring advanced HW](#spring-advanced)
* [User guide](#user-guide)

## Short description
Application allows to manage creating Events in different Auditoriums.
You can:
* create Event
* create Auditoriums for Event
* create a pool of seats and VIP seats in chosen Auditorium
* book a set of tickets
* for sure you you can rate a movie
* prolong your Event for multiple days and etc. 

As a manager of Movie Theater Manager you can manipulate all this data.
For sure, all operations are verified and validated when they occur.  

As a user you can add new Event and setup the following details:
* set a list of dates (airDates) with Auditoriums for planning Events 
* set a rating for this Event: high, medium, low
* base price for a ticket

Also you have the opportunity to manipulate Auditoriums by adding them with setting:
* name
* number of seats
* VIP seats

Each potential User with name, email and id can book a ticket on Event using BookingService with following API:
* remove
* getUserByEmail
* save

We have some additional service DiscountService for getting discount during purchases.

## How to download
* install git
* git clone https://github.com/unlimit1984/movie-manager.git

## Spring Core

### HW1

1. __DONE__ Add_UserServiceImpl, UserRepository interface and memory implementation
2. __DONE__ Add EventServiceImpl, EventRepository interface and memory implementation
3. __DONE__ All services should be provided by tests

### HW2
1. __DONE__ Add AuditoriumServiceImpl, AuditoriumRepository and memory implementation
2. __DONE__ Add BookingServiceImpl, TicketRepository and memory implementation
3. __DONE__ Add simple DiscountServiceImpl. 10% for buying more 10 tickets, 20% for 20 tickets and 30% for more than 50 tickets. At the next versions (HW3) Discount service will be improved by strategies
4. __DONE__ All services should be provided by tests
5. __IN_PROGRESS__ Provide Command Line Interface for adding entities and reading all entities
6. __DONE__ Replace Xml Spring configuration by AppConfig as a Spring Configuration annotated class
7. __DONE__ Add CounterAspect and provide tests
8. __DONE__ Add DiscountAspect and provide tests
9. __DONE__ Add LuckyWinnerAspect and provide tests

### HW3
1. __DONE__ Fix Discount service logic by adding two strategies: TicketCountStrategy and BirthdayStrategy
3. __DONE__ Fix pointcuts in Aspects. Now they targets interfaces
4. __DONE__ Fix DiscountAspect by calculating how many times each discount was given total and for user
5. __DONE__ Fix Booking service. Total price corrected: price depends on EventRating and vipSeat
6. __DONE__ Add DAO implementations that uses JdbcTemplate
7. __DONE__ Add sql scripts for initialization and populating DB
8. __DONE__ Implement DAO object to store all Aspect counters into the database

## Spring Advanced

### HW1

1. __DONE__ Configure dispatcher servlet and Spring MVC Context
2. __DONE__ Implement UserController and all necessary ftl views
3. __DONE__ Implement EventController and all necessary ftl views
4. __DONE__ Implement AuditoriumController and all necessary ftl views
5. __DONE__ Implement BookingController and all necessary views
6. __DONE__ Implement generic Exception handler (ExceptionController)
7. __DONE__ Implement download PDF link for getting list of tickets
8. __DONE__ Provide multipart upload file (users, events)

### HW2

1. __DONE__ Add maven dependencies for Spring security
2. __DONE__ Add spring security xml (example: spring-security-context.xml)
3. __DONE__ Wire all spring contexts properly
4. __DONE__ Add security filter in web.xml
5. __DONE__ Add csrf support
6. __DONE__ Add custom login/logout pages
7. __DONE__ Add roles for User entity and implement tests
8. __DONE__ Add UserDetailsService
9. __DONE__ Add Remember-Me Authentication for Hash based and DB persistent storage implementations
10. __DONE__ Implement password encoding during authentication and adding new User
11. __DONE__ Add UserAccount entity
12. __DONE__ Add user info page
13. __DONE__ Add AccountController and implement UserAccountService
14. __DONE__ Add all necessary views for account and booking process
15. __IN_PROGRESS__ Fix all tests and aspects

### HW3
1. __DONE__ Fix csrf for uploading ticket's file
2. __DONE__ Fix CounterAspect test
3. __DONE__ Add UserAccount infrastructure (entity, repository, service, controller, views)
4. __DONE__ Implement booking process
5. __DONE__ Add transaction support
6. __DONE__ Add cascade deleting
7. __DONE__ Wire users and accounts
8. __IN_PROGRESS__ Implement gaps in UserAccountServiceTest

### HW4
1. __DONE__ Implement user rest endpoint
2. __DONE__ Implement event rest endpoint
3. __DONE__ Implement booking rest endpoint
4. __DONE__ Configure ContentNegotiationViewResolver via MVC namespace
5. __DONE__ Implement PdfHttpMessageConverter
6. __DONE__ Add two converters: JSON and PDF
7. __DONE__ Extract all pdf logic in PdfUtil class
8. __DONE__ Add live tests for UserRestController and BookingRestController
9. __DONE__ Improved security
10. __DONE__ Mark User and Event entities with appropriate JAXB annotations to enable JAXB binding.
11. __DONE__ Add jaxb2-maven-plugin and generate schema.xsd by calling mvn clean install
12. __IN_PROGRESS__ Generate JAXB-derived service classes using xjc tool.
13. __IN_PROGRESS__ Create SOAP based web-service endpoints to serve all User and Event related operations.
14. __IN_PROGRESS__ Add Spring WS application context configuration.
15. __IN_PROGRESS__ Implement test client for above mentioned endpoints via spring WebServiceTemplate.

## How to test
1. install maven
2. go to project folder by "cd" command in Terminal (Mac) or any Command Line Commander (Windows)
3. mvn clean test

## How to run

__Option__ (FAST)
1. install maven
2. go to project folder
3. mvn tomcat7:run
4. go http://localhost:8080/movie

__Option__ (LONG)
1. install maven
2. install tomcat9
3. go to tomcat9/bin folder
4. using Command Line execute catalina.bat start
5. go to project folder
6. execute command: mvn clean package
7. alternatively for ignoring tests execute: mvn clean package -DskipTests
8. put generated project/target/movie.war file in tomcat9/webapps folder
9. open url in browser: http://localhost:8080/movie/
10. have fun by creating and modifying users, events and tickets

## User guide
1. Login using unlim0@mail.com/password or unlim1@mail.com/password
2. Don't forget to add BOOKING_MANAGER role to user, otherwise you will get "Access denied"
3. Logout and login again
4. Try to order tickets