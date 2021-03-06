# Roman Numerals API

A RESTful API to convert numbers between Arabic and Roman

## Getting Started

This API is not currently hosted.

In order to use it, clone this repository to your computer.

Navigate to the project folder and enter the following command in the terminal

    sbt run

The API will be accessible at http://localhost:9000/{endpoint}

## Endpoints and Methods

    /toroman    GET, POST                   Converts a number from Arabic to Roman
    /toarabic   GET, POST                   Converts a number from roman to Arabic
    /romans     GET, POST, PUT, DELETE      Allows the storage of additional Romans

See the following sections for further details

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

## Adding a new Roman

In addition to converting numbers, you may wish to use this API to store information about famous Romans, such as the year Caesar died.

The names of such Romans cannot be duplicated.

### POST

    /romans

Expects to receive a request body with content type **application/json** in the following form:

    {
        "roman": "Caesar"
        "arabic": 150344
    }

### Response

If successful, a response is returned with content type **application/json** containing details of the added Roman:

    {
        "roman": "Caesar"
        "arabic": 150344
    }

## Retrieving added Romans

### GET

    /romans

### Response

A response is returned with content type **application/json** containing details of any Romans that have been added:

    [
        {
            "arabic": 240141,
            "roman": "Caligula"
        },
        {
            "arabic": 150344,
            "roman": "Caesar"
        }
    ]

## Updating an added Roman

It is possible to update the arabic number corresponding to a Roman that has been added.

### PUT

    /romans

Expects to receive a request body with content type **application/json** in the following form:

    {
        "roman": "Caesar"
        "arabic": 150344
    }

### Response

If successful, a response is returned with content type **application/json** containing details of the updated Roman:

    {
        "roman": "Caesar"
        "arabic": 150344
    }

## Deleting an added Roman

### DELETE (via the endpoint)

    /romans/{name of Roman}

### DELETE (via request body)

    /romans

Expects to receive a request body with content type **application/json** in the following form:

    {
        "roman": "Caesar"
    }

### Response

If successful, both methods return a response with content type **application/json** containing details of the deleted Roman:

    {
        "arabic": 150344,
        "roman": "Caesar"
    }

## Tech Stack

This project was built using the Scala [Play Framework](https://www.playframework.com/) with [Google Guice](https://github.com/google/guice) for dependency injection.