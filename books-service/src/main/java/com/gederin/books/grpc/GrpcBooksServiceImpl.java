package com.gederin.books.grpc;

import com.gederin.books.service.BooksService;
import com.proto.book.BooksRequest;
import com.proto.book.BooksResponse;
import com.proto.book.BooksServiceGrpc.BooksServiceImplBase;

import org.springframework.stereotype.Service;

import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GrpcBooksServiceImpl extends BooksServiceImplBase {

    private final BooksService booksService;

    @Override
    public void getBooks(BooksRequest request, StreamObserver<BooksResponse> responseObserver) {
        BooksResponse booksResponse = BooksResponse.newBuilder()
                .addAllBook(booksService.getGrpcBooks())
                .build();

        responseObserver.onNext(booksResponse);
        responseObserver.onCompleted();
    }
}
