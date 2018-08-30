clean:
	./mvnw clean

compile:
	./mvnw compile

test:
	./mvnw surefire:test

run:
	./mvnw spring-boot:run
		
install:
	./mvnw install

package:
	./mvnw package
	