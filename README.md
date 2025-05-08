# Personal Website Backend

A Spring Boot-based backend for a personal website with secure authentication and profile management.

## Overview

This project is a RESTful API backend for a personal website built with Spring Boot, Spring Security, and PostgreSQL. It features JWT-based authentication with refresh tokens, user profiles, and role-based authorization.

## Technologies

- **Java 21**
- **Spring Boot 3.4.4**
- **Spring Security** for authentication and authorization
- **Spring Data JPA** for database access
- **PostgreSQL** for data storage
- **JWT (JSON Web Token)** for stateless authentication
- **Lombok** for reducing boilerplate code
- **Maven** for dependency management and build

## Features

- **User Authentication**
  - Login with JWT token generation
  - Signup for new users
  - Token refresh mechanism
  - Secure password handling with BCrypt encoding

- **User Management**
  - Role-based authorization (USER and ADMIN roles)
  - User profiles with personal information
  - Profile management

- **Security**
  - JWT token-based authentication
  - Refresh token mechanism for extended sessions
  - Protection against common security threats

## Project Structure

```
src/
├── main/
│   ├── java/portfolio/dattq/
│   │   ├── config/         # Configuration classes
│   │   ├── controller/     # REST controllers
│   │   ├── exception/      # Custom exceptions and handlers
│   │   ├── model/          # Entity classes
│   │   ├── payload/        # Request/response DTOs
│   │   ├── repository/     # Data repositories
│   │   ├── security/       # Security configurations
│   │   └── service/        # Business logic
│   └── resources/
│       ├── application.properties  # Application configuration
│       ├── schema.sql              # Database schema
│       └── data.sql                # Sample data
└── test/                           # Unit and integration tests
```

## Database Design

The database consists of 5 main tables:

1. **Users** - Stores user authentication information
2. **Roles** - Defines available roles in the system
3. **User_Roles** - Maps users to their assigned roles
4. **Profiles** - Stores user profile information
5. **Refresh_Tokens** - Manages refresh tokens for extended sessions

Detailed database documentation is available in [database.md](database.md).

## API Endpoints

### Authentication

- `POST /api/auth/signin` - Login and get JWT token
- `POST /api/auth/signup` - Register a new user
- `POST /api/auth/refreshtoken` - Refresh the JWT token
- `POST /api/auth/signout` - Logout and invalidate refresh token

### Profile Management

- `GET /api/profile` - Get current user profile
- `PUT /api/profile` - Update current user profile
- `GET /api/profile/{userId}` - Get profile by user ID (admin access)

## Setup and Installation

### Prerequisites

- Java 21
- PostgreSQL
- Maven

### Configuration

1. Clone the repository
2. Configure PostgreSQL database connection in `application.properties`
3. Set your JWT secret and expiration times in `application.properties`

### Build and Run

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The application will be available at `http://localhost:8080`

## Sample Data

The application comes with sample data for testing:

- Admin user (username: admin, password: password123)
- Regular users (username: user1, user2, password: password123)
- Predefined roles (ROLE_USER, ROLE_ADMIN)

## Testing APIs with Postman

Follow these steps to test the APIs using Postman:

### Setting Up Postman

1. Download and install [Postman](https://www.postman.com/downloads/)
2. Create a new Collection named "Personal Website API"
3. Set up environment variables:
   - Create a new environment (e.g., "Local Development")
   - Add the following variables:
     - `base_url`: http://localhost:8080
     - `token`: (leave empty initially)
     - `refresh_token`: (leave empty initially)

### Testing Authentication APIs

#### 1. Register a New User

- Method: `POST`
- URL: `{{base_url}}/api/auth/signup`
- Headers: 
  - Content-Type: application/json
- Body (raw JSON):
```json
{
  "username": "testuser",
  "email": "testuser@example.com",
  "password": "password123",
  "roles": ["user"]
}
```

#### 2. Login

- Method: `POST`
- URL: `{{base_url}}/api/auth/signin`
- Headers: 
  - Content-Type: application/json
- Body (raw JSON):
```json
{
  "username": "testuser",
  "password": "password123"
}
```
- After successful login, the response will contain `accessToken` and `refreshToken`. Set up a script in the "Tests" tab to save these tokens:
```javascript
var response = pm.response.json();
pm.environment.set("token", response.accessToken);
pm.environment.set("refresh_token", response.refreshToken);
```

#### 3. Refresh Token

- Method: `POST`
- URL: `{{base_url}}/api/auth/refreshtoken`
- Headers: 
  - Content-Type: application/json
- Body (raw JSON):
```json
{
  "refreshToken": "{{refresh_token}}"
}
```
- In the "Tests" tab, add this script to update the access token:
```javascript
var response = pm.response.json();
pm.environment.set("token", response.accessToken);
```

#### 4. Logout

- Method: `POST`
- URL: `{{base_url}}/api/auth/signout`
- Headers: 
  - Content-Type: application/json
  - Authorization: Bearer {{token}}
- Body (raw JSON):
```json
{
  "refreshToken": "{{refresh_token}}"
}
```

### Testing Profile APIs

#### 1. Get Current User Profile

- Method: `GET`
- URL: `{{base_url}}/api/profile`
- Headers: 
  - Authorization: Bearer {{token}}

#### 2. Update Profile

- Method: `PUT`
- URL: `{{base_url}}/api/profile`
- Headers: 
  - Content-Type: application/json
  - Authorization: Bearer {{token}}
- Body (raw JSON):
```json
{
  "fullName": "Updated Name",
  "bio": "This is an updated bio",
  "avatarUrl": "https://example.com/new-avatar.jpg",
  "phoneNumber": "+1234567890",
  "address": "123 Updated St, New City",
  "birthDate": "1990-01-01"
}
```

#### 3. Get Profile by User ID (Admin Only)

- Method: `GET`
- URL: `{{base_url}}/api/profile/2` (Replace 2 with target user ID)
- Headers: 
  - Authorization: Bearer {{token}}
- Note: You need to be logged in as an admin user for this request

### Testing Flow

For proper API testing, follow this sequence:

1. Register a new user or login with existing credentials
2. Use the obtained JWT token for authenticated requests
3. Test profile operations with the authenticated user
4. Refresh the token if needed
5. Logout to invalidate the refresh token

### Common Issues

- **401 Unauthorized**: Make sure your token is valid and properly set in the Authorization header
- **403 Forbidden**: Check if the user has the required role for the operation
- **400 Bad Request**: Verify your request body matches the expected format

## Planned Features

- Email verification for new users
- Password reset functionality
- Enhanced profile management
- Additional role-based permissions 