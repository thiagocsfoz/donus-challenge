########Maven build stage########
FROM maven:3.6-jdk-8 as maven_build
WORKDIR /app

#Copy pom
COPY ./pom.xml .

# resolve maven dependency
RUN     mvn dependency:resolve-plugins && \
        mvn spring-boot:start -Dspring-boot.run.skip

#Copy source
COPY ./src ./src

# build the app (no dependency download here)
RUN mvn clean package  -Dmaven.test.skip
RUN mkdir -p target/docker-packaging && cd target/docker-packaging && jar -xvf ../account-service-*.jar

########JRE run stage########
FROM openjdk:8-jdk-alpine
WORKDIR /app

#copy packaged app layer by layer
ARG DOCKER_PACKAGING_DIR=/app/target/docker-packaging
COPY --from=maven_build ${DOCKER_PACKAGING_DIR}/BOOT-INF/lib /app/lib
COPY --from=maven_build ${DOCKER_PACKAGING_DIR}/BOOT-INF/classes /app/classes
COPY --from=maven_build ${DOCKER_PACKAGING_DIR}/META-INF /app/META-INF

#run the app
CMD java -cp .:classes:lib/* \
         -Djava.security.egd=file:/dev/./urandom \
         com.donus.accountservice.AccountServiceApplication