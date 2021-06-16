package com.gederin.books;

import com.gederin.books.grpc.GrpcBooksServiceImpl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.io.IOException;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
@AllArgsConstructor
public class BooksServiceApplication implements ApplicationListener<ContextRefreshedEvent> {
    private final GrpcBooksServiceImpl grpcBooksService;

    public static void main(String[] args) throws IOException {
        SpringApplication.run(BooksServiceApplication.class, args);
    }

    private void initGrpcServer() throws IOException, InterruptedException {
        log.info("starting grpc service");

        Server server = ServerBuilder
                .forPort(50052)
                .addService(grpcBooksService)
                .build();

        server.start();

        log.info("grpc server is running on port 50052");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Received shutdown request");
            server.shutdown();
            log.info("Successfully stopped the grpc server");
        }));

        server.awaitTermination();
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        try {
            initGrpcServer();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
