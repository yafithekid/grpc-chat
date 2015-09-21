package com.if4031.proto;


import com.mongodb.MongoClient;
import io.grpc.ServerImpl;
import io.grpc.netty.NettyServerBuilder;
import io.grpc.stub.StreamObserver;

import java.util.logging.Logger;

public class ChatServer {
    private static final Logger logger = Logger.getLogger(ChatServer.class.getName());

    /* The port on which the server should run */
    private int port = 50051;
    private ServerImpl server;

    private void start() throws Exception {
        MongoClient mongoClient = new MongoClient("localhost",27017);
        server = NettyServerBuilder.forPort(port)
                .addService(ChatServiceGrpc.bindService(new ChatServiceImpl(mongoClient.getDB("if4031"))))
                .build().start();
        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                ChatServer.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Main launches the server from the command line.
     */
    public static void main(String[] args) throws Exception {
        final ChatServer server = new ChatServer();
        server.start();
    }
}