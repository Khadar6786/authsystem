# Spring Boot Authentication System

A secure authentication and authorization system built with Spring Boot 4.0.2 and Spring Security 7.0.2, featuring a modern Bootstrap 5 UI.

## Features

- ✅ **User Registration & Login** - Form-based authentication with custom login pages
- ✅ **REST API Authentication** - Programmatic login via REST endpoints
- ✅ **Password Encryption** - BCrypt password hashing for security
- ✅ **Duplicate Username Validation** - Prevents registration with existing usernames
- ✅ **Role-Based Access Control** - Support for USER and ADMIN roles
- ✅ **Custom UserDetailsService** - Database-backed user authentication
- ✅ **H2 Database** - In-memory database for development
- ✅ **Modern UI** - Responsive Bootstrap 5 design
- ✅ **H2 Console** - Built-in database management interface

## Tech Stack

- **Backend:** Spring Boot 4.0.2
- **Security:** Spring Security 7.0.2
- **Database:** H2 (in-memory)
- **ORM:** Spring Data JPA / Hibernate
- **Template Engine:** Thymeleaf
- **Frontend:** Bootstrap 5
- **Build Tool:** Maven
- **Java Version:** 17

## Project Structure

```
authsystem/
├── src/
│   ├── main/
│   │   ├── java/com/example/authsystem/
│   │   │   ├── config/
│   │   │   │   └── SecurityConfig.java          # Spring Security configuration
│   │   │   ├── controller/
│   │   │   │   └── AuthController.java          # Authentication endpoints
│   │   │   ├── model/
│   │   │   │   └── User.java                    # User entity
│   │   │   ├── repository/
│   │   │   │   └── UserRepository.java          # JPA repository
│   │   │   ├── service/
│   │   │   │   ├── UserService.java             # Business logic
│   │   │   │   └── CustomUserDetailsService.java # UserDetailsService implementation
│   │   │   └── AuthsystemApplication.java       # Main application class
│   │   └── resources/
│   │       ├── templates/
│   │       │   ├── login.html                   # Login page
│   │       │   ├── register.html                # Registration page
│   │       │   └── home.html                    # Home page
│   │       └── application.properties           # Application configuration
│   └── test/
└── pom.xml
```

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+ (or use included Maven wrapper)

### Installation & Running

1. **Clone the repository:**
   ```bash
   git clone https://github.com/Khadar6786/authsystem.git
   cd authsystem
   ```

2. **Run the application:**
   
   On Windows:
   ```powershell
   .\mvnw.cmd clean compile spring-boot:run
   ```
   
   On Linux/Mac:
   ```bash
   ./mvnw clean compile spring-boot:run
   ```

3. **Access the application:**
   - Application: http://localhost:8080
   - H2 Console: http://localhost:8080/h2-console
     - JDBC URL: `jdbc:h2:mem:testdb`
     - Username: `sa`
     - Password: (leave blank)

## API Endpoints

### Web UI Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/` | Redirects to home page |
| GET | `/login` | Login page |
| POST | `/login` | Process login form (Spring Security) |
| GET | `/register` | Registration page |
| POST | `/register` | Process registration form |
| GET | `/home` | Home page (authenticated) |

### REST API Endpoints

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| POST | `/api/login` | Programmatic login | `{"username": "user", "password": "pass"}` |

## Security Configuration

### Password Encoding
- Uses BCrypt for secure password hashing
- Passwords are never stored in plain text

### Access Control
- Public access: `/login`, `/register`, `/api/login`, `/h2-console`
- Admin-only: `/admin/**`
- User & Admin: `/user/**`
- All other routes require authentication

### Authentication Flow
1. User submits credentials via login form or API
2. CustomUserDetailsService loads user from database
3. DaoAuthenticationProvider validates credentials
4. Upon success, user is authenticated and redirected to home page

## Database Schema

### User Table

| Column | Type | Description |
|--------|------|-------------|
| id | Long | Primary key (auto-generated) |
| username | String | Unique username |
| password | String | BCrypt-hashed password |
| email | String | User email address |
| role | String | User role (USER/ADMIN) |

## Usage Examples

### Register a New User

1. Navigate to http://localhost:8080/register
2. Fill in the registration form
3. Click "Register"
4. If username already exists, error message will be displayed

### Login via Web UI

1. Navigate to http://localhost:8080/login
2. Enter username and password
3. Click "Login"
4. Upon success, redirected to home page

### Login via REST API

```bash
curl -X POST http://localhost:8080/api/login \
  -H "Content-Type: application/json" \
  -d '{"username": "testuser", "password": "password123"}'
```

Response:
```
Login successful
```

## Development

### Building the Project

```bash
mvnw clean install
```

### Running Tests

```bash
mvnw test
```

## Configuration

Key configuration in `application.properties`:

```properties
# H2 Database
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA/Hibernate
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update

# H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is open source and available under the [MIT License](LICENSE).

## Author

**Khadar** - [Khadar6786](https://github.com/Khadar6786)

## Acknowledgments

- Spring Boot team for the excellent framework
- Spring Security for robust authentication & authorization
- Bootstrap team for the beautiful UI components
