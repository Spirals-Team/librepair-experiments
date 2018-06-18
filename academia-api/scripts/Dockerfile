FROM maven:3.5.3-jdk-8-alpine

VOLUME [ "/root/.m2" ]

WORKDIR /app

COPY . .

EXPOSE 8080

# o parâmetro "docker" é utilizado como spring profile
ENTRYPOINT [ "./scripts/run.sh", "docker" ]
