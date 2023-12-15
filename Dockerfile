FROM openjdk:17-alpine as builder

COPY . .
RUN ./gradlew build -x test

FROM openjdk:17-alpine

WORKDIR /application
EXPOSE 8080

COPY --from=builder /build/libs/application.jar ./application.jar

ENTRYPOINT ["sh", "-c"]
CMD ["java -jar application.jar"]
