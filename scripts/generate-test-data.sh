set -e
# Make the build environment image that is shared between multiple build targets
./scripts/make-build-env.sh

# Setup the database on a named network
docker network create test-data
docker run -d --rm --name db --network=test-data -p "8000:5432" montagu.dide.ic.ac.uk:5000/montagu-db:master

# Generate the test data
docker build --tag montagu-test-data-build -f generate-test-data.Dockerfile .
docker run --rm --network=test-data montagu-test-data-build

# Dump the test data to an SQL file
docker exec db pg_dump -h localhost -U vimc -d montagu --data-only > ./test-data.sql

docker stop db
docker network rm test-data