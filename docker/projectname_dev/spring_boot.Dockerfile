FROM openjdk:17

ARG USER_GROUP_ID
ARG USER_ID

# setup env
ENV SPRING_PROFILES_ACTIVE=dev

# setup user
RUN /usr/sbin/groupadd --gid ${USER_GROUP_ID} {{container-user}}
RUN /usr/sbin/useradd -m -d /home/{{container-user}} -g {{container-user}} --uid ${USER_ID} {{container-user}}

# create work dir
RUN mkdir -p /opt/source/
RUN chown {{container-user}}:{{container-user}} /opt/source
WORKDIR /opt/source/

USER {{container-user}}
CMD ./mvnw clean package -Dmaven.test.skip=true && java -javaagent:./lib/spring-instrument-5.3.0.jar -javaagent:./lib/aspectjweaver-1.9.7.jar -jar ./target/{{project-name}}.jar