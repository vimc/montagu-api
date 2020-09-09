#!/usr/bin/env bash
set -ex
HERE=$(dirname $0)
. $HERE/common

# In case we switch agents between steps
[ ! -z $(docker images -q $BUILD_ENV_TAG) ] || docker pull $BUILD_ENV_TAG

# This is the path for Buildkite agents. If running locally, pass in your own docker config location
# i.e. /home/{user}/.docker/config.json
DOCKER_AUTH_PATH=${1:-/var/lib/buildkite-agent/.docker/config.json}

# Create an image based on the shared build env that compiles, tests and dockerises
# the app
docker build --tag montagu-api-app-build \
    --build-arg git_id=$GIT_SHA \
    --build-arg git_branch=$GIT_BRANCH \
    -f ./docker/app.Dockerfile \
    .

# Run the created image
docker run --rm \
    -v /var/run/docker.sock:/var/run/docker.sock \
    -v $DOCKER_AUTH_PATH:/root/.docker/config.json \
    --network=host \
    montagu-api-app-build
