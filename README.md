# twostep-java [![Build Status](https://travis-ci.org/objectia/twostep-java.svg?branch=master)](https://travis-ci.org/objectia/twostep-java)

Java API client for twostep.io


You can sign up for a twostep account at https://twostep.io.

## Requirements

Java 1.7 or later.

## Installation

### Maven

Add this dependency to your project's POM:

```xml
<dependency>
  <groupId>com.objectia.</groupId>
  <artifactId>twostep-java</artifactId>
  <version>1.0.0</version>
</dependency>
```

### Gradle

Add this dependency to your project's build file:

```groovy
compile "com.objectia:twostep-java:1.0.0"
```

### Others

You'll need to manually install the following JARs:

* The twostep JAR from https://github.com/objectia/twostep-java/releases/latest
* [Google Gson](https://github.com/google/gson) from <https://repo1.maven.org/maven2/com/google/code/gson/gson/2.8.2/gson-2.8.2.jar>.
* [Java JWT](https://github.com/auth0/java-jwt) from <https://repo1.maven.org/maven2/com/auth0/java-jwt/3.3.0/java-jwt-3.3.0.jar>.

### [ProGuard](http://proguard.sourceforge.net/)

If you're planning on using ProGuard, make sure that you exclude the twostep bindings. You can do this by adding the following to your `proguard.cfg` file:

    -keep class com.objectia.** { *; }

## Documentation

Please see the [Java API docs](https://docs.twostep.io/api) for the most up-to-date documentation.

## Usage

Example.java

```java
package examples;

import com.objectia.Twostep;
import com.objectia.twostep.exception.APIConnectionException;
import com.objectia.twostep.exception.APIException;
import com.objectia.twostep.exception.TwostepException;
import com.objectia.twostep.model.User;
import com.objectia.twostep.model.Users;

public class Example {

    public static void main(String[] args) {

        Twostep.clientId = System.getenv("TWOSTEP_CLIENT_ID");
        Twostep.clientSecret = System.getenv("TWOSTEP_CLIENT_SECRET");
        
        try {
            User user = Users.createUser("jdoe@example.com", "+12125551234", 1);
            // ...
        } catch (APIException ex) {
            System.err.println("Error: " + ex.getMessage());
        } catch (APIConnectionException ex) {
            System.err.println("Error: " + ex.getMessage());
        } catch (TwostepException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
    }
}```
