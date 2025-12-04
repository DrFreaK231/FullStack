FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY backend/as/pom.xml .
COPY backend/as/src ./src
RUN ./mvnw clean package -DskipTests
EXPOSE 8000
CMD ["java","-jar","target/as-0.0.1-SNAPSHOT.jar"]
