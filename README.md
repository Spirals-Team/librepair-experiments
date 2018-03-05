# Spirits of Arianwyn Discord Bot
Version: 2.0:  [![Build Status](https://travis-ci.org/SoAJeff/SoADiscordBot.svg?branch=master)](https://travis-ci.org/SoAJeff/SoADiscordBot)
Dev Branch: [![Build Status](https://travis-ci.org/SoAJeff/SoADiscordBot.svg?branch=dev)](https://travis-ci.org/SoAJeff/SoADiscordBot)

A Discord Bot written using the [Discord4J](https://github.com/austinv11/Discord4J) Java library for use by the Spirits of Arianwyn RuneScape clan's Discord Server.

## Building the Bot
To build the bot it is recommended to have:
- Java 8.  Discord4J only supports Java 8.  You can download the JDK from [Oracle's Website](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- Apache Maven.  The bot is currently structured to build with Maven.  You can get Maven from [Apache's Website](https://maven.apache.org/)

The bot can be built by running the following command:
```
mvn clean install
```

The build creates a shaded Jar with all dependencies included within to make executing the bot easier.  The build also creates the triviacreator application.

## Javadocs
Javadocs for the current master branch (of the discordbot module only) can be found [here](https://soajeff.github.io/SoADiscordBot/)

## More Information
For more information, please refer to the thread within Elvish Lounge found on the [SoA Forums](https://forums.soa-rs.com).
