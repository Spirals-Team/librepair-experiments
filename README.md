# Trash Collection

If you have a running Raspberry pi
(or any other supported [Pi4j platform](http://pi4j.com/apidocs/com/pi4j/platform/Platform.html)) with
some spare pins, you can make the pi remind you when to put out your trash cans.

# Installation

- mvn install
- cp target/trashcollection-*.jar /usr/local/bin/trashcollection.jar
- cp src/systemd/trashcollection.service /etc/systemd/system/
- vi /etc/trashcollection.properties

## Connect the Raspberry Pi

Follow the instructions of the connect wizard:
 - trashcollection.jar --spring.config.location=/etc/trashcollection.properties --setup
