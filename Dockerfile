#Start with a base image containing Java runtime
FROM openjdk:11-slim as build

#Declare user and group
RUN addgroup -S spring && adduser -S spring -G spring

#Run the application as a non-root user.
USER spring:spring

#Add the application jar to the container
COPY target/bank-statement-0.0.1-SNAPSHOT.jar app.jar

#Execute the application
ENTRYPOINT ["java","-jar","/app.jar"]
