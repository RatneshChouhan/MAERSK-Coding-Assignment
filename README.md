# MAERSK-Coding-Assignment
# Read Me First

## Prerequisites

- Java 11 or later
- Apache Maven 3.6.3 or later
- Run a Cassandra database as Docker container and creating keyspace name as container_booking
and database table name as bookings with these commands


- CREATE KEYSPACE IF NOT EXISTS container_booking
  WITH REPLICATION = {
  'class' : 'SimpleStrategy',
  'replication_factor' : 1
  } AND durable_writes = true;

- CREATE TABLE IF NOT EXISTS container_booking.bookings (
  booking_ref text PRIMARY KEY,
  container_size int,
  container_type text,
  origin text,
  destination text,
  quantity int,
  timestamp text
  );

## Downloading the Application

To download the application from the source code repository, follow these steps:

1. Open a terminal or command prompt.
2. Change to the directory where you want to download the application.
3. Clone the repository using Git:
    ```
    git clone https://github.com/RatneshChouhan/MAERSK-Coding-Assignment.git
    ```
4. Change to the directory that contains the application code:
    ```
    cd MAERSK-Coding-Assignment
    ```
5. Run below Maven commands to run the application:
    ```
    mvn clean test spring-boot:run

    mvn clean spring-boot:run

    ```



