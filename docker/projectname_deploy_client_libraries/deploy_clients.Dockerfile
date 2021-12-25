FROM openjdk:17.0.1-jdk-buster

ARG USER_GROUP_ID
ARG USER_ID

# setup user
RUN /usr/sbin/groupadd --gid ${USER_GROUP_ID} {{container-user}}
RUN /usr/sbin/useradd -m -d /home/{{container-user}} -g {{container-user}} --uid ${USER_ID} {{container-user}}

# create work dir
RUN mkdir -p /opt/source/
RUN chown {{container-user}}:{{container-user}} /opt/source
WORKDIR /opt/source/

# install rbenv
RUN apt update
RUN apt install -y build-essential libssl-dev zlib1g-dev
RUN git clone https://github.com/rbenv/rbenv.git /home/{{container-user}}/.rbenv
RUN mkdir -p /home/{{container-user}}/.rbenv/plugins
RUN git clone https://github.com/rbenv/ruby-build.git /home/{{container-user}}/.rbenv/plugins/ruby-build
RUN chown -R {{container-user}}:{{container-user}} /home/{{container-user}}/

# install npm
RUN apt install -y nodejs npm

# setup rbenv env
USER {{container-user}}
RUN /home/{{container-user}}/.rbenv/bin/rbenv install 3.0.1
RUN /home/{{container-user}}/.rbenv/bin/rbenv global 3.0.1

# TODO sbmicro fix this
ENTRYPOINT ["./docker/{{project-name-lower}}_deploy_client_libraries/deploy.sh"]
