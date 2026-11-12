# ---------- Stage 1: Build ----------
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app

# Cache dependency: chỉ chạy lại khi pom.xml thay đổi
COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests -B \
    && cp target/*.jar app.jar

# ---------- Stage 2: Run ----------
FROM eclipse-temurin:21-jre AS production
WORKDIR /app

ENV TZ=Asia/Ho_Chi_Minh \
    JAVA_OPTS="-XX:MaxRAMPercentage=75 -Djava.awt.headless=true"

RUN groupadd -r spring && useradd -r -g spring spring
COPY --from=builder --chown=spring:spring /app/app.jar app.jar
USER spring

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar app.jar"]