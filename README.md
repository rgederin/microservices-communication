[![Build Status](https://travis-ci.com/github/rgederin/microservices-communication.svg?branch=master)](https://travis-ci.com/github/rgederin/microservices-communication)

# Communication in microservices

Repository for playing with different communication in microservices styles

# Table of content

- [Request-response communication using HTTP](#request-response-communication-using-http)
 

# Request-response communication using HTTP

In order to startup all services, please checkout this repository and use docker-compose:

```
docker-compose up
```

Lets check health status of three microservices by quering `api/v1/health` endpoint in frontend facing service `bff-service`:

```
GET http://localhost:8083/api/v1/health

{
    "backendForFrontendServiceHealth": "bff service is healthy",
    "booksServiceHealth": "books service is healthy",
    "authorsServiceHealth": "authors service is healthy"
}
```

Lets check `api/v1/dashboard` endpoint in frontend facing service `bff-service` in order to get all books and authors from downstream services:

```
GET http://localhost:8083/api/v1/dashboard/

{
    "books": [
        {
            "id": 1,
            "title": "Semiosis: A Novel",
            "pages": 326,
            "authorId": 1
        },
        {
            "id": 2,
            "title": "The Loosening Skin",
            "pages": 132,
            "authorId": 1
        },
        {
            "id": 3,
            "title": "Ninefox Gambit",
            "pages": 384,
            "authorId": 2
        },
        {
            "id": 4,
            "title": "Raven Stratagem",
            "pages": 400,
            "authorId": 3
        },
        {
            "id": 5,
            "title": "Revenant Gun",
            "pages": 466,
            "authorId": 3
        }
    ],
    "authors": [
        {
            "id": 1,
            "firstName": "Loreth Anne",
            "lastName": "White",
            "numberOfBooks": 2
        },
        {
            "id": 2,
            "firstName": "Lisa",
            "lastName": "Regan",
            "numberOfBooks": 1
        },
        {
            "id": 3,
            "firstName": "Ty",
            "lastName": "Patterson",
            "numberOfBooks": 2
        }
    ]
}
```

Lets check `api/v1/dashboard/combained` endpoint in frontend facing service `bff-service` in order to get all books with authors from downstream services:


```
GET http://localhost:8083/api/v1/dashboard/combained

[
    {
        "id": 1,
        "title": "Semiosis: A Novel",
        "pages": 326,
        "authorId": 1,
        "firstName": "Loreth Anne",
        "lastName": "White"
    },
    {
        "id": 2,
        "title": "The Loosening Skin",
        "pages": 132,
        "authorId": 1,
        "firstName": "Loreth Anne",
        "lastName": "White"
    },
    {
        "id": 3,
        "title": "Ninefox Gambit",
        "pages": 384,
        "authorId": 2,
        "firstName": "Lisa",
        "lastName": "Regan"
    },
    {
        "id": 4,
        "title": "Raven Stratagem",
        "pages": 400,
        "authorId": 3,
        "firstName": "Ty",
        "lastName": "Patterson"
    },
    {
        "id": 5,
        "title": "Revenant Gun",
        "pages": 466,
        "authorId": 3,
        "firstName": "Ty",
        "lastName": "Patterson"
    }
]
```

Now lets add new book with existed author via frontend facing service `bff-service`:

```
POST http://localhost:8083/api/v1/book

{
    "id": 10,
    "title": "New book with existed author",
    "pages": 43,
    "authorId": 2,
    "firstName": "Lisa",
    "lastName": "Regan"
}

Response: true
```

And check all books and authors endpoint again (new book is added and count of books is increased):

```
GET http://localhost:8083/api/v1/dashboard/

{
    "books": [
        {
            "id": 1,
            "title": "Semiosis: A Novel",
            "pages": 326,
            "authorId": 1
        },
        {
            "id": 2,
            "title": "The Loosening Skin",
            "pages": 132,
            "authorId": 1
        },
        {
            "id": 3,
            "title": "Ninefox Gambit",
            "pages": 384,
            "authorId": 2
        },
        {
            "id": 4,
            "title": "Raven Stratagem",
            "pages": 400,
            "authorId": 3
        },
        {
            "id": 5,
            "title": "Revenant Gun",
            "pages": 466,
            "authorId": 3
        },
        {
            "id": 10,
            "title": "New book with existed author",
            "pages": 43,
            "authorId": 2
        }
    ],
    "authors": [
        {
            "id": 1,
            "firstName": "Loreth Anne",
            "lastName": "White",
            "numberOfBooks": 2
        },
        {
            "id": 2,
            "firstName": "Lisa",
            "lastName": "Regan",
            "numberOfBooks": 2
        },
        {
            "id": 3,
            "firstName": "Ty",
            "lastName": "Patterson",
            "numberOfBooks": 2
        }
    ]
}
```

Lets add same book second time:

```
POST http://localhost:8083/api/v1/book

{
    "id": 10,
    "title": "New book with existed author",
    "pages": 43,
    "authorId": 2,
    "firstName": "Lisa",
    "lastName": "Regan"
}

Response: exception while calling book service: 400 : [client exception while adding new book, transaction rollbacked, book with give id already exists]
```

Lets add new book with new author:

```
POST http://localhost:8083/api/v1/book

{
    "id": 11,
    "title": "New book with new author",
    "pages": 222,
    "authorId": 6,
    "firstName": "new - first name",
    "lastName": "new - second name"
}

Response: true
```

And check dashboard:

```
GET http://localhost:8083/api/v1/dashboard/
{
    "books": [
        {
            "id": 1,
            "title": "Semiosis: A Novel",
            "pages": 326,
            "authorId": 1
        },
        {
            "id": 2,
            "title": "The Loosening Skin",
            "pages": 132,
            "authorId": 1
        },
        {
            "id": 3,
            "title": "Ninefox Gambit",
            "pages": 384,
            "authorId": 2
        },
        {
            "id": 4,
            "title": "Raven Stratagem",
            "pages": 400,
            "authorId": 3
        },
        {
            "id": 5,
            "title": "Revenant Gun",
            "pages": 466,
            "authorId": 3
        },
        {
            "id": 10,
            "title": "New book with existed author",
            "pages": 43,
            "authorId": 2
        },
        {
            "id": 11,
            "title": "New book with new author",
            "pages": 222,
            "authorId": 6
        }
    ],
    "authors": [
        {
            "id": 1,
            "firstName": "Loreth Anne",
            "lastName": "White",
            "numberOfBooks": 2
        },
        {
            "id": 2,
            "firstName": "Lisa",
            "lastName": "Regan",
            "numberOfBooks": 2
        },
        {
            "id": 3,
            "firstName": "Ty",
            "lastName": "Patterson",
            "numberOfBooks": 2
        },
        {
            "id": 6,
            "firstName": "new - first name",
            "lastName": "new - second name",
            "numberOfBooks": 1
        }
    ]
}
```