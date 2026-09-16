FROM eclipse-temurin:21-jre

RUN groupadd --system app && useradd --system --gid app --no-create-home app

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8100

USER app

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
