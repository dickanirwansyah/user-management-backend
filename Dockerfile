#base image with JDK 21
FROM eclipse-temurin:21-jdk
#Set the working directory
WORKDIR /app
#Copy the jar file into container
COPY target/backend-service.jar backend-service.jar
#Expose the port you applications runs on
EXPOSE 8080
#Run the application
ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "-jar", "backend-service.jar"]