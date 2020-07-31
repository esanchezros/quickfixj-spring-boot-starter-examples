FROM openjdk:8-alpine

ADD maven/simple-client-with-database.jar /opt/app/application.jar
ADD quickfixj-client.cfg /opt/app/quickfixj-client.cfg
RUN mkdir -p /tmp/logs

EXPOSE 8080
EXPOSE 8000

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /opt/app/application.jar" ]
