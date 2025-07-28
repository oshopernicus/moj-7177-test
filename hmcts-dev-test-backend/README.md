# HMCTS Dev Test Backend

This will be the backend for the brand new HMCTS case management system. As a potential candidate we are leaving this in your hands. Please refer to the brief for the complete list of tasks! Complete as much as you can and be as creative as you want.

## Prerequisites

- Java 11 or higher
- Gradle (or use the included Gradle wrapper)
- An IDE of your choice (e.g., IntelliJ IDEA)

## Building the Project

Run the following command to build the project:

```bash
./gradlew build
```

This will compile the code, run the tests, and package the application.

## Running the Service

You can run the service using your IDE or the command line. To run it from the command line, use:

```bash
./gradlew bootRun
```

The service will start on `http://localhost:8080` by default.

## Example Endpoint

There is an example endpoint provided to retrieve an example of a case. You are free to add/remove fields as you wish. The endpoint can be accessed at:

```
GET /api/cases/example
```

## Testing

To run the tests, execute:

```bash
./gradlew test
```

Ensure all tests pass before making any changes or submitting your work.

## Notes for Developers

- Follow best practices for REST API design.
- Add meaningful tests for any new functionality.
- Document any additional endpoints or features you implement.
