package com.gederin.bff.service.client;

import com.gederin.bff.dto.AuthorsListDto;
import com.gederin.bff.dto.BooksListDto;
import com.gederin.bff.service.mapper.MapperService;
import com.proto.author.AuthorsRequest;
import com.proto.author.AuthorsResponse;
import com.proto.author.AuthorsServiceGrpc;
import com.proto.author.AuthorsServiceGrpc.AuthorsServiceBlockingStub;
import com.proto.author.UpdateAuthorRequest;
import com.proto.author.UpdateAuthorResponse;
import com.proto.book.AddBookRequest;
import com.proto.book.AddBookResponse;
import com.proto.book.BooksRequest;
import com.proto.book.BooksResponse;
import com.proto.book.BooksServiceGrpc;
import com.proto.book.BooksServiceGrpc.BooksServiceBlockingStub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;

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
