# User Management Service

A Spring Boot application that provides **JWT-based authentication**, **role-based authorization**, and **admin-protected APIs**.  
The project demonstrates secure user registration, login, role management, and event publishing using Kafka.

---

## Features

- User Registration & Login with JWT authentication  
- Role-based access control (USER, ADMIN)  
- Admin-only APIs using method-level security  
- Kafka event publishing on user actions (register, login)  
- Stateless security using Spring Security  
- Docker-based setup for MySQL and Kafka  

---

## Tech Stack

- Java 17  
- Spring Boot 3.x  
- Spring Security (JWT)  
- Spring Data JPA (Hibernate)  
- MySQL  
- Apache Kafka  
- Docker & Docker Compose  

---

## API Overview

### Authentication
- `POST /api/users/register` – Register a new user  
- `POST /api/users/login` – Login and receive JWT  

### User
- `GET /api/users/me` – Get logged-in user profile  

### Role Management (Admin only)
- `POST /api/roles` – Create new roles  
- `POST /api/users/{userId}/roles` – Assign roles to a user  

### Admin
- `GET /api/admin/stats` – Get user statistics (admin only)  

---

## Security Design

- JWT is used for authentication (stateless)  
- Roles are stored as `ROLE_*` and mapped to Spring authorities  
- Authorization is enforced using:
  - URL-based security rules
  - `@PreAuthorize` for method-level protection  
- Custom `JwtAuthenticationFilter` validates tokens and sets security context  

---

## Database Setup

- No manual schema creation required  
- Tables are automatically created by Hibernate on application startup  
- MySQL runs inside Docker  

---

## Running the Application

### 1. Docker-based setup (Recommended)

Make sure Docker is running, then execute:

```bash
docker-compose up
