# Tech Challenge 2025 – Backend (Spring Boot)

This is the backend service for the Tech Challenge 2025 project. It is built with Spring Boot and provides RESTful APIs for trade management, user management, and related business entities.

## Prerequisites
- Java 21 or higher
- Maven 3.8+

## Setup & Run

1. **Install dependencies:**
   ```sh
   mvn clean install
   ```
2. **Run the backend:**
   ```sh
   mvn spring-boot:run
   ```
   The backend will start on [http://localhost:8080](http://localhost:8080).

3. **API Documentation:**
   - Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

4. **Database:**
   - Uses H2 in-memory by default. For PostgreSQL, update `src/main/resources/application.properties`.

5. **CORS:**
   - CORS is enabled for `http://localhost:5173` and `http://localhost:3000` by default.

## Project Structure
- `src/main/java/com/technicalchallenge/` – Java source code
- `src/main/resources/` – Configuration and data files
- `src/test/java/` – Unit and integration tests

## Useful Commands
- Run: `mvn spring-boot:run`
- Test: `mvn test`

## Notes
- Update `application.properties` for custom DB or port settings.
- All API endpoints are prefixed with `/api`.

