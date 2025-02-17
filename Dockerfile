FROM amazoncorretto:17 AS runtime
EXPOSE 9090
RUN mkdir /app
COPY ./backend/build/libs/*.jar /app/matsumo-me-backend.jar
ENTRYPOINT ["java","-jar","/app/matsumo-me-backend.jar"]
