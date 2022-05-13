# Roman Numerals API

A RESTful API to convert numbers between Arabic and Roman

## Getting Started

This API is not currently hosted.

In order to use it, clone this repository to your computer.

Navigate to the project folder and enter the following command in the terminal

    sbt run

The API will be accessible at http://localhost:9000/{endpoint}

## Converting from Arabic to Roman Numerals

### GET

    /toroman/{arabic number}

### POST

    /toroman

Expects to receive a request body with content type **application/json** in the following form:

    {
        "arabic": 42
    }

### Response

Both methods return a response with content type **application/json** in the following form:

    {
        "arabic": 42,
        "roman": "XLII"
    }

## Converting from Roman Numerals to Arabic

### GET

    /toarabic/{roman numeral}

### POST

    /toarabic

Expects to receive a request body with content type **application/json** in the following form:

    {
        "roman": "XLII"
    }

### Response

Both methods return a response with content type **application/json** in the following form:

    {
        "arabic": 42,
        "roman": "XLII"
    }

## Tech Stack

This project was built using the Scala [Play Framework](https://www.playframework.com/) with [Google Guice](https://github.com/google/guice) for dependency injection.