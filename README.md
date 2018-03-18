# spraybot

[![Build Status](https://travis-ci.org/spraybot/chatbot.svg?branch=master)](https://travis-ci.org/spraybot/chatbot)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/b61613a7cd8c4c479dff12bff5398a16)](https://www.codacy.com/app/cspray/chatbot?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=spraybot/chatbot&amp;utm_campaign=Badge_Grade)
[![codecov](https://codecov.io/gh/spraybot/chatbot/branch/master/graph/badge.svg)](https://codecov.io/gh/spraybot/chatbot)

A fully asynchronous, multi-threaded chatbot powered by [Vert.x][vertx].
Utilizing the Actor Model, where actors are referred to as Verticles,
we intend to build a highly scalable, performant, easy-to-use system for
handling large quantities of chat messages from a variety of adapters.
The real-time, async nature of chat communications makes it a great use
case for the Actor Model.

## Directives

Being fully asynchronous there are important, official rules that must
be followed to ensure proper, performant operations. These are the spraybot
Directives.

**Violation of these directives will likely result in performance
degradation and extremely sad developers.**

1. The first, and most important, directive is **thou shall not block**.

    Performance in your ChatBot implementations will not come from spawning
    more threads but ensuring that the EventLoop passing messages between
    Verticles and executing tasks is allowed to continuously operate with
    no blocking operations. One of the prime opportunities to introduce
    blocking code is with network and file I/O; it is incredibly critical
    that you use async libraries and functionality for these type of operations
    or anything else that might block the EventLoop. If it is not possible
    to run the code asynchronously you **MUST** execute the task in a blocking
    worker thread designed to execute this type of code.

    It cannot be overemphasized how important this directive is. Blocking
    the EventLoop is very, very super-duper bad. You should be taking active
    steps in the design, development, and review stages to ensure that
    blocking code is either discouraged completely and is refactored to
    be asynchronous or the blocking code must be be executed in a blocking
    worker thread.

1. The second directive is **Verticles must not share state**.

    A critical aspect of the Actor Model is that threads do not have to
    be locked or synchronized as each Actor's state is internal to itself
    and mutations or state access should be handled via the passing of
    messages between actors. As there is no thread synchronization or locking
    abstractions readily available if your Verticles share state your
    application _will break_, likely in strange and unexpected ways.

1. The third directive is **do not create native threads**.

    It is important that you do not create native threads for a couple
    primary reasons. While creating your own threads may not be as immediately
    harmful as directive 1 or 2 it is still likely to lead to invalid
    application operations.

    The first being that it is unlikely to meaningfully
    impact the performnance of your ChatBot. Though we can conceptually
    think of each Verticle as its own "thread", in physical CPU terms each
    Verticle represents _many_ threads. The default is 2 per core but this
    is configurable and it may be less or it may be more. With the
    amount of Verticles we already have running by the time your ChatCommand
    is executed many threads are already provisioned and running. To keep
    your application performant it is much more important that you follow
    directive 1 and **do not block the EventLoop**.

    The second reason is that by creating your own native thread you're
    very likely to break directive 2. You will probably need to share some
    state between the thread running your process and the Verticle actually
    powering your code. At this point you'll need to start thinking about
    locks and synchronization and by doing so violate core tenets of the
    Actor Model.

    If you absolutely must spawn a new thread for your application you
    **MUST** create and deploy a Verticle so that Vert.x may properly
    manage it.

Violating these directives breaks the contracts agreed to by spraybot and
Vert.x as well as core principles in the Actor Model. Breaking these absolutely
will result in poor application performance or design or both.

## Architecture

In Vert.x an Actor is referred to as a Verticle. Each Verticle acts in its
own thread(s) and a Vert.x EventLoop processes tasks on each Verticle you've
deployed. Verticles communicate with each other by passing messages through
Vert.x's immensely powerful EventBus. By ensuring state in each Verticle
remains private and code execution paths aren't shared we are able to avoid
some of the pitfalls around multi-threaded architecture including locks,
deadlocks and synchronization.

However, to achieve this there are restrictions placed on the calling code.
If you have not reviewed the [spraybot Directives](#directives)
it is highly recommended you do so. The most important, and its worth
repeating, is **DO. NOT. BLOCK.** Every time you block a fairy loses its
wings and a developer weeps into their keyboard.

### Our Verticles

- HardDrive

    Acting as a key-value store for our ChatBot this Verticle is effectively
    the persistence layer. It is responsible for subscribing to events for
    "CRUD" operations and responding with requested data or status.

- ChatAdapter

    Responsible for responding to received ChatMessages from an underlying
    chat connection and publishing them for the ChatBot to process.
    Additionally is responsible for subscribing to messages for sending
    ChatMessages from the ChatBot back to the User. Ultimately all of the
    interactions with this Verticle are abstracted away from developers
    implementing their own Commands.

- ChatBot

    Ensures that ChatMessages sent from the ChatAdapter are processed by
    checking the ChatMessage against a set of command names the ChatBot
    knows; effectively the ChatBot acts as a router. If a command name
    could be found the ChatBot publishes that command name for the
    ChatCommandRunner to process.

- ChatCommandRunner

    The ChatCommandRunner acts as a registry of ChatCommands but also is
    expected to subscribe to message published by the ChatBot and to
    execute requested ChatCommands. If the ChatCommandRunner can't find
    a requested ChatCommand it should return a response indicating such.

## Message Nomenclature

Due to the domain and architecture we have chosen the word "Message" suddenly
becomes quite overloaded. A message is something that a user sends to our
ChatBot as well as an internal technical concept for how Verticles
communicate with one another. Most likely you're going to be replying to
and sending chat messages and messages for Verticles _at the same time_.
If we do not have reasonable nomenclature and semantics our app will start
to turn to mud.

As such, any place in the application or application's document that refers
to the communication between a user and a ChatBot should be referred to
as a ChatMessage. Even in documentation the class-style naming should be
used; "chat message" is ok but ChatMessage should be preferred.

Any place you see messages or Message it is referring to Vert.x concept
of communicating between Verticles. If you see a place where the term
mesage or Message is being used to refer to a ChatMessage *please fix
the mistake*.

## Verticle Message Design

Passing around messages between Verticles is a critical aspect of our
architecture; the application literally does not work without it. Though
incredibly powerful the ability for an EventBus to send a Message with literally
any body type we throw away some of the type safety benefits of Java. To
ensure that we have a consistent and standard way for messages to be
constructed the format of those messages is described here and each
Verticle documents the messages that it responds to or sends to other
Verticles. There are three primary components of a Verticle message so
lets take a look at each of them.

### Address

While addresses can be any arbitrary string in the spraybot domain there
are only a handful of available addresses that will receive responses. Each
of those addresses maps back to a Verticle. Additionally, each address
is prefixed by "spraybot" to ensure proper "namespacing". The available
addresses are:

- spraybot.brain
- spraybot.chatadapter
- spraybot.chatbot
- spraybot.commandrunner

As you can see, pretty straightforward to tell who should receive a message.

### Body type

The body type is ultimately dependent upon the address being delivered
to and the operation being performed. For example, to send a message to
the HardDrive to learn something requires the body be a HardDriveSector but if you
want to remember the HardDriveSector the body must be a string representing
the neuron identifier. It is highly important that each Verticle documents
the type of message body's it is sending and/or expecting.

### Headers

Each message sent to a Verticle must include some required headers. We
do not take the requirement for headers lightly and strive to limit the
number of required headers to strictly those required to perform operations.
Below are a list of all required headers and each Verticle should list
any required headers it may have, thought that Verticle should also strive
to limit how many headers it requires for operations.

- `op`

    The exact value of the `op` header is defined by each message but in
    practice this will likely map back to an interface method backing the
    given Verticle. For example, to teach a HardDrive a new HardDriveSector you'd
    pass an `op` header with a value of `learn`. To remember, the value
    would be `remember`, etc.

[vertx]: http://vertx.io/
