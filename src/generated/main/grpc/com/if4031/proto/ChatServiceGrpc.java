package com.if4031.proto;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;

@javax.annotation.Generated("by gRPC proto compiler")
public class ChatServiceGrpc {

  // Static method descriptors that strictly reflect the proto.
  public static final io.grpc.MethodDescriptor<com.if4031.proto.Rpcchat.JoinRequest,
      com.if4031.proto.Rpcchat.Response> METHOD_JOIN =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          "ChatService", "join",
          io.grpc.protobuf.ProtoUtils.marshaller(com.if4031.proto.Rpcchat.JoinRequest.parser()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.if4031.proto.Rpcchat.Response.parser()));
  public static final io.grpc.MethodDescriptor<com.if4031.proto.Rpcchat.LeaveRequest,
      com.if4031.proto.Rpcchat.Response> METHOD_LEAVE =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          "ChatService", "leave",
          io.grpc.protobuf.ProtoUtils.marshaller(com.if4031.proto.Rpcchat.LeaveRequest.parser()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.if4031.proto.Rpcchat.Response.parser()));
  public static final io.grpc.MethodDescriptor<com.if4031.proto.Rpcchat.SendRequest,
      com.if4031.proto.Rpcchat.Response> METHOD_SEND =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          "ChatService", "send",
          io.grpc.protobuf.ProtoUtils.marshaller(com.if4031.proto.Rpcchat.SendRequest.parser()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.if4031.proto.Rpcchat.Response.parser()));
  public static final io.grpc.MethodDescriptor<com.if4031.proto.Rpcchat.SendAllRequest,
      com.if4031.proto.Rpcchat.Response> METHOD_SEND_ALL =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          "ChatService", "sendAll",
          io.grpc.protobuf.ProtoUtils.marshaller(com.if4031.proto.Rpcchat.SendAllRequest.parser()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.if4031.proto.Rpcchat.Response.parser()));
  public static final io.grpc.MethodDescriptor<com.if4031.proto.Rpcchat.RecvAllRequest,
      com.if4031.proto.Rpcchat.ChatResponse> METHOD_RECV_ALL =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          "ChatService", "recvAll",
          io.grpc.protobuf.ProtoUtils.marshaller(com.if4031.proto.Rpcchat.RecvAllRequest.parser()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.if4031.proto.Rpcchat.ChatResponse.parser()));

  public static ChatServiceStub newStub(io.grpc.Channel channel) {
    return new ChatServiceStub(channel);
  }

  public static ChatServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new ChatServiceBlockingStub(channel);
  }

  public static ChatServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new ChatServiceFutureStub(channel);
  }

  public static interface ChatService {

    public void join(com.if4031.proto.Rpcchat.JoinRequest request,
        io.grpc.stub.StreamObserver<com.if4031.proto.Rpcchat.Response> responseObserver);

    public void leave(com.if4031.proto.Rpcchat.LeaveRequest request,
        io.grpc.stub.StreamObserver<com.if4031.proto.Rpcchat.Response> responseObserver);

    public void send(com.if4031.proto.Rpcchat.SendRequest request,
        io.grpc.stub.StreamObserver<com.if4031.proto.Rpcchat.Response> responseObserver);

    public void sendAll(com.if4031.proto.Rpcchat.SendAllRequest request,
        io.grpc.stub.StreamObserver<com.if4031.proto.Rpcchat.Response> responseObserver);

    public void recvAll(com.if4031.proto.Rpcchat.RecvAllRequest request,
        io.grpc.stub.StreamObserver<com.if4031.proto.Rpcchat.ChatResponse> responseObserver);
  }

  public static interface ChatServiceBlockingClient {

    public com.if4031.proto.Rpcchat.Response join(com.if4031.proto.Rpcchat.JoinRequest request);

    public com.if4031.proto.Rpcchat.Response leave(com.if4031.proto.Rpcchat.LeaveRequest request);

    public com.if4031.proto.Rpcchat.Response send(com.if4031.proto.Rpcchat.SendRequest request);

    public com.if4031.proto.Rpcchat.Response sendAll(com.if4031.proto.Rpcchat.SendAllRequest request);

    public com.if4031.proto.Rpcchat.ChatResponse recvAll(com.if4031.proto.Rpcchat.RecvAllRequest request);
  }

  public static interface ChatServiceFutureClient {

    public com.google.common.util.concurrent.ListenableFuture<com.if4031.proto.Rpcchat.Response> join(
        com.if4031.proto.Rpcchat.JoinRequest request);

    public com.google.common.util.concurrent.ListenableFuture<com.if4031.proto.Rpcchat.Response> leave(
        com.if4031.proto.Rpcchat.LeaveRequest request);

    public com.google.common.util.concurrent.ListenableFuture<com.if4031.proto.Rpcchat.Response> send(
        com.if4031.proto.Rpcchat.SendRequest request);

    public com.google.common.util.concurrent.ListenableFuture<com.if4031.proto.Rpcchat.Response> sendAll(
        com.if4031.proto.Rpcchat.SendAllRequest request);

    public com.google.common.util.concurrent.ListenableFuture<com.if4031.proto.Rpcchat.ChatResponse> recvAll(
        com.if4031.proto.Rpcchat.RecvAllRequest request);
  }

  public static class ChatServiceStub extends io.grpc.stub.AbstractStub<ChatServiceStub>
      implements ChatService {
    private ChatServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ChatServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChatServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ChatServiceStub(channel, callOptions);
    }

    @java.lang.Override
    public void join(com.if4031.proto.Rpcchat.JoinRequest request,
        io.grpc.stub.StreamObserver<com.if4031.proto.Rpcchat.Response> responseObserver) {
      asyncUnaryCall(
          channel.newCall(METHOD_JOIN, callOptions), request, responseObserver);
    }

    @java.lang.Override
    public void leave(com.if4031.proto.Rpcchat.LeaveRequest request,
        io.grpc.stub.StreamObserver<com.if4031.proto.Rpcchat.Response> responseObserver) {
      asyncUnaryCall(
          channel.newCall(METHOD_LEAVE, callOptions), request, responseObserver);
    }

    @java.lang.Override
    public void send(com.if4031.proto.Rpcchat.SendRequest request,
        io.grpc.stub.StreamObserver<com.if4031.proto.Rpcchat.Response> responseObserver) {
      asyncUnaryCall(
          channel.newCall(METHOD_SEND, callOptions), request, responseObserver);
    }

    @java.lang.Override
    public void sendAll(com.if4031.proto.Rpcchat.SendAllRequest request,
        io.grpc.stub.StreamObserver<com.if4031.proto.Rpcchat.Response> responseObserver) {
      asyncUnaryCall(
          channel.newCall(METHOD_SEND_ALL, callOptions), request, responseObserver);
    }

    @java.lang.Override
    public void recvAll(com.if4031.proto.Rpcchat.RecvAllRequest request,
        io.grpc.stub.StreamObserver<com.if4031.proto.Rpcchat.ChatResponse> responseObserver) {
      asyncUnaryCall(
          channel.newCall(METHOD_RECV_ALL, callOptions), request, responseObserver);
    }
  }

  public static class ChatServiceBlockingStub extends io.grpc.stub.AbstractStub<ChatServiceBlockingStub>
      implements ChatServiceBlockingClient {
    private ChatServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ChatServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChatServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ChatServiceBlockingStub(channel, callOptions);
    }

    @java.lang.Override
    public com.if4031.proto.Rpcchat.Response join(com.if4031.proto.Rpcchat.JoinRequest request) {
      return blockingUnaryCall(
          channel.newCall(METHOD_JOIN, callOptions), request);
    }

    @java.lang.Override
    public com.if4031.proto.Rpcchat.Response leave(com.if4031.proto.Rpcchat.LeaveRequest request) {
      return blockingUnaryCall(
          channel.newCall(METHOD_LEAVE, callOptions), request);
    }

    @java.lang.Override
    public com.if4031.proto.Rpcchat.Response send(com.if4031.proto.Rpcchat.SendRequest request) {
      return blockingUnaryCall(
          channel.newCall(METHOD_SEND, callOptions), request);
    }

    @java.lang.Override
    public com.if4031.proto.Rpcchat.Response sendAll(com.if4031.proto.Rpcchat.SendAllRequest request) {
      return blockingUnaryCall(
          channel.newCall(METHOD_SEND_ALL, callOptions), request);
    }

    @java.lang.Override
    public com.if4031.proto.Rpcchat.ChatResponse recvAll(com.if4031.proto.Rpcchat.RecvAllRequest request) {
      return blockingUnaryCall(
          channel.newCall(METHOD_RECV_ALL, callOptions), request);
    }
  }

  public static class ChatServiceFutureStub extends io.grpc.stub.AbstractStub<ChatServiceFutureStub>
      implements ChatServiceFutureClient {
    private ChatServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ChatServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChatServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ChatServiceFutureStub(channel, callOptions);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<com.if4031.proto.Rpcchat.Response> join(
        com.if4031.proto.Rpcchat.JoinRequest request) {
      return futureUnaryCall(
          channel.newCall(METHOD_JOIN, callOptions), request);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<com.if4031.proto.Rpcchat.Response> leave(
        com.if4031.proto.Rpcchat.LeaveRequest request) {
      return futureUnaryCall(
          channel.newCall(METHOD_LEAVE, callOptions), request);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<com.if4031.proto.Rpcchat.Response> send(
        com.if4031.proto.Rpcchat.SendRequest request) {
      return futureUnaryCall(
          channel.newCall(METHOD_SEND, callOptions), request);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<com.if4031.proto.Rpcchat.Response> sendAll(
        com.if4031.proto.Rpcchat.SendAllRequest request) {
      return futureUnaryCall(
          channel.newCall(METHOD_SEND_ALL, callOptions), request);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<com.if4031.proto.Rpcchat.ChatResponse> recvAll(
        com.if4031.proto.Rpcchat.RecvAllRequest request) {
      return futureUnaryCall(
          channel.newCall(METHOD_RECV_ALL, callOptions), request);
    }
  }

  public static io.grpc.ServerServiceDefinition bindService(
      final ChatService serviceImpl) {
    return io.grpc.ServerServiceDefinition.builder("ChatService")
      .addMethod(io.grpc.ServerMethodDefinition.create(
          METHOD_JOIN,
          asyncUnaryCall(
            new io.grpc.stub.ServerCalls.UnaryMethod<
                com.if4031.proto.Rpcchat.JoinRequest,
                com.if4031.proto.Rpcchat.Response>() {
              @java.lang.Override
              public void invoke(
                  com.if4031.proto.Rpcchat.JoinRequest request,
                  io.grpc.stub.StreamObserver<com.if4031.proto.Rpcchat.Response> responseObserver) {
                serviceImpl.join(request, responseObserver);
              }
            })))
      .addMethod(io.grpc.ServerMethodDefinition.create(
          METHOD_LEAVE,
          asyncUnaryCall(
            new io.grpc.stub.ServerCalls.UnaryMethod<
                com.if4031.proto.Rpcchat.LeaveRequest,
                com.if4031.proto.Rpcchat.Response>() {
              @java.lang.Override
              public void invoke(
                  com.if4031.proto.Rpcchat.LeaveRequest request,
                  io.grpc.stub.StreamObserver<com.if4031.proto.Rpcchat.Response> responseObserver) {
                serviceImpl.leave(request, responseObserver);
              }
            })))
      .addMethod(io.grpc.ServerMethodDefinition.create(
          METHOD_SEND,
          asyncUnaryCall(
            new io.grpc.stub.ServerCalls.UnaryMethod<
                com.if4031.proto.Rpcchat.SendRequest,
                com.if4031.proto.Rpcchat.Response>() {
              @java.lang.Override
              public void invoke(
                  com.if4031.proto.Rpcchat.SendRequest request,
                  io.grpc.stub.StreamObserver<com.if4031.proto.Rpcchat.Response> responseObserver) {
                serviceImpl.send(request, responseObserver);
              }
            })))
      .addMethod(io.grpc.ServerMethodDefinition.create(
          METHOD_SEND_ALL,
          asyncUnaryCall(
            new io.grpc.stub.ServerCalls.UnaryMethod<
                com.if4031.proto.Rpcchat.SendAllRequest,
                com.if4031.proto.Rpcchat.Response>() {
              @java.lang.Override
              public void invoke(
                  com.if4031.proto.Rpcchat.SendAllRequest request,
                  io.grpc.stub.StreamObserver<com.if4031.proto.Rpcchat.Response> responseObserver) {
                serviceImpl.sendAll(request, responseObserver);
              }
            })))
      .addMethod(io.grpc.ServerMethodDefinition.create(
          METHOD_RECV_ALL,
          asyncUnaryCall(
            new io.grpc.stub.ServerCalls.UnaryMethod<
                com.if4031.proto.Rpcchat.RecvAllRequest,
                com.if4031.proto.Rpcchat.ChatResponse>() {
              @java.lang.Override
              public void invoke(
                  com.if4031.proto.Rpcchat.RecvAllRequest request,
                  io.grpc.stub.StreamObserver<com.if4031.proto.Rpcchat.ChatResponse> responseObserver) {
                serviceImpl.recvAll(request, responseObserver);
              }
            }))).build();
  }
}
