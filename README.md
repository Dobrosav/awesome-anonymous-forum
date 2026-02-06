# Awesome Anonymous Forum

A robust, "medior-level" Spring Boot application designed for anonymous discussions. This project demonstrates modern software engineering practices, including clean architecture, DTO pattern usage, automated mapping with MapStruct, and integration testing with TestContainers.

## 🚀 Key Features

*   **Anonymous Posting**: Create posts and comments without registration.
*   **Multimedia Support**: Posts can include text, external links, and optional "Keanu Whoa" multimedia content via external API integration.
*   **Nested Comments**: Support for comments and replies.
*   **Modern Architecture**: Separation of concerns using Controller, Service, Repository layers, and strict DTO/Entity mapping.
*   **Containerized Environment**: Fully dockerized setup for application and database.

## 🛠 Tech Stack

*   **Java**: 21
*   **Framework**: Spring Boot 3.5.3
*   **Database**: MySQL 8.0.33
*   **ORM**: Spring Data JPA / Hibernate
*   **Mapping**: MapStruct 1.5.5.Final
*   **Testing**: JUnit 5, Mockito, TestContainers (for integration tests)
*   **Containerization**: Docker & Docker Compose
*   **Build Tool**: Maven

## 🏗 Architecture

The project follows a layered architecture to ensure maintainability and testability:

1.  **API Layer (`api`)**: REST Controllers that handle HTTP requests. They consume and return **DTOs** (`Request` objects and `Response` objects), never exposing database entities directly.
2.  **Service Layer (`service`)**: Contains business logic. Uses **Mappers** to convert between DTOs and Entities.
3.  **Data Access Layer (`database`)**: Spring Data JPA Repositories for database interactions.
4.  **Mapper Layer (`mapper`)**: **MapStruct** interfaces that define how to map between DTOs and Entities, reducing boilerplate code.

## 📋 Prerequisites

*   **Java JDK 21**
*   **Maven** (3.8+)
*   **Docker** & **Docker Compose**

## 🏃‍♂️ Running the Application

### Option 1: Using Docker Compose (Recommended)

This is the easiest way to run the full stack (App + Database).

1.  Build the application:
    ```bash
    mvn clean package -DskipTests
    ```

2.  Start the services:
    ```bash
    docker compose up --build
    ```

The application will be available at `http://localhost:11050`.

### Option 2: Running Locally with Maven

If you want to run the app locally (e.g., for debugging), ensure you have a MySQL database running on port `3306` (or configure `application.properties`).

```bash
mvn spring-boot:run
```

## 🧪 Testing

This project uses **TestContainers** to run integration tests against a real, throwaway MySQL Docker container. This ensures that tests are reliable and environment-independent.

To run the tests:

```bash
mvn test
```

*Note: You need a running Docker daemon for TestContainers to work.*

## 🔌 API Endpoints

### Posts

*   **Create Post**: `POST /posts`
    *   Body: `{ "content": "...", "authorName": "...", "postType": "PLAIN_TEXT" }`
*   **Get All Posts**: `GET /posts?page=0&size=10`
*   **Get Posts by Author**: `GET /posts/by-author/{authorName}`

### Comments

*   **Comment on Post**: `POST /posts/{postId}/comments`
    *   Body: `{ "content": "...", "authorName": "..." }`
*   **Reply to Comment**: `POST /comments/{parentCommentId}/replies`
    *   Body: `{ "content": "...", "authorName": "..." }`

### Swagger UI (API Documentation)

You can view and test the API using the built-in Swagger UI:

*   **URL**: `http://localhost:11050/swagger-ui/index.html`
*   **OpenAPI JSON**: `http://localhost:11050/v3/api-docs`

## 📁 Project Structure

```text
src/main/java/com/thereputeo/awesomeanonymousforum
├── api                  # REST Controllers & Request/Response Models
│   ├── model
│   │   ├── request      # Input DTOs
│   │   └── response     # Output DTOs
│   ├── CommentController.java
│   └── PostController.java
├── client               # External API Clients (WhoaService)
├── database             # Entities & Repositories
│   ├── entity
│   └── repository
├── exception            # Global Error Handling
├── mapper               # MapStruct Interfaces
└── service              # Business Logic
```
