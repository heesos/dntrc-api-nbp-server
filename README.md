# dntrc-api-nbp-server

## Description
This is the solution for the internship task for Dynatrace. 
This project contains simple local runnable server which fetches data from http://api.nbp.pl/ and provides three endpoints to get detailed information from this API.

## Installation

To run this project you can use one of the following commands while inside project directory


Run the Spring boot application with the java -jar command:
```bash
java -jar target/nbpapi-0.0.1-SNAPSHOT.jar
```
Run the Spring boot application using Maven:
```bash
mvn spring-boot:run
```
Run your Spring Boot application using Gradle
```bash
gradle bootRun
```

## Available Endpoints

Gets the average value of provided currency during given day.
```bash
http://localhost:8080/api/currency/average/{date}/{code}
```
Gets the minimum average value and maximum average value of the given currency.
```bash
http://localhost:8080/api/currency/maxmin/{code}/{limit}
```
Gets biggest difference for ask and sell rates for given currency
```bash
http://localhost:8080/api/currency/maxdiff/{code}/{limit}
```

Path variables:
- <span style="color:red"> {date} </span> - Date in YYYY-MM-DD format. This date points from which day should the data be fetched from. In case of Saturday or Sunday it cannot provide any data. (standard ISO 8601)
- <span style="color:red"> {code} </span> - Three letter currency code (standard ISO 4217)
- <span style="color:red"> {limit} </span> - Maximum amount of returned rates for the given currency

## Example queries

Gets the average value for USD on April 24th 2023
```bash
http://localhost:8080/api/currency/average/2023-04-24/usd
```

Gets the minimum average value and maximum average value for USD, fetches data from 100 days back.
```bash
http://localhost:8080/api/currency/maxmin/usd/100
```
Gets biggest difference for ask and sell rates for USD, fetches data from 50 days back
```bash
http://localhost:8080/api/currency/maxdiff/usd/50
```

## Test Endpoints!

In order to try this endpoints out, please run your local server as said before and paste in browser request below.
```bash
http://localhost:8080/swagger-ui/index.html#/
```
You should be able to use "Try it out" function. This front-end has been made by open source project SwaggerUI.
![image](https://user-images.githubusercontent.com/60614375/234400955-e1b84dfb-5578-486a-9fe0-7e1139ab2778.png)
