FROM openjdk:8-alpine

ADD maven/simple-server.jar /opt/app/application.jar
ADD quickfixj-server.cfg /opt/app/quickfixj-server.cfg
RUN mkdir -p /tmp/logs

EXPOSE 9876
EXPOSE 9877
EXPOSE 9878
EXPOSE 9879
EXPOSE 9880
EXPOSE 9881
EXPOSE 8080
EXPOSE 8000

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /opt/app/application.jar" ]
