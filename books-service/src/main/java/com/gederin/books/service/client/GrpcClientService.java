package com.gederin.books.grpc;

import com.proto.author.AuthorsServiceGrpc.AuthorsServiceBlockingStub;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@Service
public class GrpcClientService {

    @Value("${authors.grpc.host}")
    private String authorsGrpcHost;

    @Value("${authors.grpc.port}")
    private int authorsGrpcPort;

    public Boolean addAuthor(com.proto.author.AddAuthorRequest addAuthorRequest) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(authorsGrpcHost, authorsGrpcPort)
                .usePlaintext()
                .build();

        AuthorsServiceBlockingStub authorsClient = com.proto.author.AuthorsServiceGrpc.newBlockingStub(channel);
        com.proto.author.AddAuthorResponse grpcResponse = authorsClient.addAuthor(addAuthorRequest);

        return grpcResponse.getAdded();
    }
}
