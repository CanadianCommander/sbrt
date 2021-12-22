FROM openjdk:17

ARG USER_GROUP_ID
ARG USER_ID

# setup user
RUN /usr/sbin/groupadd --gid ${USER_GROUP_ID} {{container-user}}
RUN /usr/sbin/useradd -m -d /home/{{container-user}} -g {{container-user}} --uid ${USER_ID} {{container-user}}

# create work dir
RUN mkdir -p /opt/source/
RUN chown {{container-user}}:{{container-user}} /opt/source
WORKDIR /opt/source/

USER {{container-user}}
CMD ./mvnw clean verify -Dspring.profiles.active=test