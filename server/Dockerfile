FROM openjdk:8-jdk-alpine
VOLUME /tmp
RUN mkdir /elo-tracker
COPY . /elo-tracker
WORKDIR /elo-tracker
ARG ELO_TRACKER_DB_USERNAME=username
ARG ELO_TRACKER_DB_PASSWORD=password
ENV ELO_TRACKER_DB_USERNAME ${ELO_TRACKER_DB_USERNAME}
ENV ELO_TRACKER_DB_PASSWORD ${ELO_TRACKER_DB_PASSWORD}
RUN /elo-tracker/gradlew build -x test
RUN mv /elo-tracker/build/libs/*.jar /elo-tracker/app.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/elo-tracker/app.jar"]

# docker build --no-cache -t elo-tracker-dev .
# docker run -it --rm /
# -e ELO_TRACKER_DB_USERNAME=${ELO_TRACKER_DB_USERNAME} /
# -e ELO_TRACKER_DB_PASSWORD=${ELO_TRACKER_DB_PASSWORD}/
# -p 8080:8080 elo-tracker-dev
