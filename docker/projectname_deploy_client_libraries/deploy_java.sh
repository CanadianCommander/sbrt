#!/bin/bash

pushd ../../target/generated-sources/client/java/ || exit 1

../../../../mvnw deploy -DaltDeploymentRepository={{client.java.deployment-repository}}{{project-name}}-java-client

popd || exit 1