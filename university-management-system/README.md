# University Management System

## RESTful API for university management built with Spring Boot.

##### The application allows managing students, teachers, courses, and enrollments. It supports CRUD operations, filtering, searching, course enrollment, payments, grades, GPA calculation, student transcripts, Swagger documentation, and tests.

## Technologies

- Java 21
- Spring Boot 3
- Spring Web
- Spring Data JPA
- PostgreSQL
- Flyway
- Bean Validation
- Lombok
- Swagger / OpenAPI
- JUnit 5
- Mockito
- MockMvc
- Gradle

## Project Structure

```text
src/main/java/ua/university/sms
 ├── config
 ├── controller
 ├── exception
 ├── model
 │    ├── dto
 │    │    ├── request
 │    │    └── response
 │    └── entity
 ├── repository
 └── service
```

## Main Features

### Students
* Create student
* Get student by ID
* Get all students
* Update student
* Delete student
* Filter students by status
* Filter students by study year
* Search students by first name, last name, or email
* Get students with unpaid courses
* Get student transcript with GPA

### Teachers

* Create teacher
* Get teacher by ID
* Get all teachers
* Filter teachers by department
* Update teacher
* Delete teacher

### Courses

* Create course
* Get course by ID
* Get all courses
* Filter courses by teacher
* Filter courses by credits
* Update course
* Delete course

### Enrollments

* Enroll student in course
* Get enrollment by ID
* Get all enrollments
* Update enrollment
* Delete enrollment
* Assign grade
* Mark enrollment as paid
* Get unpaid enrollments
* Calculate student GPA
* Calculate average grade by course
* Calculate average grade by semester
* Get top students by GPA

## Database

### The project uses PostgreSQL.

#### Default database configuration:

```text
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/university_db
    username: postgres
    password: postgres
```

#### Before running the application, create the database:

```text
CREATE DATABASE university_db;
```

#### Database schema and initial data are managed by Flyway migrations:

```text
src/main/resources/db/migration/V1__create_tables.sql
src/main/resources/db/migration/V2__populate_tables.sql
API Documentation
```

### Swagger UI is available at:

```text
http://localhost:8080/swagger-ui.html
```

### OpenAPI docs are available at:

```text
http://localhost:8080/v3/api-docs
```

## Main API Endpoints

### Students
```text
GET    /api/students
GET    /api/students/{id}
POST   /api/students
PUT    /api/students/{id}
DELETE /api/students/{id}
GET    /api/students?status=ACTIVE
GET    /api/students?year=2
GET    /api/students?search=alice
GET    /api/students/unpaid
GET    /api/students/{id}/transcript
```

### Teachers
```text
GET    /api/teachers
GET    /api/teachers/{id}
POST   /api/teachers
PUT    /api/teachers/{id}
DELETE /api/teachers/{id}
GET    /api/teachers?department=Computer
```

### Courses

```text
GET    /api/courses
GET    /api/courses/{id}
POST   /api/courses
PUT    /api/courses/{id}
DELETE /api/courses/{id}
GET    /api/courses?teacherId=1
GET    /api/courses?credits=5
```

### Enrollments

```text
GET    /api/enrollments
GET    /api/enrollments/{id}
POST   /api/enrollments
PUT    /api/enrollments/{id}
DELETE /api/enrollments/{id}

PATCH  /api/enrollments/{id}/grade?grade=95
PATCH  /api/enrollments/{id}/pay

GET    /api/enrollments/unpaid
GET    /api/enrollments/gpa/student/{studentId}
GET    /api/enrollments/average/course/{courseId}
GET    /api/enrollments/average/semester?semester=2026-SPRING
GET    /api/enrollments/top-students?limit=5
```

### Validation and Error Handling

#### The project uses Bean Validation and centralized exception handling with @RestControllerAdvice.
```text
Example error response:

{
  "timestamp": "2026-07-01T15:27:13.95946",
  "status": 400,
  "error": "Bad Request",
  "message": "Grade must be between 0 and 100",
  "path": "/api/enrollments/1/grade"
}
```

### Tests

#### The project contains unit and controller tests:
```text
StudentServiceTest
EnrollmentServiceTest
StudentControllerTest

Total: 13 tests.
```
#### Run tests:
```text
./gradlew test
```
#### Or run tests directly from IntelliJ IDEA.

#### How to Run

##### Create PostgreSQL database:
```text
CREATE DATABASE university_db;
```
##### Check database settings in application.yml.

##### Start the application:

```text
./gradlew bootRun
```
##### Open Swagger UI:
```text
http://localhost:8080/swagger-ui.html
```
```text
Author: Tamara Katsyashvili
```
```text
Final Java Spring Boot project.
```


