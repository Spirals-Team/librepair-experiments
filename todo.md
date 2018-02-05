# todo

* Implement a delegated class for URL or HttpRequest
    * read/write headers
    * read/write payload
    * ..this class will be used by the worker
* Implement a fluent API Builder API
    * HttpConsumer.getHeaders(), HttpConsumer.doGet(), ...
    * Group, WorkerTask, Operation, Outcome, ..

* parameter store bound to each group
* builder stack-walker
* use id and idref in builder for inter referencing
* make a config system to define an execution flow

Fluent API sketching:
--

```java
APIClient.builder()
    .group("posten")
        .operation(GetPostenDatabase.class)
        .outcome(HandlePostenDatabase.class)
        .store(State.Data("city").From("0001"))
        .done()
    .group("maps")
        .operation(GetGoogleMapsGeoLocation.class, State.data("city"))
        .outcome(HandleGoogleMapsGeoLocation.class)
        .store(State.Data("city"))
        .done()
    .group("facebook")
        .operation(GetFacebookPages.class, State.data("places"))
        .outcome(HandleFacebookPages.class)
        .store(State.Data("city"))
        .done()
    .execute();
```
    
Execute all Operations in HystrixCommand<Outcome>.

Plan:
--

> https://dzone.com/articles/java-fluent-api-design

1. Start with Implementing Operation, Outcome and a generic Hystrix command.
   * Operation should take Parameters and return an Outcome object that uses generics for returnType
1. Investigate how in and out metadata should be handled
1. Do the builder when key features are in place and decide on how to express the fluent api
1. Auth strategy and how apply that per operation needs to investigated
1. Common StateStore must also be defined
1. Agnostic event observer (publish/subscribe) should to be implemented, since both CDI and Spring should be supported
    * This may be done by exposing adapters?
1. Consider the use of JSR107 JCache so HazelCase and alike can be used as memory storage
    * Check out: https://github.com/jsr107/RI


Experimental:
--

