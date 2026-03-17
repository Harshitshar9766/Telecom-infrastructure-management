# Tower Health Dashboard for Telecom Networks

This is a college project simulating a telecom tower monitoring system.
The backend is implemented using **Java Spring Boot** (MVC + REST), **Spring Data JPA**, and uses an H2 in-memory database by default (can be switched to MySQL).
Frontend pages are built with **Thymeleaf** and **Bootstrap**.

## Project Structure

- `src/main/java/com/example/towerdashboard`
  - `model` - JPA entity classes and enums
  - `repository` - Spring Data JPA repositories
  - `service` - Service layer handling business logic
  - `controller` - REST controllers and Thymeleaf page controllers
  - `TowerDashboardApplication.java` - Spring Boot entry point

- `src/main/resources`
  - `application.properties` - Database and application configuration
  - `data.sql` - Sample data inserted on startup (creates towers, technicians, errors, assignments)
  - `templates` - Thymeleaf HTML templates for UI pages

- `pom.xml` - Maven build file

## Entities and Database Tables

The application defines the following entities which correspond to database tables:

1. **Tower** (`towers`)
   - `towerId`, `location`, `city`, `region`, `status`, `lastUpdated`
   - `TowerStatus` enum: ACTIVE, ERROR, UNDER_MAINTENANCE, FIXED

2. **ErrorReport** (`error_reports`)
   - `errorId`, `towerId`, `errorType`, `description`, `priority`, `reportedTime`, `status`
   - `Priority` enum: LOW, MEDIUM, HIGH, CRITICAL
   - `ErrorStatus` enum: OPEN, IN_PROGRESS, RESOLVED

3. **Technician** (`technicians`)
   - `techId`, `name`, `phone`, `region`

4. **Assignment** (`assignments`)
   - `assignmentId`, `towerId`, `techId`, `etaHours`, `status`, `assignedTime`
   - `AssignmentStatus` enum: ASSIGNED, IN_PROGRESS, COMPLETED

Tables are created automatically via `spring.jpa.hibernate.ddl-auto=update` and sample rows are inserted from `data.sql` at startup.

## API Endpoints

### Tower APIs
- **GET** `/api/towers` - list all towers
- **POST** `/api/towers` - add a new tower
- **GET** `/api/towers/{id}` - get details of a tower
- **PUT** `/api/towers/{id}/status?status=ACTIVE` - update tower status

### Error APIs
- **POST** `/api/errors/report` - report a new error
- **GET** `/api/errors` - list all errors
- **GET** `/api/errors/open` - list open errors
- **PUT** `/api/errors/{id}/status?status=RESOLVED` - update error status

### Technician APIs
- **POST** `/api/technicians` - add a technician
- **GET** `/api/technicians` - get technician list

### Assignment APIs
- **POST** `/api/assignments` - create assignment
- **GET** `/api/assignments` - list assignments
- **PUT** `/api/assignments/{id}/status?status=COMPLETED` - update assignment status

### Dashboard API
- **GET** `/api/dashboard/stats` - get dashboard statistics

## Frontend Pages

- `/` - Dashboard with cards and open error table
- `/towers` - Tower list page
- `/errors/report` - Error reporting form
- `/technicians` - Technician management form and list
- `/assignments` - Assign technician form and assignment list

Use the navigation bar to move between pages.

## Running the Application

1. Ensure JDK 17 and Maven are installed.
2. Open a terminal in the project root.
3. Run:
   ```bash
   mvn spring-boot:run
   ```
4. Access the UI at `http://localhost:8080/`.
5. The H2 console is available at `http://localhost:8080/h2-console` (JDBC URL `jdbc:h2:mem:towerdb`, user `sa`).

To use MySQL instead of H2, update `spring.datasource.*` properties in `application.properties` accordingly and ensure the MySQL server is running.

## Notes

- All database tables are created automatically if they do not exist.
- Sample data is inserted at startup via `data.sql`.
- The project uses Maven as the build tool and Java 17.

Enjoy exploring the Tower Health Dashboard!