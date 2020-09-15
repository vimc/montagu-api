#!/usr/bin/env bash
set -ex
HERE=$(dirname $0)
ROOT=$(realpath $HERE/..)

if [[ ! -z $NETWORK ]]; then
  NETWORK_MAPPING="--network=$NETWORK"
else
  # if no network provided, use db default network
  NETWORK_MAPPING="--network=db_nw"
fi

docker run --rm -d \
  $NETWORK_MAPPING \
  -p 5672:5672 \
  --name mq \
  rabbitmq

docker pull vimc/task-queue-worker:master
docker run --rm -d \
  $NETWORK_MAPPING \
  -v $ROOT/scripts/task-queue-config.yml:/home/worker/config/config.yml \
  --name task_queue_worker \
  vimc/task-queue-worker:master

# add task q user
CLI=vimc/montagu-cli:master
docker pull $CLI
docker run --rm \
  $NETWORK_MAPPING \
  $CLI \
  add "Task User" task.user task.user@example.com password --if-not-exists

docker run --rm \
  $NETWORK_MAPPING \
  $CLI addRole task.user user

$HERE/orderly-web-cli.sh add-users task.user@example.com
$HERE/orderly-web-cli.sh grant task.user@example.com */reports.read */reports.run */reports.review
