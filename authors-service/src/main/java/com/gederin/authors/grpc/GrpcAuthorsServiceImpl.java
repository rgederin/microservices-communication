package com.gederin.authors.grpc;

import com.gederin.authors.service.AuthorsService;
import com.proto.author.AddAuthorRequest;
import com.proto.author.AddAuthorResponse;
import com.proto.author.AuthorsRequest;
import com.proto.author.AuthorsResponse;
import com.proto.author.AuthorsServiceGrpc.AuthorsServiceImplBase;
import com.proto.author.UpdateAuthorRequest;
import com.proto.author.UpdateAuthorResponse;

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
