FROM maven:3.5-jdk-8-alpine
ADD ./ ./
EXPOSE 80
CMD ["mvn", "spring-boot:run"]