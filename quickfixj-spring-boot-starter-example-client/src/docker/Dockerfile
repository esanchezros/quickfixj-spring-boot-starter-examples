FROM frolvlad/alpine-oraclejdk8:slim

ADD quickfixj-spring-boot-starter-example-client.jar /opt/app/application.jar
ADD application.yaml /opt/app/application.yml
ADD quickfixj-client.cfg /opt/app/quickfixj-client.cfg
ENV JAVA_OPTS="$JAVA_OPTS -Xmx512m -Xms512m"

EXPOSE 8080
EXPOSE 8000

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /opt/app/application.jar --spring.config.location=file:/opt/app/application.yml" ]
