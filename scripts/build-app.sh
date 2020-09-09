set -e
git_id=$(git rev-parse --short=7 HEAD)
git_branch=$(git symbolic-ref --short HEAD)
HERE=$(dirname $0)

# This is the path for teamcity agents. If running locally, pass in your own docker config location
# i.e. /home/{user}/.docker/config.json
docker_auth_path=${1:-/opt/teamcity-agent/.docker/config.json}

# Make the build environment image that is shared between multiple build targets
./scripts/make-build-env.sh

# Create an image based on the shared build env that compiles, tests and dockerises
# the app
docker build --tag montagu-api-app-build \
	--build-arg git_id=$git_id \
	--build-arg git_branch=$git_branch \
    -f app.Dockerfile \
	.

# Run the created image
docker run --rm \
    -v /var/run/docker.sock:/var/run/docker.sock \
    -v $docker_auth_path:/root/.docker/config.json \
    --network=host \
    montagu-api-app-build

# tear down
docker-compose --project-name montagu down
$HERE/clear-docker.sh
