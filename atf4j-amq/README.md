# Automated Test Framework for Java

## Automated Test Framework for Java - ATF4J-AMQ

Automation Test Framework for Apache ActiveMQ

## Maven Dependency

	<dependency>
		<groupId>net.atf4j</groupId>
		<artifactId>atf4j-amq</artifactId>
		<version>0.10.0-SNAPSHOT</version>
	</dependency>

## Usage

In command line shell run:

    mvn test

## Overriding options

    mvn -Doptions="value" test

## Example Code
### Message Finder

        final MessageFinder folderWalker = new MessageFinder();
        verifyNotNull(folderWalker);

        final List<File> messages = folderWalker.walk();
        verifyNotNull(messages);
        for (final File file : messages) {
            log.info("{} = {}", file.getName(), file.toString());
        }

### Message Producer

        final String[] messages = ...;

        final Producer producer = new Producer();
        producer.execute(messages);

### Message Consumer

        final Consumer consumer = new Consumer();
        final String[] messages = consumer.execute();
 