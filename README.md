# rxjava2-http
<a href="https://travis-ci.org/davidmoten/rxjava2-http"><img src="https://travis-ci.org/davidmoten/rxjava2-http.svg"/></a><br/>
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.davidmoten/rxjava2-http/badge.svg?style=flat)](https://maven-badges.herokuapp.com/maven-central/com.github.davidmoten/rxjava2-http)<br/>
[![codecov](https://codecov.io/gh/davidmoten/rxjava2-http/branch/master/graph/badge.svg)](https://codecov.io/gh/davidmoten/rxjava2-http)

Transmit RxJava2 Flowable over http (with non-blocking backpressure).

Status: *pre-alpha* (in development)

## Features
* Apply non-blocking backpressure to streams over networks
* Use whatever serialization library you want (the core supports `Flowable<ByteBuffer>`)
* Supports plain HTTP/HTTPS service (no firewall troubles with WebSockets)
* Uses Servlet 3.1+ asynchronous processing (by default)

<img src="src/docs/marble-diagram.png?raw=true" />

## Getting started

### Maven dependency
```xml
<dependency>
  <groupId>com.github.davidmoten</groupId>
  <artifactId>rxjava2-http</artifactId>
  <version>VERSION_HERE</version>
</dependency>
```

### Create servlet
The servlet below exposes the `Flowable.range(1, 1000)` stream across HTTP:

```java
@WebServlet(urlPatterns={"/stream"}, asyncSupported=true)
public class HandlerServlet extends FlowableHttpServlet {
      
    public HandlerServlet() {
        super( request -> 
          Flowable
            .range(1,1000)
            .map(Serializer.javaIo()::serialize)      
        );
    }
}
```
The default behaviour is to schedule requests on `Schedulers.io()` but this is configurable via another `FlowableHttpServlet` constructor.

### Create client
Assuming the servlet above is listening on `http://localhost:8080/stream`, this is how you access it over HTTP:

```java
Flowable<Integer> numbers = 
  Client
   .get("http://localhost:8080/stream")
   .deserialized();
```
More client options are available. Here is an example:

```java
Flowable<Integer> numbers = 
  Client
    .get("http://localhost:8080/stream") //
    .method(HttpMethod.GET) // 
    .serializer(serializer) // used for deserialization
    .rebatchRequests(128); // necessary to enable backpressure over the network without blocking calls
```

### Serializers

`Serializer.javaIo()` can be used to serialize classes that implement `Serializable`. It is much slower than products like *Kryo* or indeed if you have the time, custom serialization.

## Good practices

### Backpressure
To ensure backpressure is applied over the network (so operating system IO buffers don't fill and block threads) it's a good idea to request data in batches:

* apply `rebatchRequests` to the client-side Flowable
* [*rxjava2-extras*](https://github.com/davidmoten/rxjava2-extras) has a number of request manipulating operators (`minRequest`, `maxRequest` and another version of [`rebatchRequests`](https://github.com/davidmoten/rxjava2-extras#rebatchrequests) with different features)

### Quiet streams
Note that a long running quiet source Flowable over http(s) is indistinguishable from a chopped connection (by a firewall for instance). To avoid this:

* regularly cancel and reconnect to the stream

OR

* include a heartbeat emission in the Flowable which you filter out on the client side

OR

* put a `timeout` operator on the Flowable on the client side and `retry`

### Orphan streams on the server

It's also a good idea to:

*  put a `timeout` operator on the server Flowable in case a client leaves (or is killed) without cancelling

This goes for any server Flowable, even one that is normally of very short duration. This is because the subscription is retained in a global map until cancellation and will retain some memory. Note that under a lot of GC pressure a container may choose to destroy a servlet (and run `init` again when another call to that servlet happens). In this circumstance `FlowableHttpServlet` is designed to cancel all outstanding subscriptions and release the mentioned map for gc. 

## Design
WebSockets is a natural for this but can be blocked by corporate firewalls so this library starts with support for HTTP 1.0. 

We want API support for these actions:

* subscribe
* request
* cancel

Support is provided via these URL paths

Path | Method | Action | Returns
--- | --- | --- | --
`/`   | GET | subscribe with no request | Stream ID then binary stream
`/?r=REQUEST`|GET | subscribe with initial request | Stream ID then binary stream
`/?id=ID&r=REQUEST` |GET| request more from given stream | nothing
`/?id=ID&r=-1` |GET| cancel given stream | nothing

The format returned in the subscribe calls is (EBNF):

```
Stream ::= Id Item* ( Error | Complete )?
Item ::= Length Byte*
Error ::= NegativeLength StackTrace 
StackTrace ::= Byte+
Complete ::= NegativeMinLength
```

### Stream
<img src="src/docs/Stream.png?raw=true"/><br/>

### Item
<img src="src/docs/Item.png?raw=true"/><br/>

### Complete
<img src="src/docs/Complete.png?raw=true"/><br/>

### Error
<img src="src/docs/Error.png?raw=true"/><br/>


The core of the library is support for publishing a `Flowable<ByteBuffer>` over HTTP. Serialization is a little optional extra that occurs at both ends.

### Nested Flowables
I have a design in mind for publishing nested Flowables over HTTP as well (representing the beginning of a nested Flowable with a special length value). I don't currently have a use case but if you do raise an issue and we'll implement it.


