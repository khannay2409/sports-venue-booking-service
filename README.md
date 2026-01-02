User Management Service

A Spring Boot based User Management service with JWT authentication, role-based authorization, and Kafka event publishing.

🚀 Tech Stack & Versions

Java: 17

Spring Boot: 3.5.x

Spring Security (JWT-based)

MySQL: 8 (Docker)

Apache Kafka (Docker)

Hibernate / JPA

Docker & Docker Compose

📦 Features

User Registration & Login (JWT Authentication)

Role-based Authorization (USER / ADMIN)

Admin-only APIs using @PreAuthorize

Kafka events on user registration & login

Stateless authentication (No sessions)

▶️ Run Locally (Without Docker)
./mvnw spring-boot:run


Note:
You must have MySQL & Kafka running locally and update application.yml accordingly.

🐳 Run Using Docker (Recommended)
docker-compose up


This starts:

MySQL

Kafka

Zookeeper

Spring Boot Application

✔ No manual DB setup required.

🗄 Database Schema

Database schema is auto-created by Hibernate

Tables: users, roles, user_roles

Controlled via:

spring.jpa.hibernate.ddl-auto=update


❌ No manual SQL or migrations required.

🔐 Security Design

JWT token generated on login

Token validated via custom JwtAuthenticationFilter

User roles stored in JWT

Authorization enforced using:

SecurityFilterChain

@PreAuthorize("hasRole('ADMIN')")

📡 Kafka Integration

User registration & login events published to Kafka

Producer configured using Spring Kafka

Events are JSON-based

🧠 Design Decisions & Assumptions

Stateless authentication for scalability

Role-based access using Spring Security best practices

Docker used to simplify environment setup

Minimal configuration to keep assignment clean and explainable

✅ How to Start
docker-compose up
