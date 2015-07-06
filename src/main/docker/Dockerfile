FROM java:8
VOLUME /tmp
ADD app-0.0.1-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","-Dserver.port=8888","-Detherpad.base.url=http://epserver/p","/app.jar"]
