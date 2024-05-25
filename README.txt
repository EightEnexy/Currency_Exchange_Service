This is a simple Currency Exchange Service built with Spring Boot. It uses an embedded H2 database and can be containerized using Docker.

Running the Application:
------------------------

1. Use Maven to build the project and create the JAR file.

    mvn clean package

    -This will create a JAR file in the 'target' directory named 'currency-exchange-0.0.1-SNAPSHOT.jar'.

2. Running Locally:

    java -jar target/currency-exchange-0.0.1-SNAPSHOT.jar

3. Running with Docker Compose:
    -In the root directory of the project, run the following command to start the container:
     docker-compose up

    -Alternatively, you can pull the image from Docker Hub and run it directly:
    docker pull vladimirdockercurrency/currency-exchange:latest
    docker run -p 8080:8080 vladimirdockercurrency/currency-exchange:latest

4. Access the Application:
    -After starting the application, the server will be accessible on `http://localhost:8080`.

Endpoints:
----------
1. Get All Exchange Rates
   - Endpoint: /api/rates
   - Method: GET
   - Description: Retrieves a list of all available exchange rates.
   - Response Example:
     [
       {
         "baseCurrency": "USD",
         "targetCurrency": "EUR",
         "rate": 0.923500
       },
       {
         "baseCurrency": "EUR",
         "targetCurrency": "USD",
         "rate": 1.082600
       },
       ...
     ]

2. Convert Currency
   - Endpoint: /api/convert
   - Method: POST
   - Description: Converts an amount from one currency to another based on the provided exchange rates.
   - Request Example:
     {
       "fromCurrency": "USD",
       "amount": 100.0,
       "toCurrency": "EUR"
     }
   - Response Example:
     {
       "convertedAmount": 92.3500000
     }