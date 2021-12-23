#!/bin/bash
pushd "$(dirname "$0")" || exit 1

REBUILD=false;
export USER_ID="$(id -u)";
export USER_GROUP_ID="$(id -g)";
export USER_HOME=$HOME;

print_usage()
{
  echo "docker.sh <operation> [-R]"
  echo "[OPTIONS]"
  echo "  <operation> one of: "
  echo "    - dev run the dev server"
  echo "    - prod build the prod image"
  echo "    - generate_api generate the client APIs"
  echo "    - deploy_client_libraries deploy client libraries to their respective repositories"
  echo "    - deploy_staging build and deploy this branch to the staging server (Open Shift)"
  echo "  -R rebuild containers before running"
  popd && exit 0
}

# parse options
if [[ "${1}" == "--help" ]] || [[ "${1}" == "-h" ]]; then
  print_usage
fi

OPERATION=${1}
shift

while getopts "R" option; do
  case ${option} in
    R ) REBUILD=true
    ;;
    \? ) print_usage
  esac
done

if [[ ${OPERATION} == "dev" ]] || [[ ${OPERATION} == "generate_api" ]]; then
  if [[ ${REBUILD} == true ]]; then
    sudo -E docker container rm {{project-name-lower}}_${OPERATION}_spring_boot_1
    sudo -E DOCKER_BUILDKIT=1 docker build --no-cache -f {{project-name-lower}}_${OPERATION}/spring_boot.Dockerfile ../ -t "{{project-name-lower}}_${OPERATION}_spring_boot" --build-arg USER_ID=${USER_ID} --build-arg USER_GROUP_ID=${USER_GROUP_ID}
  fi
  sudo -E docker-compose -f {{project-name-lower}}_${OPERATION}/docker-compose.yml up --abort-on-container-exit --exit-code-from spring_boot
elif [[ ${OPERATION} == "deploy_client_libraries" ]]; then
    if [[ ${REBUILD} == true ]]; then
      sudo -E docker container rm {{project-name-lower}}_${OPERATION}_deploy_clients_1
      sudo -E DOCKER_BUILDKIT=1 docker build --no-cache -f {{project-name-lower}}_${OPERATION}/deploy_clients.Dockerfile ../ -t "{{project-name-lower}}_${OPERATION}_deploy_clients" --build-arg USER_ID=${USER_ID} --build-arg USER_GROUP_ID=${USER_GROUP_ID}
    fi
    sudo -E docker-compose -f {{project-name-lower}}_${OPERATION}/docker-compose.yml up --abort-on-container-exit --exit-code-from spring_boot
elif [[ ${OPERATION} == "prod" ]]; then
  sudo -E docker container rm {{project-name-lower}}_${OPERATION}_spring_boot_1
  sudo -E DOCKER_BUILDKIT=1 docker build --no-cache -f {{project-name-lower}}_${OPERATION}/spring_boot.Dockerfile ../ -t "{{project-name-lower}}_${OPERATION}_spring_boot"
elif [[ ${OPERATION} == "deploy_staging" ]]; then
  IMAGE_PATH={{openshift.image-path}}
  # build prod image
  ./docker.sh prod
  echo $(oc whoami -t) | sudo -E docker login --username $(oc whoami) --password-stdin $IMAGE_PATH
  sudo -E docker tag {{project-name-lower}}_prod_spring_boot $IMAGE_PATH
  sudo -E docker push $IMAGE_PATH
else
  echo "Unsupported build mode [${OPERATION}]"
  popd && exit 1
fi

DOCKER_EXIT_CODE=$?
popd && exit $DOCKER_EXIT_CODE