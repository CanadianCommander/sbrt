#!/bin/bash
pushd "$(dirname "$0")" > /dev/null || exit 1

# generate client code
pushd ../../ || exit 1
./mvnw clean package -Dmaven.test.skip=true
popd || exit 1

# run all deployment scripts one at a time.
for script in *.sh; do
  if [[ "${script}" != "deploy.sh" ]]; then
    bash "$script"

    if (( $? != 0 )); then
      echo "Failed to deploy one or more API clients do to errors"
      exit 1
    fi
  fi
done

popd || exit 1