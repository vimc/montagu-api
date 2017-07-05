FROM montagu-api-build-environment

ARG git_id='UNKNOWN'
ARG git_branch='UNKNOWN'
ARG registry=docker.montagu.dide.ic.ac.uk:5000
ARG name=montagu-api

ENV GIT_ID $git_id
ENV APP_DOCKER_TAG $registry/$name
ENV APP_DOCKER_COMMIT_TAG $registry/$name:$git_id
ENV APP_DOCKER_BRANCH_TAG $registry/$name:$git_branch

curl https://download.libsodium.org/libsodium/releases/libsodium-1.0.12.tar.gz \
    | tar xz --directory lib/
pushd lib/libsodium-1.0.12/
./configure
make && make check
sudo make install
popd

CMD ./gradlew testLibrary :app:distDocker -i -Pdocker_version=$GIT_ID -Pdocker_name=$APP_DOCKER_TAG \
    && docker tag $APP_DOCKER_COMMIT_TAG $APP_DOCKER_BRANCH_TAG \
    && docker push $APP_DOCKER_BRANCH_TAG
