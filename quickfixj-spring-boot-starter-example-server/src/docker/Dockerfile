FROM frolvlad/alpine-oraclejdk8:slim

ADD quickfixj-spring-boot-starter-example-server.jar /opt/app/application.jar
ADD application.yaml /opt/app/application.yml
ADD quickfixj-server.cfg /opt/app/quickfixj-server.cfg
ENV JAVA_OPTS="$JAVA_OPTS -Xmx512m -Xms512m"

EXPOSE 9876
EXPOSE 9877
EXPOSE 9878
EXPOSE 9879
EXPOSE 9880
EXPOSE 9881
EXPOSE 8080
EXPOSE 8000

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /opt/app/application.jar --spring.config.location=file:/opt/app/application.yml" ]
