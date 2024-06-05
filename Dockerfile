#FROM alpine as build
#
#WORKDIR /app
#
#COPY . /app
#
#RUN ./gradlew build
#
#
#FROM openjdk:17-alpine
#
#VOLUME /tmp
#
#COPY --from=build build/libs/*.jar app/jar
#
##ARG JAR_FILE
##
##COPY ${JAR_FILE} app.jar
#
#ENTRYPOINT [ "java", "-jar", "/app.jar" ]


# mysql
FROM postgres