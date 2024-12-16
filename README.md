# cqrs-kafka-app

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

This application is part of Master thesis by Ján Macháček, validating usage of CQRS pattern in low-code platforms

## Running the application in dev mode

Prerequisites for the running the app is to have:

* Podman <https://quarkus.io/guides/podman>
* JDK 17+ installed with JAVA_HOME configured appropriately
* Apache Maven 3.9.9

You can run your application in dev mode that enables live coding using:

```shell script


./gradlew setupEnvironment
./gradlew clean build
./gradlew quarkusDev

```

After the running the app, test version is available at Swager: <http://localhost:8080/swagger-ui/>

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Related Guides

- Messaging - Kafka Connector ([guide](https://quarkus.io/guides/kafka-getting-started)): Connect to Kafka with Reactive Messaging

## Provided Code

### Messaging codestart

Use Quarkus Messaging

[Related Apache Kafka guide section...](https://quarkus.io/guides/kafka-reactive-getting-started)

