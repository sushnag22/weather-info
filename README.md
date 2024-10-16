# Weather Service API

## Overview

The **Weather Service API** provides weather information based on an Indian pincode and a specific date. The application integrates with external APIs such as OpenWeatherMap and RapidAPI to fetch pincode-related geographical information and real-time weather data. The API stores pincode and weather information in a database to reduce redundant API calls and improve performance.

## Features

- Fetch real-time weather information using a pincode and date.
- Automatically retrieve geographic details of pincodes via RapidAPI if not already in the database.
- Caches weather information for a specified date and pincode to minimize external API calls.
- Built with **Spring Boot**, using **RestTemplate** for API integration.
- Stores data in **PostgreSQL** using JPA.

## Technologies Used

- **Java 21**
- **Spring Boot 3**
    - Spring Data JPA
    - Spring Web
- **PostgreSQL** for database management
- **RestTemplate** for API calls
- **Jackson** for JSON parsing
- **OpenWeatherMap API** for fetching weather data
- **RapidAPI** for fetching geographic data related to pincode

### Prerequisites

- **Java 21**
- **PostgreSQL** database
