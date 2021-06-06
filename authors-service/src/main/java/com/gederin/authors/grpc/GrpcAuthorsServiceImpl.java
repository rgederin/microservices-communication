package com.gederin.authors.grpc;

import com.gederin.authors.service.AuthorsService;
import com.proto.author.AuthorsRequest;
import com.proto.author.AuthorsResponse;
import com.proto.author.AuthorsServiceGrpc.AuthorsServiceImplBase;

import org.springframework.stereotype.Service;

import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;

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
}
