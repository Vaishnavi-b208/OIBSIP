# Online Reservation System

## Objective

The objective of this project is to develop a GUI-based Online Reservation System using Java Swing, JDBC, and MySQL.

The system allows users to log in, book train tickets, and cancel bookings using a PNR number.

## Technologies Used

- Java
- Java Swing
- JDBC
- MySQL
- MySQL Connector/J
- Visual Studio Code

## Features

### 1. Login Form
- User enters username and password.
- Valid credentials allow the user to access the reservation system.
- Invalid credentials are rejected.

### 2. Reservation Form
The user can enter:
- Passenger name
- Train number
- Class type
- Date of journey

The train name, source station, and destination station are automatically retrieved from the database using the train number.

### 3. Ticket Booking
- The booking details are stored in MySQL.
- A unique PNR number is generated.
- A booking confirmation message displays the ticket details.

### 4. Ticket Cancellation
- User enters the PNR number.
- The system fetches the booking details.
- The user confirms the cancellation.
- The booking is removed from the database.

### 5. Input Validation
The system checks:
- Empty required fields
- Numeric train number
- Valid journey date format

## Database

Database name:

`online_reservation`

Tables used:

- `users`
- `trains`
- `reservations`

## Sample Login

Username:

`admin`

Password:

`admin123`

## Sample Train Details

| Train Number | Train Name | Source | Destination |
|--------------|------------|--------|-------------|
| 10101 | Pune Express | Pune | Mumbai |
| 10102 | Deccan Express | Pune | Mumbai |
| 10103 | Intercity Express | Mumbai | Pune |
| 10104 | Karnataka Express | Pune | Delhi |

## Project Structure

```text
Java-Task1-OnlineReservationSystem
│
├── lib
│   └── mysql-connector-j-26.7.0.jar
│
├── screenshots
│   ├── login.png
│   ├── reservation.png
│   ├── booking-confirmation.png
│   ├── cancellation.png
│   └── mysql-reservations.png
│
├── src
│   ├── CancellationFrame.java
│   ├── DBConnection.java
│   ├── LoginFrame.java
│   ├── Main.java
│   └── ReservationFrame.java
│
└── README.md