Sports Venue Booking Service

A Spring Boot backend service that manages sports venues, time slots, availability, and bookings with JWT-based authentication.
This project simulates a real-world sports ground / turf booking system.

The service is fully Dockerized and uses MySQL for persistence.

Features
Core Booking Features

Venue management (add, list, view, delete)

Time slot management per venue (no overlapping slots)

Venue availability search by sport & time range

Slot booking with conflict prevention (no double booking)

Booking cancellation (slot freed immediately)

Authentication & Authorization (Supporting)

User registration & login with JWT

Role-based access control (USER, ADMIN)

Admin-protected APIs (e.g. venue creation)

Tech Stack

Java 17

Spring Boot 3.x

Spring Security (JWT)

Spring Data JPA (Hibernate)

MySQL

Docker & Docker Compose

Assumptions

One booking corresponds to exactly one slot

Slots are predefined by venue admins

Slot timings are immutable once booked

Cancelled bookings free the slot immediately

Single MySQL instance (no external cache)

Sports are selected only from the public sports list API

Sports List Constraint

Sports data is fetched from the public API:

GET https://stapubox.com/sportslist/


Only sport_code / sport_id from this API is stored

No hardcoded sports in the database

API Endpoints
Authentication

POST /api/auth/register – Register a user

POST /api/auth/login – Login and receive JWT

Venue Management (Admin)

POST /venues – Add a venue

GET /venues – List all venues

Slot Management (Admin)

POST /venues/{venueId}/slots – Add slots for a venue

GET /venues/{venueId}/slots – Get slots of a venue

Availability

GET /venues/available – Get available venues by sport & time range

Booking

POST /bookings – Book a slot

PUT /bookings/{id}/cancel – Cancel booking

Running the Application
Docker Setup (Recommended)

Make sure Docker is running, then execute:

docker-compose up --build


Services started:

Spring Boot application

MySQL database

Database & Constraints

Tables auto-created by Hibernate

Unique constraint ensures one booking per slot

Indexes added for:

venue + time range

sport code

slot lookup

Notes

Authentication is implemented as a supporting domain

Core focus of the project is venue availability & booking logic

Designed to reflect real-world backend service patterns

Author

Yatharth Khanna
