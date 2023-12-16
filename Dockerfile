FROM gradle:8.5-jdk17 as builder

WORKDIR /

COPY . .
RUN gradle build -x test

FROM openjdk:17-alpine

WORKDIR /application
EXPOSE 8080

RUN apk add curl

COPY --from=builder /build/libs/application.jar ./application.jar

HEALTHCHECK CMD curl --fail http://localhost:8080/actuator/health || exit 1
ENTRYPOINT ["sh", "-c"]
CMD ["java -jar application.jar"]
