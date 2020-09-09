#!/usr/bin/env bash
set -ex

here=$(dirname $0)

export MONTAGU_API_VERSION=master
export MONTAGU_DB_VERSION=master
export ORDERLY_SERVER_USER_ID=$UID
registry=docker.montagu.dide.ic.ac.uk:5000
migrate_image=$registry/montagu-migrate:$MONTAGU_DB_VERSION

$here/run-orderly-web-deps.sh

# Run API, DB, orderlyweb and task q
docker-compose pull || true
docker-compose --project-name montagu up -d
docker exec montagu_api_1 mkdir -p /etc/montagu/api/
docker exec montagu_api_1 touch /etc/montagu/api/go_signal

# This chunk should be refactored to be shared with the db repo
# -------------------------------------------------------------
docker exec montagu_db_1 montagu-wait.sh

docker pull $migrate_image || true
docker run --rm --network=montagu_default $migrate_image

# -------------------------------------------------------------

docker exec montagu_orderly_web_1 mkdir -p /etc/orderly/web
docker exec montagu_orderly_web_1 touch /etc/orderly/web/go_signal

$here/setup-task-queue.sh
