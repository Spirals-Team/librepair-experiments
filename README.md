[![Build Status](https://secure.travis-ci.org/weaverplatform/weaver-sdk-java.png?branch=develop)](http://travis-ci.org/weaverplatform/weaver-sdk-java)[![codecov](https://codecov.io/gh/weaverplatform/weaver-sdk-java/branch/develop/graph/badge.svg)](https://codecov.io/gh/weaverplatform/weaver-sdk-java)

# Introduction

On top of Weaver Server for your datastorage, the Weaver Software Development Kit (SDK) for Java allows you
to efficiently create Weaver objects in your Java app and exchange them with your datastorage.

## Getting started

The main entry point of this SDK is the Weaver class, which you can find in the classpath directory (i.e.
'src/main/java/..'). The Weaver class provides a simple way to connect to your Weaver Server and create
your Weaver objects from this point on.

## Your first Weaver object

For your visual, lets say we have a brand new car that has a V8 engine.
We want to put information about our car and engine in our datastorage.

The first step is to create Weaver objects from Car and Engine. To make this easy for you, we designed the Entity class.
Every Weaver object - so we have a Weaver object called Car, and a Weaver object called Engine - is represented in this
SDK by the Entity class. You can find the Entity class in the same directory as your Weaver class. With the Entity
class, you have a great tool to add detailed information about your weaver object.

For the simplicity of this example, we're not going to discuss further details of the Entity class.
As said before, we start creating Weaver objects via the Weaver entry point.
Lets start!

The steps (will explain after):

1. Create a new instance of the Weaver class;
2. Connect the Weaver class to Weaver server;
3. Create attributes for Car;
4. Create attributes for Engine;
5. Link Engine to Car.

Step 1. To create a new instance of weaver class
```java
Weaver weaver = new Weaver()
```

To connect our Weaver instance with Weaver Server, the Weaver class provides a connect-method.
The SDK provides multiple ways to connect to Weaver server. For this example we use the WeaverSocket class.
The only thing you have to do, is to create a new WeaverSocket instance and provide your Weaver Server IP to
the WeaverSocket class.

Step 2. Connect the Weaver instance to Weaver server
```java
weaver.connect(new WeaverSocket(new URI("http://localhost:9487")));
```

We are now going to create attributes for our Car and Engine. Therefore, we use a HashMap.

Step 3 and 4, Create attributes
```java
Map<String, String> engineAttributes = new HashMap<>();
engineAttributes.put("engine", "v8");

Map<String, String> carAttributes = new HashMap<>();
carAttributes.put("model", "sedan");
```

The only thing left, is give Weaver your attributes for each Weaver object and link the objects together.
Step 5
```java
// Create our Weaver objects :)
Entity car = weaver.add(carAttributes, "Car");
Entity engine = weaver.add(engineAttributes, "Engine");

// Link them together and specify the relation between the car and engine
car.linkEntity("hasEngine", engine.toShallowEntity());
```

Congratulations! You created Weaver objects with the Weaver Development Kit.

## Want to do more with your Weaver objects?

Yes, you can :-). In the next example we're going to:

1. read our Weaver objects Car and Engine from the datastorage.
2. save/edit values on our Car and Engine Entities (because now we do more with Entity class).
3. unlink the Engine from Car

Actually we have another few options to read and fetch our Car object.
In step 5, we didnt define a custom ID for our Car and Engine.
We can also add a third argument to the add-function of the Weaver class for a custom ID.

```java
// Lets say we replace step 5
// we provide 'myCarId' and 'myEngineId' as example id's of our weaver objects.
Entity car = weaver.add(carAttributes, "Car", "myCarId");
Entity engine = weaver.add(engineAttributes, "Engine", "myEngineId");
```

To read an Weaver object via the SDK, the Weaver class provides a get-method. We only have to provide the id
of our Weaver objects. To get the id's from our Weaver objects we use the getId-method of the Entity class.

Step 1. Read a Weaver object with our SDK from the datastorage.
```java
// In case we dont change the above lines for step 5
// Lets just get our entities Id's
String idOfCar = car.getId();
String idOfEngine = engine.getId();

Entity myCarEntity = weaver.get(idOfCar);
Entity myEngineEntity = weaver.get(idOfEngine);
```

In the next step you will see how we edit some values from our Weaver objects via the Entity class.
Lets say we have to change to model of our car? After we did research, the model is not called a sedan, but
its actually a hatchback-model. Because we're going to change one of the attributes, the Entity class provides
a the updateEntityWithValue-method to make this possible.

Step 2. Save/Edit values on our Car Entity
```java
// Lets go further with our Entity instance called myCarEntity.
// the first argument is the key i.e. our 'model'
// the second argument is the value i.e. our change from 'sedan' to 'hatchback'
myCarEntity.updateEntityWithValue("model", "hatchback");
```

Lets say we want to remove the Car-Engine relation. The Entity class provides an unlinkEntity-method.
We only need to specify the name of the relation.

Step 3. unlink the Car from the Engine
```java
// provide the relation-name 'hasEngine' from step 5.
myCarEntity.unlinkEntity("hasEngine");
```



## Install with Maven
```xml
<dependency>
    <groupId>com.weaverplatform</groupId>
    <artifactId>sdk-java</artifactId>
    <version>1.1.0</version>
</dependency>
```

## Releasing
See http://central.sonatype.org/pages/ossrh-guide.html and http://central.sonatype.org/pages/apache-maven.html
