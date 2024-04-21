
# Finologee Bank API


## About The Project
Bank Management API

This Spring Boot application provides a RESTful API for bank management, allowing users to perform various banking operations:



There are many great README templates available on GitHub; however, I didn't find one that really suited my needs so I created this enhanced one. I want to create a README template so amazing that it'll be the last one you ever need -- I think this is it.

Here's why:
* Authenticating
* Logging out
* Searching for Accounts
* Searching for Payments with Pagination and filters
* Creating Payments (Fund Transfers)

## Prerequisites
Before running the application, make sure you have the following installed:

Java Development Kit (JDK) 21

Apache Maven

PostgreSQL database server

## Setup
Clone the Repository: 
Clone this repository to your local machine.

    git clone https://github.com/bilelneb13/bank-api.git

Build the Application:

Navigate to the project directory and build the application using Maven.

    cd bank-api
    docker-compose up -d


The application will start and listen for incoming requests on port 6868 by default.
Access the API:You can now access the API endpoints using tools like cURL, Postman, or your web browser.Base 

    URL: http://localhost:6868/api/v1



API Documentation
Swagger UI: After running the application, access the Swagger UI documentation at 

    http://localhost:8080/swagger-ui.html 

to explore and interact with the API endpoints.
