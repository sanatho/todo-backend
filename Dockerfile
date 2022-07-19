FROM maven AS builder

COPY . /tmp
WORKDIR /tmp

RUN mvn clean install -DskipTests

ENTRYPOINT ["java", "-jar", "./target/todo-0.0.1-SNAPSHOT.jar"]

FROM openjdk:19-jdk-alpine3.14 AS runner

COPY --from=builder /tmp .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "./target/todo-0.0.1-SNAPSHOT.jar"]


