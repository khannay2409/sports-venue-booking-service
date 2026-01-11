# 🏟️ Sports Venue Booking Service

A **Spring Boot backend service** that manages **sports venues, time slots, availability, and bookings** with **JWT-based authentication**.

This project simulates a real-world **sports ground / turf booking system** and focuses on **backend correctness, conflict handling, and clean API design**.

The service is fully **Dockerized** and uses **MySQL** for persistence.

---

## ✨ Features

### Core Booking Features
- Venue management (add, list, view, delete)
- Time slot management per venue (**no overlapping slots**)
- Venue availability search by sport & time range
- Slot booking with conflict prevention (**no double booking**)
- Booking cancellation (**slot freed immediately**)

### Authentication & Authorization (Supporting)
- User registration & login using JWT
- Role-based access control (`USER`, `ADMIN`)
- Admin-protected APIs (e.g. venue & slot creation)

---

## 🛠️ Tech Stack

- **Java 17**
- **Spring Boot 3.x**
- **Spring Security (JWT)**
- **Spring Data JPA (Hibernate)**
- **MySQL**
- **Docker & Docker Compose**

---

## 📌 Assumptions

- One booking corresponds to **exactly one slot**
- Slots are **predefined by venue admins**
- Slot timings are **immutable once booked**
- Cancelled bookings free the slot immediately
- Single MySQL instance (no external cache)
- Sports are selected **only from the public sports list API**

---

## 🏏 Sports List Constraint

Sports are fetched from the public API:

GET https://stapubox.com/sportslist/


- Only `sport_code` / `sport_id` from this API is stored
- No hardcoded sports in the database

---

## 🔐 Prerequisites (One-Time Setup)

### 1️⃣ Insert Roles into Database

-sql
INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');

2️⃣ Register a User via API
POST /api/auth/register


By default, the user gets ROLE_USER.

3️⃣ Promote User to Admin (DB Query)

Example: make user with user_id = 1 an admin.

INSERT INTO user_roles (user_id, role_id)
SELECT 1, id FROM roles WHERE name = 'ROLE_ADMIN';

4️⃣ Login Again

Login again to receive an updated JWT token with admin privileges.

🌐 API Endpoints
Authentication

POST /api/auth/register – Register user

POST /api/auth/login – Login and receive JWT

Venue Management (Admin)

POST /venues – Add venue

GET /venues – List venues

Slot Management (Admin)

POST /venues/{venueId}/slots – Add slots

GET /venues/{venueId}/slots – List slots

Availability

GET /venues/available – Get available venues by sport & time range

Booking

POST /bookings – Book a slot

PUT /bookings/{id}/cancel – Cancel booking

🐳 Running the Application

Make sure Docker is running, then:

docker-compose up --build


Services started:

Spring Boot application

MySQL database

🧠 Notes

Authentication is implemented as a supporting domain

Core focus is on availability, booking, and conflict handling

Designed to reflect real-world backend service patterns

👤 Author

Yatharth Khanna
