# Montagu API

## Running the app locally
System requirements:
* openjdk 8
* Docker

Install Docker and add your user to the Docker group (e.g. https://www.digitalocean.com/community/tutorials/how-to-install-and-use-docker-on-ubuntu-16-04.) You may need to restart your machine for changes to take effect.

Configure your Docker client to use our registry by following instructions here:
https://github.com/vimc/montagu-ci#configuring-docker-clients-to-use-the-registry

Start the database by navigating to the `src` folder and running

    ./gradlew :startDatabase

Run the app

    ./gradlew :run
   
## Generating a root token
For use with the Ebola work, we wanted to be able to run the [reporting API](https://github.com/vimc/montagu-reporting-api)
without this main API. To circumvent the normal authorization, we added a mode
that allows you to run generate a root token with a 1 year expiry that gives 
access to all reports. This can then be hardcoded into the reporting portal and
any other code that needs access to this API-less version of Montagu.

To generate, first create a keypair at some TOKEN_KEY_PATH and then run:

```
docker run --rm \
    -v $TOKEN_KEY_PATH:/etc/montagu/api/token_key \
    docker.montagu.dide.ic.ac.uk:5000/montagu-api:$MONTAGU_API_VERSION \
    generate-token */can-login */reports.read */reports.review
```

The reporting API needs to be run with the public key from the keypair.

## Running tests
To run the Blackbox tests, you will need to start the database and run the app as described above. Note that if you want to run individual tests through IntelliJ, you will need to manually run the `copySpec` Gradle task first.

To run Blackbox tests from the command line, after running the above 2 commands, from the same folder run

     ./gradlew :blackboxTests:run

## Upgrading dependencies
Run `./gradlew dependencyUpdates` and then manually update as required.

## Project anatomy
At the top level we have four folders of note:
* `src/`: The source code of the application and its tests and helpers
* `spec/`: The formal API specification. The API must conform to this. Developers writing clients use this.
* `scripts/`: Shell scripts used to automate build tasks (mainly used by TeamCity, but they should work for you too)
* `demo/`: The proof-of-concept API demo developed for the 2017 annual VIMC conference

### Source code
This is a Kotlin application. I use IntelliJ IDEA to develop it. The build system is Gradle. The root Gradle files are `src/build.gradle` and `src/settings.gradle`.

They define the following subprojects:
* `app/`: This contains the main code of the application that serves the API over HTTP. It contains a high-level database interface that wraps around the low-level `databaseInterface` project. It also (in `app/src/test`) contains unit tests.
* `databaseInterface/`: This contains the code for low-level interactions with the database, using jOOQ. It is largely generated Java code, with a few Kotlin classes that act as helpers.
* `generateDatabaseInterface/`: This is a very small program that is disjoint with this rest of the codebase (nothing depends on it, it depends on nothing). It invokes jOOQ's code generation to generate the Java classes in `databaseInterface`.
* `testHelpers/`: Code shared between the three kinds of tests.
* `databaseTests/`: In addition to the unit tests that run with no IO and no dependencies, we also have `databaseTests`. These use `databaseInterface` to set up the database in known states, and then test that the high-level repository layer  reads from or mutates the database state in the expected way. These could be considered a partial integration test: Checking integration with the database, but not actually running the API.
* `blackboxTests/`: The final kind of test is a full integration test. We run both a database and the API. We then use separate Kotlin code to interact with the API as a client and check that the results conform to the spec. Note that we again use the low-level `databaseInterface` to set up the database in a known state. Note that in TeamCity there are two build configurations: The first runs the unit and database tests, and then stores an app image in the Docker registry. The second, which runs the blackbox tests, actually uses this built image and runs the tests against the containerised API.

## Docker build
This is what the CI system does:

1. `./scripts/make-build-env.sh` - This builds a Docker image that contains OpenJDK, Gradle and the source files
2. `./scripts/run-build.sh` - Within that built docker image it compiles the code, runs the unit tests, and builds a second Docker image. This second Docker image contains the compiled code and a Java Runtime Environment.

## Docker run
To make use of a built image, run:

    docker pull docker.montagu.dide.ic.ac.uk:5000/montagu-api:master
    docker run --rm -p 8080:8080 docker.montagu.dide.ic.ac.uk:5000/montagu-api:master

Subsitute a different branch or 7-character commit hash in place of 'master' to get a different version.


