FROM amazoncorretto:23 AS runtime
ARG SUPABASE_URL
ARG SUPABASE_KEY
EXPOSE 9090:9090
RUN mkdir /app
COPY ./backend/build/libs/*.jar /app/matsumo-me-backend.jar
ENTRYPOINT ["java","-DSUPABASE_URL=$SUPABASE_URL","-DSUPABASE_KEY=$SUPABASE_KEY","-jar","/app/matsumo-me-backend.jar"]
