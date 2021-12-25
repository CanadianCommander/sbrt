#!/bin/bash

pushd ../../target/generated-sources/client/ts/ || exit 1

echo '{{client.ts.npmrc-registry}}' > .npmrc
npm install
npm publish

popd || exit 1