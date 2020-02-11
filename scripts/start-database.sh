#!/usr/bin/env bash
set -ex
here=$(dirname $0)

# If the database is already running, stop it
if docker top db &>/dev/null; then
    echo "Stopping database"
    $here/../db/scripts/stop.sh
    sleep 1s
fi

echo "Starting database"
$here/../db/scripts/start.sh $DB_VERSION $DB_PORT $ANNEX_PORT

echo "-------------------------------------------------------------------------"
echo "Databases are now running:"
echo "Main database is accessible at port $DB_PORT"
echo "Annex database is accessible at port $ANNEX_PORT"
