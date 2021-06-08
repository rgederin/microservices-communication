Travis CI build status: 

[![Build Status](https://travis-ci.org/rgederin/spring-boot-rest-api-cicd.svg?branch=master)](https://travis-ci.com/github/rgederin/microservices-communication)


# Communication in microservices

Repository for playing with different communication in microservices styles

# Table of content

- [Request-response communication using HTTP](#request-response-communication-using-http)
- [Request-response communication using GRPC](#request-response-communication-using-grpc)

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

# Request-response communication using GRPC

In order to startup all services, please checkout this repository and use docker-compose:

```
docker-compose up
```

## Proto files per services

### Authors service

`authorService.proto`

```
syntax = "proto3";

package author;

option java_package = "com.proto.author";
option java_multiple_files = true;

message AuthorsRequest {
}

message AuthorsResponse {
    repeated Author authors = 1;
}

message Author {
    int32 id = 1;
    string firstName = 2;
    string lastName = 3;
    int32 numberOfBooks = 4;
}

message AddAuthorRequest {
    int32 id = 1;
    string firstName = 2;
    string lastName = 3;
}

message AddAuthorResponse {
    bool added = 1;
}

message UpdateAuthorRequest {
    int32 id = 1;
    string firstName = 2;
    string lastName = 3;
}

message UpdateAuthorResponse {
    bool updated = 1;
}

service AuthorsService {
    rpc getAuthors (AuthorsRequest) returns (AuthorsResponse);
    rpc addAuthor (AddAuthorRequest) returns (AddAuthorResponse);
    rpc updateAuthor (UpdateAuthorRequest) returns (UpdateAuthorResponse);
}
```

### Books service

`authorService.proto`

```
syntax = "proto3";

package author;

option java_package = "com.proto.author";
option java_multiple_files = true;

message AddAuthorRequest {
    int32 id = 1;
    string firstName = 2;
    string lastName = 3;
}

message AddAuthorResponse {
    bool added = 1;
}

service AuthorsService {
    rpc addAuthor (AddAuthorRequest) returns (AddAuthorResponse);
}
```

`booksService.proto`

```
syntax = "proto3";

package book;

option java_package = "com.proto.book";
option java_multiple_files = true;

message BooksRequest {
}

message BooksResponse {
    repeated Book book = 1;
}

message Book {
    int32 id = 1;
    string title = 2;
    int32 pages = 3;
    int32 authorId = 4;
}

message AddBookRequest {
    int32 id = 1;
    string title = 2;
    int32 pages = 3;
    int32 authorId = 4;
    string firstName = 5;
    string lastName = 6;
}

message AddBookResponse {
    bool added = 1;
}

service BooksService {
    rpc getBooks (BooksRequest) returns (BooksResponse);
    rpc addBook (AddBookRequest) returns (AddBookResponse);
}
```

### BFF service

`authorService.proto`

```
syntax = "proto3";

package author;

option java_package = "com.proto.author";
option java_multiple_files = true;

message AuthorsRequest {
}

message AuthorsResponse {
    repeated Author authors = 1;
}

message Author {
    int32 id = 1;
    string firstName = 2;
    string lastName = 3;
    int32 numberOfBooks = 4;
}

message UpdateAuthorRequest {
    int32 id = 1;
    string firstName = 2;
    string lastName = 3;
}

message UpdateAuthorResponse {
    bool updated = 1;
}

service AuthorsService {
    rpc getAuthors (AuthorsRequest) returns (AuthorsResponse);
    rpc updateAuthor (UpdateAuthorRequest) returns (UpdateAuthorResponse);
}
```

`bookService.proto`

```
syntax = "proto3";

package book;

option java_package = "com.proto.book";
option java_multiple_files = true;

message BooksRequest {
}

message BooksResponse {
    repeated Book book = 1;
}

message Book {
    int32 id = 1;
    string title = 2;
    int32 pages = 3;
    int32 authorId = 4;
}

message AddBookRequest {
    int32 id = 1;
    string title = 2;
    int32 pages = 3;
    int32 authorId = 4;
    string firstName = 5;
    string lastName = 6;
}

message AddBookResponse {
    bool added = 1;
}

service BooksService {
    rpc getBooks (BooksRequest) returns (BooksResponse);
    rpc addBook (AddBookRequest) returns (AddBookResponse);
}
```

## Grpc-related code snippets

Implementation of `GrpcAuthorService`:

```
@Service
@AllArgsConstructor
public class GrpcAuthorsServiceImpl extends AuthorsServiceImplBase {

    private final AuthorsService authorsService;

    @Override
    public void getAuthors(AuthorsRequest request, StreamObserver<AuthorsResponse> responseObserver) {
        AuthorsResponse authorsResponse = AuthorsResponse.newBuilder()
                .addAllAuthors(authorsService.getGrpcAuthors())
                .build();

        responseObserver.onNext(authorsResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void addAuthor(AddAuthorRequest request, StreamObserver<AddAuthorResponse> responseObserver) {
        AddAuthorResponse addAuthorResponse = AddAuthorResponse.newBuilder()
                .setAdded(authorsService.addAuthor(request))
                .build();

        responseObserver.onNext(addAuthorResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void updateAuthor(UpdateAuthorRequest request, StreamObserver<UpdateAuthorResponse> responseObserver) {
        UpdateAuthorResponse updateAuthorResponse = UpdateAuthorResponse.newBuilder()
                .setUpdated(authorsService.updateAuthor(request))
                .build();

        responseObserver.onNext(updateAuthorResponse);
        responseObserver.onCompleted();
    }
}
```

Grpc server start up:

```
private void initGrpcServer() throws IOException, InterruptedException {
        log.info("starting grpc service");

        Server server = ServerBuilder
                .forPort(50051)
                .addService(grpcAuthorsService)
                .build();

        server.start();

        log.info("grpc server is running on port 50051");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Received shutdown request");
            server.shutdown();
            log.info("Successfully stopped the grpc server");
        }));

        server.awaitTermination();
    }
```

GrpcClient in BFF service:

```
@Service
@Slf4j
public class GrpcClientService {

    @Value("${authors.grpc.host}")
    private String authorsGrpcHost;

    @Value("${authors.grpc.port}")
    private int authorsGrpcPort;

    @Value("${books.grpc.host}")
    private String booksGrpcHost;

    @Value("${books.grpc.port}")
    private int booksGrpcPort;

    private final MapperService mapperService;

    @Autowired
    public GrpcClientService(MapperService mapperService) {
        this.mapperService = mapperService;
    }

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<AuthorsListDto> getAuthors() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(authorsGrpcHost, authorsGrpcPort)
                .usePlaintext()
                .build();

        AuthorsServiceBlockingStub authorsClient = AuthorsServiceGrpc.newBlockingStub(channel);
        AuthorsResponse grpcResponse = authorsClient.getAuthors(AuthorsRequest.newBuilder().build());

        AuthorsListDto authorsListDto = new AuthorsListDto();

        authorsListDto.setAuthors(grpcResponse.getAuthorsList()
                .stream()
                .map(mapperService::mapFromGrpc)
                .collect(Collectors.toList()));

        return CompletableFuture.completedFuture(authorsListDto);
    }

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<BooksListDto> getBooks() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(booksGrpcHost, booksGrpcPort)
                .usePlaintext()
                .build();

        BooksServiceBlockingStub booksClient = BooksServiceGrpc.newBlockingStub(channel);
        BooksResponse response = booksClient.getBooks(BooksRequest.newBuilder().build());

        BooksListDto booksListDto = new BooksListDto();

        booksListDto.setBooks(response.getBookList()
                .stream()
                .map(mapperService::mapFromGrpc)
                .collect(Collectors.toList()));

        return CompletableFuture.completedFuture(booksListDto);
    }

    public boolean addBook(AddBookRequest addBookRequest) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(booksGrpcHost, booksGrpcPort)
                .usePlaintext()
                .build();

        BooksServiceBlockingStub booksClient = BooksServiceGrpc.newBlockingStub(channel);
        AddBookResponse response = booksClient.addBook(addBookRequest);

        return response.getAdded();
    }

    public boolean updateAuthor(UpdateAuthorRequest updateAuthorRequest) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(authorsGrpcHost, authorsGrpcPort)
                .usePlaintext()
                .build();

        AuthorsServiceBlockingStub authorsClient = AuthorsServiceGrpc.newBlockingStub(channel);
        UpdateAuthorResponse response = authorsClient.updateAuthor(updateAuthorRequest);

        return response.getUpdated();
    }
}
```

## Test grpc implementation

For grpc flow we need to use `v2` endpoints in frontend service.

### Simple not combined dashboard

```
GET http://localhost:8083/api/v2/dashboard/

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

### Combined dashboard

```
http://localhost:8083/api/v2/dashboard/combained

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

### Add book with new author

```
POST http://localhost:8083/api/v2/book

{
    "id": 8,
    "title": "New book with new author",
    "pages": 567,
    "authorId": 6,
    "firstName": "new - first name",
    "lastName": "new - second name"
}

GET http://localhost:8083/api/v2/dashboard/

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
            "id": 8,
            "title": "New book with new author",
            "pages": 567,
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
            "numberOfBooks": 1
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

### Add book with existed author


```
POST http://localhost:8083/api/v2/book

{
    "id": 9,
    "title": "New book with existed author",
    "pages": 567,
    "authorId": 6,
    "firstName": "new - first name",
    "lastName": "new - second name"
}

GET http://localhost:8083/api/v2/dashboard/

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
            "id": 8,
            "title": "New book with new author",
            "pages": 567,
            "authorId": 6
        },
        {
            "id": 9,
            "title": "New book with existed author",
            "pages": 567,
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
            "numberOfBooks": 1
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
            "numberOfBooks": 2
        }
    ]
}
```

### Update author

```
PUT http://localhost:8083/api/v2/author

{
    "id": 6,
    "firstName": "v2 - first name",
    "lastName": "v2 - second name"
}

GET http://localhost:8083/api/v2/dashboard/

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
            "id": 8,
            "title": "New book with new author",
            "pages": 567,
            "authorId": 6
        },
        {
            "id": 9,
            "title": "New book with existed author",
            "pages": 567,
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
            "numberOfBooks": 1
        },
        {
            "id": 3,
            "firstName": "Ty",
            "lastName": "Patterson",
            "numberOfBooks": 2
        },
        {
            "id": 6,
            "firstName": "v2 - first name",
            "lastName": "v2 - second name",
            "numberOfBooks": 2
        }
    ]
}
```