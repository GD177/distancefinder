# Distance Finder API

The Distance Finder API provides functionality to calculate the distance between two postal codes.

## Table of Contents
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
    - [Running the Application](#running-the-application)
    - [API Documentation](#API Documentation)
- [Usage](#usage)
    - [Calculate Distance](#calculate-distance)
- [API Reference](#api-reference)
    - [Calculate Distance](#calculate-distance-api)

## Getting Started

### Prerequisites

Before running the application, ensure that you have the following prerequisites installed:

- Java Development Kit (JDK) 11 or higher
- Maven (for building and running the application)
- Your preferred IDE or text editor

### Installation

To install and configure the Distance Finder API, follow these steps:

1. Clone the repository to your local machine:

   ```shell
   git clone https://github.com/your-username/distance-finder-api.git
    ```
   
2. Open the project in your preferred IDE or text editor.


3. Build the project using Maven:

    Build the project using your IDE's build tools or 
    by running the command below from the command line in the project's root directory.
    ```Shell
    mvn clean install
    ```

### Start/Running the application

In the root run the application by using the command below

```Shell
mvn spring-boot:run
```
OR

You can run the application from your IDE by executing 
the main class with the main() method.

This will run the server on port 8080.
The application should now start.

The API will be accessible at http://localhost:8080/api.

### API Documentation

API documentation for this project is available using Swagger.

To view the API documentation, follow these steps:

- Make sure the project is running. 
- Open a web browser and go to: `http://localhost:8080/swagger-ui.html` (replace `localhost:8080` with the appropriate host and port if different). 
- Explore the available endpoints, request/response models, and make API calls directly from the Swagger UI.

Alternatively, you can access the raw API specification in JSON format at: 

[API Documentation](./src/main/resources/swagger.json).

## Usage
### Calculate Distance

To calculate the distance between two postal codes, 
make a GET request to the /distance endpoint with the 
following query parameters:

- postCode1: The first postal code.
- postCode2: The second postal code.

The API will return the distance between the postal codes in kilometers along with the unit and additional location information.

Example Request:

```http
GET /api/distance?postCode1=AB10%201XG&postCode2=AB11%205QN
```

Example Response:

```json
{
  "locationInfo": [
    {
      "postalCode": "AB10 1XG",
      "latitude": 57.144156,
      "longitude": -2.114864
    },
    {
      "postalCode": "AB11 5QN",
      "latitude": 57.142701,
      "longitude": -2.093295
    }
  ],
  "distance": 1.3112226281726593,
  "unit": "km"
}
```

## API Reference
### Calculate Distance API

Endpoint: /api/distance

Method: GET

Query Parameters:

 - postCode1 (required): The first postal code.
 - postCode2 (required): The second postal code.

Response:

 - 'locationInfo' : An array of objects containing the postal code, latitude, and longitude for each location.
 - 'distance' : The distance between the postal codes in kilometers.
 - 'unit' : The unit of distance.

Example Response:

```json
{
  "locationInfo": [
    {
      "postalCode": "AB10 1XG",
      "latitude": 57.144156,
      "longitude": -2.114864
    },
    {
      "postalCode": "AB11 5QN",
      "latitude": 57.142701,
      "longitude": -2.093295
    }
  ],
  "distance": 1.3112226281726593,
  "unit": "km"
}
```