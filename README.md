
# Dinosaurs Application application

![Spring Boot Logo](https://img.shields.io/badge/SpringBoot-Dinosaurs-6DB33F?style=for-the-badge&logo=springboot)

## Overview
This is a **Spring Boot** application that provides a CRUD implementation of **Dinosaurs**
The application is built with the following stack:

- **Spring Boot** for the backend
- **JPA/Hibernate** for ORM and database interaction
- **H2DB** for the database on memory
- **Lombok** to reduce boilerplate code
- **Spring Data JPA** for repository management
- **RabbitMQ** for messaging

## Features
- Create, read, update and retrieve dinosaurs
- Scheduler to update dinasour status
- Messaging with RabbitMQ
- Unit tests

## Table of Contents
- [Getting Started](#getting-started)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Technologies](#technologies)

## Getting Started

To get started with the application, follow these steps:

### Prerequisites
Make sure you have the following installed:
- **Java 17**
- **Maven 3.x**
- **Git**
- **Docker**
- **Docker Compose**

### Clone the Repository

```bash
git https://github.com/mgrellet/dino
cd dino
```

### Build the Application

```bash
mvn clean package
```

## Running the Application

You can run the application by executing:

```bash
docker compose up
```
This command will start the sprinboot application and the RabbitMQ container.

## API Endpoints

Below are the available API endpoints for managing dinosaurs:

| HTTP Method | Endpoint            | Description           |
|-------------|---------------------|-----------------------|
| `POST`      | `/dinosaur`         | Create a new dinosaur |
| `GET`       | `/dinosaur`         | Gets all dinosaurs    |
| `GET`       | `/dinosaur/{id}`    | Gets dinosaur by id   |
| `PUT`       | `/dinosaur/{id}`    | Update dinosaur       |
| `DELETE`    | `/dinosaur/{id}`    | Delete dinosaur       |

### Sample API Request

#### Create a new Dinosaur
```bash
POST /dinosaur
Content-Type: application/json

{
    "name": "Tyrannosaurus Rex",
    "species": "Theropod",
    "discoveryDate": "1902-01-01T23:59:59",
    "extinctionDate": "2023-12-31T23:59:59",
    "status": "ALIVE"
}
```

#### Response:
```json
{
  "message": "success",
  "status": 201,
  "data": {
    "id": 1,
    "name": "Tyrannosaurus Rex",
    "species": "Theropod",
    "discoveryDate": "1902-01-01T23:59:59",
    "extinctionDate": "2023-12-31T23:59:59",
    "status": "ALIVE"
  }
}
```

#### Update a Dinosaur
```bash
UPDATE /dinosaur/{id}
Content-Type: application/json

{
    "name": "T-Rex",
    "species": "Theropod",
    "discoveryDate": "1902-01-01T23:59:59",
    "extinctionDate": "2023-12-31T23:59:59",
    "status": "ALIVE"
}
```

#### Response:
```json
{
  "message": "success",
  "status": 200,
  "data": {
    "id": 1,
    "name": "TREX",
    "species": "Theropod",
    "discoveryDate": "1902-01-01T23:59:59",
    "extinctionDate": "2023-12-31T23:59:59",
    "status": "ALIVE"
  }
}
```

### Swagger documentation

You can access the Swagger documentation at: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Technologies
- **Java 17**
- **Spring Boot**
- **Spring Data JPA**
- **Hibernate**
- **H2DB**
- **Lombok**
- **RabbitMQ**

## Scheduler

The application has a scheduler that updates the status of dinosaurs every 10 minutes
checking the extintion date.

```java
  @Scheduled(fixedRate = 600000)
```

## Messaging
Each time that a dinosaur is updated, the application sends a message to a RabbitMQ queue
with the new status and the timestamp of the update to be logged.

```json
{
  "dinosaurId": 1,
  "newStatus": "ENDANGERED",
  "timestamp": "2023-10-01T09:00:00"
}
```

NOTE: The producer and consumer was built in the same springboot application

## Running Tests

To run the unit and integration tests:

```bash
mvn test
```