FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY . .

RUN ./mvnw package -DskipTests

EXPOSE 8081

CMD ["java", "-jar", "target/*.jar"]

