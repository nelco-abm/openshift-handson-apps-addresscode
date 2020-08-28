# Update the VARIANT arg in devcontainer.json to pick a Java version >= 11
ARG VARIANT=14
FROM openjdk:${VARIANT}-jdk-buster

# Options for setup script
ARG INSTALL_ZSH="true"
ARG UPGRADE_PACKAGES="false"
ARG USERNAME=vscode
ARG USER_UID=1000
ARG USER_GID=$USER_UID
ENV WORKSPACE_DIR "/workspace"

# Install needed packages and setup non-root user. Use a separate RUN statement to add your own dependencies.
COPY library-scripts/*.sh /tmp/library-scripts/
RUN /bin/bash /tmp/library-scripts/common-debian.sh "${INSTALL_ZSH}" "${USERNAME}" "${USER_UID}" "${USER_GID}" "${UPGRADE_PACKAGES}" \
    && if [ ! -d "/docker-java-home" ]; then ln -s "${JAVA_HOME}" /docker-java-home; fi \
    && apt-get clean -y && rm -rf /var/lib/apt/lists/* /tmp/library-scripts/common-debian.sh

# [Optional] Install Maven
ARG INSTALL_MAVEN="true"
ARG MAVEN_VERSION=3.6.3
ARG MAVEN_DOWNLOAD_SHA="no-check"
ARG MAVEN_CONFIG="/home/$USERNAME/.m2"
ENV MAVEN_HOME=/usr/local/share/maven
RUN if [ "${INSTALL_MAVEN}" = "true" ]; then /bin/bash /tmp/library-scripts/maven-debian.sh ${MAVEN_VERSION} ${MAVEN_HOME} ${USERNAME} ${MAVEN_DOWNLOAD_SHA}; fi \
    && rm -f /tmp/library-scripts/maven-debian.sh

