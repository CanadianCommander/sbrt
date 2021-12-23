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

# check for ruby
which ruby > /dev/null
if (( $? == 1 )); then
  echo "Ruby not detected on your system. Please install it and try again. See: https://www.ruby-lang.org/en/downloads/"
fi

if [[ $DEV_MODE ]]; then
  pushd "$(dirname "$0")" > /dev/null || exit 1

  echo "Copying template to testing directory, target/deploy_test/"
  # copy code to test directory
  rm -rf ../target/deploy_test
  mkdir -p ../target/deploy_test
  cp -r ../docker ../target/deploy_test/
  cp -r ../lib ../target/deploy_test/
  cp -r ../openapi ../target/deploy_test/
  cp -r ../script ../target/deploy_test/
  cp -r ../src ../target/deploy_test/
  cp -r ../mvnw* ../target/deploy_test/
  cp -r ../pom.xml ../target/deploy_test/

  pushd ../target/deploy_test/script/ > /dev/null || exit 1
  ruby ./project_setup.rb
  popd > /dev/null && popd > /dev/null || exit 1
else
  echo "NOT DONE YET"
fi