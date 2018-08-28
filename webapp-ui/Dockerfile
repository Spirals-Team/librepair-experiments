FROM openjdk:8-jdk-alpine
ADD /target/phonebook.war demo.war
EXPOSE 8080
ENTRYPOINT ["java","-jar","demo.war"]