# Awesome Anonymous Forum

A Spring Boot application for an anonymous forum system with MySQL database support.

## Prerequisites

- Docker and Docker Compose
- Java Development Kit (JDK) 21 or later
- Maven

## Environment Variables

Before running the application, create a `.env` file in the root directory with the following variables:

## Building the Application

1. Build the application using Maven:

## Running with Docker Compose

1. Start the application and database:
This will:
- Start a MySQL 8.0.33 database container
- Build and start the Spring Boot application
- Connect the application to the database

2. To stop the application:
Add `-v` flag to remove the volumes when stopping:

## Accessing the Application

The application will be available at:
- Base URL: `http://localhost:11050`

## Database

- Database Port: 3306
- Username: root
- Password: (as specified in .env file)
- Database Name: (as specified in .env file)

## Configuration

The application uses the following configuration:

- Server port: 11050
- Database: MySQL with automatic schema update (hibernate.ddl-auto=update)
- External service: Whoa client integration

## Container Health Checks

The MySQL container includes health checks to ensure the database is ready before the application starts. The health check:
- Retries up to 10 times
- Checks every 3 seconds
- Times out after 30 seconds

## Volumes

The application uses a persistent volume for MySQL data storage:
- Volume name: mysql-data
- Mount point: /var/lib/mysql

## Networks

All services run on a dedicated network:
- Network name: springboot-mysql-network

## Troubleshooting

1. If the application fails to start, check:
   - Docker logs: `docker compose logs`
   - Database connectivity: `docker compose logs mysqldb`
   - Application logs: `docker compose logs app`

2. To reset the database:
   - Stop the containers: `docker compose down`
   - Remove the volume: `docker volume rm mysql-data`
   - Restart: `docker compose up -d`