package com.if4031.proto;


import io.grpc.stub.StreamObserver;
import com.mongodb.*;

import java.util.ArrayList;
import java.util.List;

public class ChatServiceImpl implements ChatServiceGrpc.ChatServiceBlockingClient {
    //DB Schema
    //{_id,nickname,channel,content,timestamp}
    static final String MESSAGE_COLLECTION_NAME = "messages";
    //{_id,nickname,channel}
    static final String USER_CHANNEL_COLLECTION_NAME = "user_channels";
    //{_id,nickname,last_fetch}
    static final String USER_LAST_FETCH_COLLECTION_NAME = "user_last_fetch";
    DB db;

    public ChatServiceImpl(DB db){
        this.db = db;
        createIndex();
    }

    @Override
    public com.if4031.proto.Rpcchat.Response join(com.if4031.proto.Rpcchat.JoinRequest request) {
        String nickname = request.getNickname();
        String channel = request.getChannel();
        DBCollection userChannelCollection = this.getUserChannelCollection();
        BasicDBObject dbObject = new BasicDBObject()
                .append("nickname",nickname)
                .append("channel", channel);

        //check if user already joined
        if (userChannelCollection.findOne(dbObject) != null){
            return Rpcchat.Response.newBuilder()
                    .setStatus("ERROR")
                    .setMessage("user "+nickname+" already joined "+channel).build();
        } else {
            //set the last fetch to current timestamp
            DBCollection lastFetchCollection = getUserLastFetchCollection();
            lastFetchCollection.update(new BasicDBObject().append("nickname",nickname),new BasicDBObject().append("nickname", nickname).append("last_fetch", System.currentTimeMillis()),true,false,WriteConcern.ACKNOWLEDGED);

            userChannelCollection.insert(dbObject, WriteConcern.ACKNOWLEDGED);
            return Rpcchat.Response.newBuilder()
                    .setStatus("OK")
                    .setMessage(nickname+" has joined "+channel).build();
        }
    }

    @Override
    public com.if4031.proto.Rpcchat.Response leave(com.if4031.proto.Rpcchat.LeaveRequest request) {
        String nickname = request.getNickname();
        String channel = request.getChannel();
        DBCollection userChannelCollection = this.getUserChannelCollection();
        DBObject dbObject = new BasicDBObject()
                .append("nickname", nickname)
                .append("channel", channel);
        WriteResult writeResult = userChannelCollection.remove(dbObject, WriteConcern.ACKNOWLEDGED);
        if (writeResult.getN() == 0){
            return Rpcchat.Response.newBuilder()
                    .setStatus("ERROR").setMessage("user "+nickname+" doesn't join "+channel).build();
        } else {
            return Rpcchat.Response.newBuilder()
            .setStatus("OK").setMessage(nickname+" removed from "+channel).build();
        }
    }

    @Override
    public com.if4031.proto.Rpcchat.Response send(com.if4031.proto.Rpcchat.SendRequest request) {
        String nickname = request.getNickname();
        String channel = request.getChannel();
        String message = request.getMessage();
        //check if user has joined to channel
        DBCollection userChannelCollection = getUserChannelCollection();
        DBObject one = userChannelCollection.findOne(new BasicDBObject().append("nickname", nickname).append("channel", channel));
        if (one == null){
            return Rpcchat.Response.newBuilder()
            .setStatus("ERROR").setMessage("User "+nickname+" not joined "+channel).build();
        } else {
            DBCollection messageCollection = this.getMessageCollection();
            DBObject dbObject = new BasicDBObject()
                    .append("nickname",nickname)
                    .append("channel", channel)
                    .append("content", message)
                    .append("timestamp", System.currentTimeMillis());
            messageCollection.insert(dbObject,WriteConcern.ACKNOWLEDGED);
            return Rpcchat.Response.newBuilder().setStatus("OK").setMessage("").build();
        }
    }

    @Override
    public com.if4031.proto.Rpcchat.Response sendAll(com.if4031.proto.Rpcchat.SendAllRequest request) {
        String nickname = request.getNickname();
        String message = request.getMessage();
        //get user channels
        List<String> channels = fetchUserChannels(nickname);
        for(String channel: channels){
            send(Rpcchat.SendRequest.newBuilder()
                    .setNickname(nickname).setChannel(channel).setMessage(message).build());
        }
        return Rpcchat.Response.newBuilder()
                .setStatus("OK").setMessage("Channels affected : "+channels.size()).build();
    }

