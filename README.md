# Spotippos REST API

Code implementation for [Viva Real back-end challenge](https://github.com/VivaReal/code-challenge/blob/master/backend.md).


## Requirements

* [Git](https://git-scm.com/)
* [Docker](https://www.docker.com)


## Stack

* [Java 8](http://openjdk.java.net/)
* [Apache Maven 3.3](http://maven.apache.org/)
* [Spring Boot 1.4](http://projects.spring.io/spring-boot/)
* [Jetty 9.3](http://www.eclipse.org/jetty/)
* [Swagger 2.0](http://swagger.io/)


## Running application from docker image

This application uses [CircleCI](https://circleci.com) for Continuous Integration. Every push or tag release generates a docker image that is pushed to [Docker Hub](https://hub.docker.com/r/mwada/spotippos/).
You can run the application using this command:

```sh
$ docker run -p 8080:8080 -it mwada/spotippos
```


## Running application from source

```sh
$ git clone https://github.com/mwada/spotippos
$ cd spotippos
$ docker build -t mwada/spotippos .
$ docker run -p 8080:8080 -it mwada/spotippos
```


## Importing provinces/properties data

```sh
$ curl -H "Content-Type: application/json" -X POST -d @./data/provinces.json http://localhost:8080/loadProvinces
$ curl -H "Content-Type: application/json" -X POST -d @./data/properties.json http://localhost:8080/loadProperties
```

## Endpoints

Description | Endpoint
------------|---------
REST Documentation|/swagger-ui.html
App Info|/info
Health Check|/health
Metrics|/metrics


## REST API

### 1. Create properties
Request:
```
POST /properties
```

Body:
```json
{
  "x": 222,
  "y": 444,
  "title": "Imóvel código 1, com 5 quartos e 4 banheiros",
  "price": 1250000,
  "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
  "beds": 4,
  "baths": 3,
  "squareMeters": 210
}
```
Response:
```json
{
  "id": 665,
  "title": "Imóvel código 1, com 5 quartos e 4 banheiros",
  "price": 1250000,
  "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
  "x": 222,
  "y": 444,
  "beds": 4,
  "baths": 3,
  "provinces" : ["Ruja"],
  "squareMeters": 210
}
```

Response Code | Description
------------|---------
201 | Created
400 | Invalid Parameters
500 | Internal Server Error

Example:
```sh
$ curl -H "Content-Type: application/json" -X POST -d '{"x":222,"y":444,"title": "Title","price":1250000,"description":"Description","beds":4,"baths":3,"squareMeters":210}' http://localhost:8080/properties
```

### 2. Get properties

Request:
```
GET /properties/{id}
```

Response:
```json
{
  "id": 665,
  "title": "Imóvel código 665, com 1 quarto e 1 banheiro",
  "price": 540000,
  "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
  "x": 667,
  "y": 556,
  "beds": 1,
  "baths": 1,
  "provinces" : ["Ruja"],
  "squareMeters": 42
}
```
Response Code | Description
------------|---------
200 | OK
404 | Entity Not Found
400 | Invalid Parameters
500 | Internal Server Error

Example:
```sh
$ curl -H "Accept: application/json" http://localhost:8080/properties/665
```

### 3. Find properties

Request:
```
GET /properties?ax={integer}&ay={integer}&bx={integer}&by={integer}
```

Response:
```json
{
  "foundProperties": 60,
  "properties": [
    {
      "id": 34,
      "title": "Imóvel código 34, com 4 quartos e 3 banheiros",
      "price": 1250000,
      "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
      "x": 999,
      "y": 333,
      "beds": 4,
      "baths": 3,
      "squareMeters": 237,
      "provinces" : ["Scavy", "Gode"]
    },
    {"..."},
    {"..."}
  ]
}
```

Response Code | Description
------------|---------
200 | OK
400 | Invalid Parameters
500 | Internal Server Error

Example:
```sh
$ curl -H "Accept: application/json" http://localhost:8080/properties/?ax=0&ay=100&bx=100&by=0
```

## Improvements

The application works with 3 entities:

* Property: represents a single property with its attributes and coordinate
* Province: represents a single province with its coordinate
* Territory: matrix with all Position of the map, each Position contains a list of property ids and province names

These 3 entities are using memory structures as repositories and should be replaced with real databases for production environment, implementing the repository interfaces (com.vivareal.spotippos.repository.*Repository) to access these storages.

Property and Province could be persisted in a relational database like MySQL.
Territory could be persisted in a fast data structure storage like Redis.

Redis also has a helpful geospatial structure (Geo) that could be used to improve the territory persistence, with the caveat that it works with radius queries instead of box area.
