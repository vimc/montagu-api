#!/usr/bin/env bash
set -e

# Expects the following environment variables:
# DB_VERSION (the tag of the database + migrations image to use)
# DB_PORT    (the port that the db container should expose to the host machine)
# NAME       (the name to assign to the db container)
registry=docker.montagu.dide.ic.ac.uk:5000
db_image=$registry/montagu-db:$DB_VERSION
migrations_image=$registry/montagu-migrate:$DB_VERSION
url=jdbc:postgresql://localhost:$DB_PORT/montagu

echo "Using these images:"
echo " - $db_image"
echo " - $migrations_image"
docker pull $db_image
docker pull $migrations_image

# If the database is already running, stop it
if docker top $NAME &>/dev/null; then
    echo "Stopping database"
    docker stop $NAME
    sleep 1s
fi

echo "Starting database"
docker run -d --rm -p "$DB_PORT:5432" --name $NAME \
    --entrypoint start-with-config.sh \
    $db_image /postgresql.test.conf

docker exec $NAME montagu-wait.sh

echo "Migrating"
docker run --rm --network=host $migrations_image migrate -url=$url

echo "----------------------------------------"
echo "Database is now running on port $DB_PORT"

