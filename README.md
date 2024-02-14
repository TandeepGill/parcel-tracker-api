# Parcel Tracker

Parcel Tracker is a backend application which allows users, both senders and receivers, to track their parcel. This application is designed with Java, Spring Boot and Hibernate. Unit testing is done via JUnit, along with Mockito. This application also uses Swagger UI for interactive API documentation for anyone to visualize the API resources available and make API calls to test them out. There is also entry and exit trace logging implemented using Log4j. Parcel Tracker is using an in memory SQL database so it is easy for any user to download this repository and run the application.

# Main Application
The main application can be found in the following folder: src/main/java/com/fdmgroup/parceltracker

# Unit Tests
Unit tests can be found in the following folder for both the Controller and Servicer layer: src/test/java/com/fdmgroup/parceltracker

# Tech Stack
- Java - Object Oriented Programming (OOP) language
- Spring Boot - Spring framework to build REST APIs using Java
- H2 Database - Java in-memory relational database management system (RDBMS)
- Hibernate - Java based object-relational mapping (ORM)
- Swagger UI - Interactive API documentation
- Log4j - Java logging framework used for entry and exit logging
- JUnit - Unit testing framework for Java
- Mockito - Java based mocking framework

# Set-Up
1. Download this repository by clicking Clone --> Download ZIP
2. Go to your downloads folder and find the downloaded file called: parcel-tracker-api-main
3. If your computer has not unzipped it, double click on the downloaded .zip file to unzip it
4. Open up an IDE, either Eclipse or IntelliJ
5. Import the downloaded file as an existing Maven project
6. Run the application as a Spring Boot Application

# Swagger UI
To interact with the application via Swagger UI, follow the Set-Up instructions above and start the application. Then visit the following URL from your choice of browser: http://localhost:8080/swagger-ui/index.html

# Images
## Swagger UI API Documentation - All API Endpoints
![Swagger UI API Documentation - All API Endpoints](https://github.com/TandeepGill/parcel-tracker-api/assets/77635364/db500b64-2f77-4b66-a9cd-6f8aabfb0b51)

## Get All Parcels
![Get All Parcels](https://github.com/TandeepGill/parcel-tracker-api/assets/77635364/8a8b1a9c-8a2b-4c2e-96c7-3953f82b4230)

## Create a New Parcel
![Create a New Parcel](https://github.com/TandeepGill/parcel-tracker-api/assets/77635364/2a50318d-0abd-49a2-abdf-459e8a67ad28)

## Get a Parcel By Tracking Number
![Get a Parcel By Tracking Number](https://github.com/TandeepGill/parcel-tracker-api/assets/77635364/24a9954d-a474-4c32-a947-9b66e9fe0d5c)
