package com.if4031.proto;

import io.grpc.ChannelImpl;
import io.grpc.netty.NegotiationType;
import io.grpc.netty.NettyChannelBuilder;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.if4031.proto.Rpcchat.*;

public class ChatClient {
    private static final Logger logger = Logger.getLogger(ChatClient.class.getName());

    private final ChannelImpl channel;
    private final ChatServiceGrpc.ChatServiceBlockingStub blockingStub;

    /** Construct client connecting to HelloWorld server at {@code host:port}. */
    public ChatClient(String host, int port) {
        channel =
                NettyChannelBuilder.forAddress(host, port).negotiationType(NegotiationType.PLAINTEXT)
                        .build();
        blockingStub = ChatServiceGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public Response join(String name, String channel) {
        try {
            Rpcchat.JoinRequest request = Rpcchat.JoinRequest.newBuilder()
                    .setNickname(name)
                    .setChannel(channel).build();
            Rpcchat.Response response = blockingStub.join(request);
            return response;
        } catch (RuntimeException e) {
            logger.log(Level.WARNING, "RPC failed", e);
            return null;
        }
    }

    public Response leave(String name, String channel) {
        try {
            Rpcchat.LeaveRequest request = Rpcchat.LeaveRequest.newBuilder()
                    .setNickname(name)
                    .setChannel(channel).build();
            Rpcchat.Response response = blockingStub.leave(request);
            return response;
        } catch (RuntimeException e) {
            logger.log(Level.WARNING, "RPC failed", e);
            return null;
        }
    }

    public Response send(String name, String channel, String message) {
        try {
            Rpcchat.SendRequest request = Rpcchat.SendRequest.newBuilder()
                    .setNickname(name)
                    .setChannel(channel)
                    .setMessage(message)
                    .build();
            Rpcchat.Response response = blockingStub.send(request);
            return response;
        } catch (RuntimeException e) {
            logger.log(Level.WARNING, "RPC failed", e);
            return null;
        }
    }

    public Response sendAll(String name, String message) {
        try {
            Rpcchat.SendAllRequest request = Rpcchat.SendAllRequest.newBuilder()
                    .setNickname(name)
                    .setMessage(message)
                    .build();
            Rpcchat.Response response = blockingStub.sendAll(request);
            return response;
        } catch (RuntimeException e) {
            logger.log(Level.WARNING, "RPC failed", e);
            return null;
        }
    }

    public ChatResponse recvAll(String name) {
        try {
            Rpcchat.RecvAllRequest request = Rpcchat.RecvAllRequest.newBuilder()
                    .setNickname(name)
                    .build();
            Rpcchat.ChatResponse response = blockingStub.recvAll(request);
            return response;
        } catch (RuntimeException e) {
            logger.log(Level.WARNING, "RPC failed", e);
            return null;
        }
    }

    /**
     * Greet server. If provided, the first element of {@code args} is the name to use in the
     * greeting.
     */
    public static void main(String[] args) throws Exception {
        ChatClient client = new ChatClient("localhost", 50051);
        try {
            String currNick = "paijo";
            boolean stop = false;
            do{
                System.out.print("Command: ");
                Scanner sc = new Scanner(System.in);
                String str = sc.nextLine();
                String[] splited = str.split("\\s+");
                if (splited[0].equals("/NICK")){
                    if (splited.length != 2){
                        System.out.println("Usage: /NICK <nickname>");
                    } else {
                        currNick = splited[1];
                        System.out.println("[OK] Nickname changed to '"+currNick+"'");
                    }

                } else if (splited[0].equals("/JOIN")){
                    if (splited.length != 2){
                        System.out.println("Usage: /JOIN <nickname>");
                    } else {
                        Response response = client.join(currNick, splited[1]);
                        if (response == null) {
                            System.out.println("Someshit error");
                            continue;
                        }
                        System.out.println("["+response.getStatus()+"] "+response.getMessage());
                    }

                } else if (splited[0].equals("/LEAVE")){
                    if (splited.length != 2){
                        System.out.println("Usage: /LEAVE <channel>");
                    } else {
                        Response response = client.leave(currNick,splited[1]);
                        if (response == null) {
                            System.out.println("Someshit error");
                            continue;
                        }
                        System.out.println("[" + response.getStatus() + "] " + response.getMessage());
                    }
                } else if (splited[0].equals("/EXIT")){
                    stop = true;
                } else {
                    StringBuffer message = new StringBuffer();
                    if (splited[0].startsWith("@")){
                        if (splited.length < 2){
                            System.out.println("Usage: @<channel> <text>");
                        } else {
                            String channelName = splited[0].substring(1);
                            for(int i = 1; i < splited.length; i++){
                                if (i > 1) message.append(" ");
                                message.append(splited[i]);
                            }
                            Response response = client.send(currNick,channelName,message.toString());
                            if (response == null) {
                                System.out.println("Someshit error");
                                continue;
                            }
                            System.out.println("["+response.getStatus()+"] "+response.getMessage());
                        }
                    } else {
                        for(int i = 0; i < splited.length; i++){
                            if (i > 0) message.append(" ");
                            message.append(splited[i]);

                        }
                        Response response = client.sendAll(currNick, message.toString());
                        if (response == null) {
                            System.out.println("Someshit error");
                            continue;
                        }
                        System.out.println("["+response.getStatus()+"] "+response.getMessage());
                    }
                }
                if (!stop){
                    ChatResponse chatResponse = client.recvAll(currNick);
                    if (chatResponse == null) {
                        System.out.println("Someshit error");
                        continue;
                    }
                    System.out.println("["+chatResponse.getStatus()+"] "+chatResponse.getMessage());
                    for(Message message:chatResponse.getChatsList()){
                        System.out.println("["+message.getChannel()+"]("+message.getNickname()+")" + message.getContent());
                    }
                }
            } while (!stop);
            System.out.println("bye");
        } finally {
            client.shutdown();
        }
    }
}
