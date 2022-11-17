FROM adoptopenjdk/openjdk11:jdk-11.0.11_9-alpine
ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
    JHIPSTER_SLEEP=0 \
    JAVA_OPTS=""
EXPOSE 9681

    
WORKDIR /app   
COPY /target/*.jar /app/


 
 
CMD echo "The application GWINVENTORY will start in ${JHIPSTER_SLEEP}s..." && \
    sleep ${JHIPSTER_SLEEP} && export EUREKA_INSTANCE_HOSTNAME=$HOSTNAME && \
    java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar /app/gwmmadminiot-0.0.1-SNAPSHOT.jar
 