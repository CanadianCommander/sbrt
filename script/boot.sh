#!/bin/bash
DEV_MODE=false

print_usage()
{
  echo "boot.sh  [-d]"
  echo "  -d if provided run in test mode. This script will expect to be inside the source code directory. It will not download any thing from GH."
  exit 1
}

if [[ "${1}" == "--help" ]] || [[ "${1}" == "-h" ]]; then
  print_usage
fi

while getopts "d" option; do
  case ${option} in
    d ) DEV_MODE=true
    ;;
    \? ) print_usage
  esac
done

export DEV_MODE

# check for git
which git > /dev/null
if (( $? == 1 )); then
  echo "Git not detected on your system. Please install it and try again. See: https://git-scm.com/downloads"
  exit 1
fi

# check for ruby
which ruby > /dev/null
if (( $? == 1 )); then
  echo "Ruby not detected on your system. Please install it and try again. See: https://www.ruby-lang.org/en/downloads/"
  exit 1
fi

# check for docker
which docker > /dev/null
if (( $? == 1 )); then
  echo "Docker not detected on your system. Please install it and try again. See: https://docs.docker.com/engine/install/"
  exit 1
fi

if [[ $DEV_MODE == true ]]; then
  pushd "$(dirname "$0")" > /dev/null || exit 1

  echo "Copying template to testing directory, target/deploy_test/"
  # copy code to test directory
  sudo rm -rf ../target/deploy_test
  mkdir -p ../target/deploy_test
  cp -r ../docker ../target/deploy_test/
  cp -r ../lib ../target/deploy_test/
  cp -r ../openapi ../target/deploy_test/
  cp -r ../script ../target/deploy_test/
  cp -r ../src ../target/deploy_test/
  cp -r ../.mvn ../target/deploy_test/
  cp -r ../mvnw* ../target/deploy_test/
  cp -r ../pom.xml ../target/deploy_test/

  pushd ../target/deploy_test/script/ > /dev/null || exit 1
  ruby ./project_setup.rb
  popd > /dev/null && popd > /dev/null || exit 1
else
  echo "Downloading template..."
  git clone https://github.com/CanadianCommander/sbrt.git

  pushd sbrt/script/ > /dev/null || exit 1
  ruby ./project_setup.rb
  popd > /dev/null || exit 1
fi