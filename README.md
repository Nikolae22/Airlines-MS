# ✈️ Distributed Airline Booking & Reservation Platform (Mini-GDS)

A production-grade, event-driven distributed system built with Java, Spring Boot, and a Microservices Architecture. This platform simulates a Global Distribution System (GDS), mimicking real-world airline workflows, high-throughput flight searching, seat inventory management, and reliable transaction processing.

---

## 🏗️ Architecture Overview

The platform is designed around the **Database-per-Service** pattern to ensure loose coupling and independent scalability. Inter-service communication is handled via synchronous REST (**OpenFeign**) for read operations and asynchronous event-streaming (**Apache Kafka**) for data consistency and workflows.

### Key Architectural Patterns
* **API Gateway Pattern:** Unified entry point implementing central routing, rate limiting, and JWT validation.
* **Saga Orchestration Pattern:** Manages distributed transactions across Booking, Payment, and Inventory services with automatic compensation events on failure.
* **Event-Driven Workflows:** Asynchronous decoupling using Kafka for flight updates, seating allocation, and notifications.
* **CQRS & Caching:** Multi-layer caching via Redis to optimize the Flight Search Engine and reduce database load.

---

## 🛠️ Tech Stack & Ecosystem

### Backend & Microservices
* **Core:** Java 17+, Spring Boot 3.x, Spring Cloud
* **Service Discovery:** Netflix Eureka
* **API Gateway:** Spring Cloud Gateway
* **Resilience:** Resilience4j (Circuit Breakers, Retries, Rate Limiters)
* **Communication:** OpenFeign (Sync), Apache Kafka (Async)

### Data & Caching
* **Databases:** MySQL (Production profiles), H2 (In-Memory Development/Testing)
* **ORM:** Spring Data JPA / Hibernate
* **Caching & Session:** Redis (Data caching & JWT Blacklisting/Token Revocation)

### Security
* **Framework:** Spring Security
* **Authentication:** Stateless JWT with Role-Based Access Control (RBAC)

### DevOps & Deployment
* **Containerization:** Docker, Google Jib
* **Orchestration:** Docker Compose
* **Monitoring:** Spring Boot Actuator

---

## 🚀 Key Modules & Services

1. **API Gateway Service:** Handles cross-cutting concerns like security filtering, routing, and token validation.
2. **Identity & Auth Service:** Manages user registration, authentication, RBAC, and active token blacklisting via Redis.
3. **Flight & Fleet Service:** Airline admin module for scheduling, aircraft management, and routes.
4. **Search & Inventory Service:** High-performance engine for flight availability, optimized with Redis caching.
5. **Booking Service:** Manages the reservation lifecycle and coordinates the distributed Saga transaction.
6. **Payment Service:** Simulates/Integrates secure payment processing gateway (Razorpay).
7. **Notification Service:** Consumes Kafka events to send automated asynchronous email updates (e-tickets, receipts).

---

## ⚡ Distributed Transaction Management (Saga Pattern)

To handle the booking lifecycle safely across distributed boundaries without blocking databases, this project implements an **Orchestrated Saga Pattern** via Kafka:

[Booking Initiated] ──> (Reserve Seat) ──> (Process Payment) ──> [Booking Confirmed]
│                     │
(Fail / Timeout)      (Payment Denied)
│                     │
▼                     ▼
[Release Seat] <─── [Rollback Booking] (Compensating Actions)
