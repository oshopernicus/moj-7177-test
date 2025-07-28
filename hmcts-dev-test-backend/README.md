# HMCTS Dev Test Backend

This is the backend service for the HMCTS case management system, providing a RESTful API for managing tasks.

## Prerequisites
- Java 11 or higher
- Gradle (or use the included Gradle wrapper)

## Setup & Running

1. **Build the project:**
   ```sh
   ./gradlew build
   ```

2. **Run the service:**
   ```sh
   ./gradlew bootRun
   ```
   The service will start on `http://localhost:8080` by default.

## API Endpoints

All endpoints are prefixed with `/api/tasks`.

### 1. Create a Task
- **POST** `/api/tasks`
- **Request Body:** JSON Task object
- **Response:** 200 OK with created Task, or 400 Bad Request

### 2. Get Task by ID
- **GET** `/api/tasks/{id}`
- **Response:** 200 OK with Task, or 404 Not Found

### 3. Get All Tasks
- **GET** `/api/tasks`
- **Response:** 200 OK with list of Tasks

### 4. Update Task by ID
- **PUT** `/api/tasks/{id}`
- **Request Body:** JSON Task object
- **Response:** 200 OK with updated Task, or 404 Not Found

### 5. Delete Task by ID
- **DELETE** `/api/tasks/{id}`
- **Response:** 200 OK if deleted, or 404 Not Found

## Testing

Run all tests with:
```sh
./gradlew test
```
