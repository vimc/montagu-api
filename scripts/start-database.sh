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
$here/../db/scripts/start.sh $DB_VERSION $DB_PORT

docker exec db psql -U postgres -d montagu -c "create extension tablefunc;"

echo "-------------------------------------------------------------------------"
echo "Databases are now running:"
echo "Main database is accessible at port $DB_PORT"
