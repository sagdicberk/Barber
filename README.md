# BarberApp - Backend Project

BarberApp is a backend service designed for managing appointments in a barbershop system. The project focuses on building a scalable and flexible backend infrastructure to handle multiple user roles and automate common business tasks, such as scheduling, notifications, and role-based access control.

## Project Overview:
BarberApp allows barbers, super barbers, and customers to interact within a system that manages appointments and customer information. The core functionality revolves around scheduling and verifying appointments, with additional features like SMS-based verification for enhanced security.

## Key Features:

- **Role-based Access Control (RBAC):** The system defines three roles: Barber, SuperBarber, and Customer. Each role has specific permissions for creating, viewing, and managing appointments.

- **Appointment Scheduling:** Users can schedule and manage appointments. The system tracks appointment data in the backend using a PostgreSQL database.

- **SMS Verification Service:** To enhance the security and reliability of the appointment system, Twilio API is integrated for sending SMS verification codes. This ensures only verified users can confirm their appointments.

## Technologies:

- **Spring Boot (Java):** Used for building a robust and scalable backend service. Spring Boot's dependency management and built-in features provide a solid framework for building RESTful services.
- **PostgreSQL:** The relational database management system used for storing user, appointment, and system data securely and efficiently.
- **Twilio API:** A third-party SMS service used to send verification codes to users. This ensures authentication and validation in appointment scheduling.

## Challenges:

One of the challenges faced in the project was Twilio’s free-tier limitation, which only allows one phone number for sending SMS messages. This restricted testing across multiple phone numbers, impacting the ability to fully validate appointment scheduling for all user roles.