package com.gederin.books.grpc;

import com.gederin.books.service.BooksGrpcService;
import com.proto.book.AddBookRequest;
import com.proto.book.AddBookResponse;
import com.proto.book.BooksRequest;
import com.proto.book.BooksResponse;
import com.proto.book.BooksServiceGrpc.BooksServiceImplBase;

import org.springframework.stereotype.Service;

import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GrpcBooksServiceImpl extends BooksServiceImplBase {

    private final BooksGrpcService booksService;

    @Override
    public void getBooks(BooksRequest request, StreamObserver<BooksResponse> responseObserver) {
        BooksResponse booksResponse = BooksResponse.newBuilder()
                .addAllBook(booksService.getGrpcBooks())
                .build();

        responseObserver.onNext(booksResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void addBook(AddBookRequest request, StreamObserver<AddBookResponse> responseObserver) {
        AddBookResponse addBookResponse = booksService.addBook(request);

        responseObserver.onNext(addBookResponse);
        responseObserver.onCompleted();
    }
}
