#!/usr/bin/env bash
set -ex

HERE=$(dirname $0)

if [[ ! -z $NETWORK ]]; then
  # if running on a network, need this config to be able to talk to the api
  OPTION_MAPPING="--option montagu_url=http://api:8080 --option montagu_api_url=http://api:8080/v1 --option network=$NETWORK"
fi

# Install orderly-web
pip3 install orderly-web
orderly-web start $HERE $OPTION_MAPPING

$HERE/orderly-web-cli.sh add-users user@test.com
$HERE/orderly-web-cli.sh grant user@test.com */users.manage

