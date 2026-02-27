# AlgaSensors Temperature Monitoring

Temperature Monitoring is a Spring Boot microservice in the AlgaSensors study project. It focuses on ingesting temperature readings from devices.

## Responsibilities

- Receive temperature readings from devices.
- Validate and normalize incoming measurements.
- Publish events for downstream processing.

## Tech Stack

- Java 21+
- Spring Boot
- Gradle (wrapper included)

## Run Locally

1. Start shared infrastructure from the workspace root:

	docker compose up -d

2. Start this service:

	./gradlew bootRun

## Configuration

- Defaults are in src/main/resources/application.yml.
- Override via environment variables or an external application.yml.

## Related Services

- device-management: provides device metadata used for validation.
- temperature-processing: consumes events produced by this service.
