#!/usr/bin/env bash
set -ex
HERE=$(dirname $0)
ROOT=$(realpath $HERE/..)

OW_CLI_IMAGE="vimc/orderly-web-user-cli:master"
docker run -v $ROOT/src/demo:/orderly $OW_CLI_IMAGE "$@"
