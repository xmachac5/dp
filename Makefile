# Variables for service configuration
KAFKA_CONTAINER_NAME=kafka
ZOOKEEPER_CONTAINER_NAME=zookeeper
POSTGRES_CONTAINER_NAME=postgres
POSTGRES_DB=cqrs_kafka_db

# Start all the required services
start_services:
    # Start Zookeeper
	podman run -d --name $(ZOOKEEPER_CONTAINER_NAME) -p 2181:2181 zookeeper
    # Start Kafka
	podman run -d --name $(KAFKA_CONTAINER_NAME) --network=host -e KAFKA_ZOOKEEPER_CONNECT=localhost:2181 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9094 -p 9094:9094 wurstmeister/kafka:latest
    # Start PostgreSQL
	podman run -d --name $(POSTGRES_CONTAINER_NAME) -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=$(POSTGRES_DB) -p 5432:5432 postgres

# Create necessary Kafka topics
create_topics:
    # Create the "user-commands" topic for producing events
    podman exec -it $(KAFKA_CONTAINER_NAME) kafka-topics.sh --create --topic user-commands --bootstrap-server localhost:9094 --partitions 1 --replication-factor 1
    # Create the "user-events" topic for consuming events
    podman exec -it $(KAFKA_CONTAINER_NAME) kafka-topics.sh --create --topic user-events --bootstrap-server localhost:9094 --partitions 1 --replication-factor 1
# Run the Quarkus application
run_app:
    ./gradlew quarkusDev

# Stop all services
stop_services:
    podman stop $(ZOOKEEPER_CONTAINER_NAME) $(KAFKA_CONTAINER_NAME) $(POSTGRES_CONTAINER_NAME)
    podman rm $(ZOOKEEPER_CONTAINER_NAME) $(KAFKA_CONTAINER_NAME) $(POSTGRES_CONTAINER_NAME)

# Clean all resources and stop services
clean: stop_services

# Default command to run everything in order
all: start_services create_topics run_app
