#!/bin/bash

pushd ../../target/generated-sources/client/ruby/ || exit 1

/home/{{container-user}}/.rbenv/shims/gem build {{project-name}}-ruby-client.gemspec
/home/{{container-user}}/.rbenv/shims/gem push --key {{client.ruby.client-key}} --host {{client.ruby.repo-url}} {{project-name}}-ruby-client*.gem

popd || exit 1