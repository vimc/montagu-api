#!/usr/bin/env bash
set -e

here=$(dirname $0)

# If the database is already running, stop it
if docker top db &>/dev/null; then
    echo "Stopping database"
    $here/../db/scripts/stop.sh
    sleep 1s
fi

echo "Starting database"
$here/../db/scripts/start.sh $DB_VERSION

echo "----------------------------------------"
echo "Database is now running on port 5432"

