# Facts App - Quarkus
This is a **Quarkus**-based application for managing and retrieving interesting facts. The application has been built using **Jakarta EE**, **Kotlin**, and supports various structured services for caching, REST API handling, and remote data fetching.
## Features
- **REST API**:
    - Exposes endpoints for retrieving and managing facts.
    - Includes admin-specific functionalities.

- **Caching**: Implements a customizable caching layer for efficient fact retrieval.
- **Remote Integration**:
    - Fetches facts from external sources using an external client.

- **Data Models**:
    - Includes statistics tracking for fact usage.

- **Error Handling**: Implements custom exception management for smooth user experience.
- **JUnit Tests**: Provides unit tests to ensure code reliability.

## Project Structure
The project is modular and contains the following core components:
### 1. **Controllers**
- `FactsController`: Provides endpoints for interacting with facts.
- `AdminController`: Manages admin operations.

### 2. **Services**
- `FactService`: Primary service interface for managing logic around facts.
- `FactServiceImpl`: Implementation of FactService with business logic.
- `FactCacheService`: Handles caching of facts for performance improvements.
- `FactCacheServiceImpl`: Implementation of caching service.

### 3. **Clients**
- `RemoteFactsClient`: Provides integration with external data sources for fetching facts.

### 4. **Models**
- `FactEntity`: Core database entity for facts.
- `FactEntityWithStats`: Extension of FactEntity with additional usage statistics.
- `FactDto`: Data Transfer Object for facts.
- `FactDtoWithStats`: Dto with additional statistical data.

### 5. **Exceptions**
- `FactNotFoundException`: Exception handling for non-existing facts.

### 6. **Resources**
- `application.properties`: Configuration file for the application.
- `logback.xml`: Logging configuration.

## Installation
### Prerequisites
- **Java 21 SDK**
- **Maven** (for dependency management)
- **Quarkus CLI** (optional but recommended)

### Steps
1. Clone the repository:
``` bash
   git clone https://github.com/mashole/facts-app-quarkus.git
   cd facts-app-quarkus/server
```
1. Build the project:
``` bash
   mvn clean package
```
1. Run the application:
``` bash
   ./mvnw quarkus:dev
```
## Usage
### 1. API Endpoints
#### Public Endpoints
- **GET /facts**: Retrieve a list of cached facts.
- **GET /facts/{id}**: Get a specific fact by ID.
- **POST /facts**: Retrieve a random fact and cache it.

#### Admin Endpoints
- **GET /admin/statistics**: retrieve a list of cached facts with access statistic 

### 2. Configuration
- Update the `application.properties` file to configure remote API URLs, caching durations, etc.

## Testing
1. Run all tests:
``` bash
   ./mvnw test
```
The test suite uses JUnit to validate:
- Service logic
- Caching functionality
- Controller behavior

## Technologies Used
- **Quarkus**: Main framework
- **Kotlin**: Programming language
- **Jakarta EE**: For enterprise-grade configurations
- **JUnit**: For test cases
- **Maven**: Dependency and build management
