FROM maven:3.9.13-eclipse-temurin-8 AS builder

WORKDIR /workspace

COPY pom.xml pom.xml
COPY blog-common/pom.xml blog-common/pom.xml
COPY blog-user/pom.xml blog-user/pom.xml
COPY blog-api/pom.xml blog-api/pom.xml
COPY blog-article/pom.xml blog-article/pom.xml

COPY .mvn .mvn

COPY blog-common/src blog-common/src
COPY blog-user/src blog-user/src
COPY blog-api/src blog-api/src

RUN mvn -pl blog-api -am clean package -DskipTests

FROM eclipse-temurin:8-jre

WORKDIR /app

COPY --from=builder /workspace/blog-api/target/blog-api-1.0.0.jar app.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "/app/app.jar", "--spring.profiles.active=docker"]
