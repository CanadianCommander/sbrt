FROM openjdk:17

# setup env
ENV SPRING_PROFILES_ACTIVE=prod

# create work dir
RUN mkdir -p /opt/source/
COPY . /opt/source/
WORKDIR /opt/source/

# build package && copy artifacts (Kubernetes runs containers as a homeless user)
RUN ./mvnw clean package -Dmaven.test.skip=true && \
    cp -r ~/.m2 /.m2 && \
    chmod -R 777 /.m2

ENTRYPOINT ["java", "-javaagent:./lib/spring-instrument-5.3.0.jar", "-javaagent:./lib/aspectjweaver-1.9.7.jar", "-jar", "./target/{{project-name}}.jar"]