FROM openjdk:17-oracle as builder

COPY . /app
WORKDIR /app
RUN chmod +x ./mvnw

RUN ./mvnw -Dmaven.test.skip=true clean package


FROM openjdk:17-oracle

WORKDIR /app
COPY --from=builder /app/target/*.jar manutenzione.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/manutenzione.jar"]

