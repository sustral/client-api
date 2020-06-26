FROM openjdk:11

WORKDIR /app
COPY . .
RUN ./gradlew build -x test

EXPOSE 8080
CMD ["java", "-jar", "build/libs/clientapi-1.0.0-SNAPSHOT.jar"]
