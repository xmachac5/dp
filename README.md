# cqrs-kafka-app

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script

Použite wrapper Gradle na spustenie vašej aplikácie:
podman machine start - pokial nebezi
podman start zookeeper
podman start postgres
podman start kafka
(Keď sa niektorý nepodarí znova vytvoriť ako v krokoch vyššie)
podman ps (zvyčajne nenabehne kafka)
SKontrolovat ci su vsetky topics:
podman exec -it kafka kafka-topics.sh --list --bootstrap-server localhost:9094
Ked nie je tak:
podman exec -it kafka kafka-topics.sh --create --topic user-commands --bootstrap-server localhost:9094 --partitions 1 --replication-factor 1
podman exec -it kafka kafka-topics.sh --create --topic user-events --bootstrap-server localhost:9094 --partitions 1 --replication-factor 1
./gradlew clean build
./gradlew quarkusDev

Swager:
http://localhost:8080/swagger-ui/

```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./gradlew build
```

It produces the `quarkus-run.jar` file in the `build/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `build/quarkus-app/lib/` directory.

The application is now runnable using `java -jar build/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./gradlew build -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar build/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./gradlew build -Dquarkus.native.enabled=true
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./gradlew build -Dquarkus.native.enabled=true -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./build/cqrs-kafka-app-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/gradle-tooling>.

## Related Guides

- Messaging - Kafka Connector ([guide](https://quarkus.io/guides/kafka-getting-started)): Connect to Kafka with Reactive Messaging

## Provided Code

### Messaging codestart

Use Quarkus Messaging

[Related Apache Kafka guide section...](https://quarkus.io/guides/kafka-reactive-getting-started)

