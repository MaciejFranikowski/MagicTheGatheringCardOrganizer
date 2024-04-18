FROM openjdk:19-jdk-alpine
WORKDIR /usr/local/service
COPY target/magicTheGatheringCardOrganizer-1.0.0-SNAPSHOT.jar magicTheGatheringCardOrganizer-1.0.0-SNAPSHOT.jar
EXPOSE 1500
CMD ["java", "-jar", "magicTheGatheringCardOrganizer-1.0.0-SNAPSHOT.jar"]