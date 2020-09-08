#!/usr/bin/env bash
set -ex
HERE=$(dirname $0)
ROOT=$(realpath $HERE/..)

# add task q user
CLI=vimc/montagu-cli:master
docker pull $CLI
docker run --rm --network montagu_default $CLI \
  add "Task User" task.user \
  task.user@example.com password \
  --if-not-exists

docker run --rm --network montagu_default $CLI addRole task.user user

$HERE/orderly-web-cli.sh add-users task.user@example.com
$HERE/orderly-web-cli.sh grant task.user@example.com */reports.read */reports.run */reports.review

# configure task q
docker cp $ROOT/scripts/task-queue-config.yml montagu_task_queue_1:home/worker/config/config.yml
