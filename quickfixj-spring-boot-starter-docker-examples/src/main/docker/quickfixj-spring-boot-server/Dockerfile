FROM frolvlad/alpine-oraclejdk8:slim

ADD quickfixj-spring-boot-server.jar .
ENV JAVA_OPTS=""

EXPOSE 9876
EXPOSE 9877
EXPOSE 9878
EXPOSE 9879
EXPOSE 9880
EXPOSE 9881

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /quickfixj-spring-boot-server.jar" ]