    @Override
    public Rpcchat.ChatResponse recvAll(Rpcchat.RecvAllRequest request) {
        String nickname = request.getNickname();

        //{'last_fetch':{'$gte':lastFetch},'channel':{'or':[....]}}
        long lastFetch = fetchUserLastFetchAndUpdate(nickname);
        if (lastFetch == -1){
            return Rpcchat.ChatResponse.newBuilder()
                    .setStatus("OK").setMessage("No messages found").build();
        }
        BasicDBObject messageQuery = new BasicDBObject().append("timestamp", new BasicDBObject("$gte", lastFetch));

        List<String> channels = fetchUserChannels(nickname);
        messageQuery.append("channel",new BasicDBObject().append("$in", channels));

        BasicDBObject sortQuery = new BasicDBObject().append("channel",1).append("timestamp",1);

        DBCollection messageCollection = getMessageCollection();
        DBCursor cursor = messageCollection.find(messageQuery).sort(sortQuery);
        int counts = cursor.count();

        Rpcchat.ChatResponse.Builder builder = Rpcchat.ChatResponse.newBuilder();
        while (cursor.hasNext()){
            DBObject dbObject = cursor.next();
            builder.addChats(Rpcchat.Message.newBuilder()
                    .setNickname((String) dbObject.get("nickname"))
                    .setChannel((String) dbObject.get("channel"))
                    .setContent((String) dbObject.get("content"))
                    .setTimestamp((long) dbObject.get("timestamp"))
                    .build()
            );
        }
        return builder.setStatus("OK").setMessage("Got " + counts + " messages").build();
    }

    private DBCollection getUserChannelCollection(){
        return db.getCollection(USER_CHANNEL_COLLECTION_NAME);
    }

    private DBCollection getMessageCollection(){
        return db.getCollection(MESSAGE_COLLECTION_NAME);
    }

    private DBCollection getUserLastFetchCollection(){
        return db.getCollection(USER_LAST_FETCH_COLLECTION_NAME);
    }

    private void createIndex(){
        DBCollection messageCollection = getMessageCollection();
        //create index for messages
        messageCollection.createIndex(new BasicDBObject().append("channel", 1));
        messageCollection.createIndex(new BasicDBObject().append("timestamp", -1));

        DBCollection userChannelCollection = getUserChannelCollection();
        userChannelCollection.createIndex(new BasicDBObject().append("nickname",1).append("channel", 1),null,true);

        DBCollection userLastFetchCollection = getUserLastFetchCollection();
        userLastFetchCollection.createIndex(new BasicDBObject().append("nickname", 1), null, true);

    }

    /**
     * Get when user last fetch a chat message
     * @param nickname user nickname
     * @return timestamp in millis
     */
    private long fetchUserLastFetchAndUpdate(String nickname){
        long lastFetch = fetchUserLastFetch(nickname);
        updateLastFetch(nickname);
        return lastFetch;
    }

    private void updateLastFetch(String nickname){
        DBCollection userLastFetchCollection = getUserLastFetchCollection();
        DBObject userChannelQuery = new BasicDBObject().append("nickname", nickname);
        userLastFetchCollection.update(userChannelQuery,
                new BasicDBObject().append("nickname",nickname).append("last_fetch",System.currentTimeMillis()));
    }

    private long fetchUserLastFetch(String nickname){
        DBCollection userLastFetchCollection = getUserLastFetchCollection();
        DBObject userQuery = new BasicDBObject().append("nickname", nickname);
        DBObject userDBObject = userLastFetchCollection.findOne(userQuery);
        if (userDBObject == null){
            return -1;
        } else {
            return (long) userDBObject.get("last_fetch");
        }
    }

    private List<String> fetchUserChannels(String nickname){
        List<String> channels = new ArrayList<>();
        //get user channels
        DBCollection userChannelCollection = getUserChannelCollection();
        DBCursor cursor = userChannelCollection.find(new BasicDBObject().append("nickname", nickname));

        //for each channels
        while (cursor.hasNext()){
            DBObject dbObject = cursor.next();
            String channel = (String) dbObject.get("channel");
            channels.add(channel);
        }
        return channels;
    }
}
