#!/usr/bin/env bash
set -ex

ORDERLY_IMAGE="vimc/orderly:master"
OW_MIGRATE_IMAGE="vimc/orderlyweb-migrate:master"

here=$(dirname $0)
root=$(realpath $here/..)

# create orderly db
if [[ -d $root/src/demo ]]
then
  rm $root/src/demo -rf
  rm $root/src/git -rf
fi

docker pull $ORDERLY_IMAGE
docker run --rm --entrypoint create_orderly_demo.sh -v "$root/src:/orderly" -u $UID -w /orderly $ORDERLY_IMAGE .


# migrate to add orderlyweb tables
docker pull $OW_MIGRATE_IMAGE
docker run --rm -v "$root/src/demo:/orderly" $OW_MIGRATE_IMAGE

$here/orderly-web-cli.sh add-users user@test.com
$here/orderly-web-cli.sh add-users grant user@test.com */users.manage
