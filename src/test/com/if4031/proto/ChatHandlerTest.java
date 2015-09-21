package com.if4031.proto;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.if4031.proto.Rpcchat.*;
import org.junit.Test;

import java.io.PrintStream;
import java.util.Set;

import static org.junit.Assert.*;

public class ChatHandlerTest {
    DB testDB;
    PrintStream sysout = System.out;
    ChatClient client;
    ChatServer server;
    ChatServiceImpl impl;
    int server_port = 10000;
    String test_db = "test-pat";

    @org.junit.Before
    public void setUp() throws Exception {
        System.out.println("Contacting MongoDB on localhost:27017...");
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        try {
            testDB = mongoClient.getDB(test_db);
            impl = new ChatServiceImpl(testDB);
            server = new ChatServer(impl,server_port);
            client = new ChatClient("localhost",server_port);
            server.start();
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    @org.junit.After
    public void tearDown() throws Exception {
        MongoClient client = new MongoClient("localhost",27017);
        client.dropDatabase(test_db);
        server.stop();
    }

    @Test
    public void testHelloWorld(){
        sysout.print("Hello world");
    }

    @Test
    public void testJoinLeave(){
        Response response = client.join("calvin", "mboh");
        assertEquals(response.getStatus(), "OK");
        sysout.println(response.getMessage());

        Response response2 = client.join("calvin", "mboh");
        assertEquals(response2.getStatus(), "ERROR");
        sysout.println(response2.getMessage());

        Response response3 = client.leave("calvin", "mboh");
        assertEquals(response3.getStatus(), "OK");
        sysout.println(response3.getMessage());

        Response response4 = client.leave("calvin","mboh");
        assertEquals(response4.getStatus(),"ERROR");
        sysout.println(response4.getMessage());
    }

    @Test
    public void testSendReceiveMessageOneChannel(){
        client.join("calvin", "mboh");
        client.join("yafi", "mboh");

        client.send("calvin", "mboh", "Saya calvin");
        client.send("calvin", "mboh", "Saya geje");
        ChatResponse yafiResponse = client.recvAll("yafi");

        assertEquals(yafiResponse.getChatsList().size(), 2);
        ChatResponse yafiAgainResponse = client.recvAll("yafi");
        assertEquals(yafiAgainResponse.getChatsList().size(),0);

        client.send("calvin", "mboh", "Saya calvin lagi");
        ChatResponse yafiAgainAgainResponse = client.recvAll("yafi");
        assertEquals(yafiAgainAgainResponse.getChatsList().size(),1);

        client.join("sadewa", "mboh");
        ChatResponse calvinResponse = client.recvAll("sadewa");
        assertEquals(calvinResponse.getChatsList().size(), 0);
    }

    @Test
    public void testInvalidChannel(){
        client.join("anggur", "buah");
        Response response = client.send("wortel", "buah", "halo aku wortel");
        assertEquals(response.getStatus(),"ERROR");
        ChatResponse anggurResponse = client.recvAll("anggur");
        assertEquals(anggurResponse.getChatsList().size(),0);
    }

    @Test
    public void testSendReceiveMultipleChannel(){
        client.join("bayam","sayur");
        client.join("wortel", "sayur");
        client.join("tomat", "sayur");
        client.join("tomat", "buah");
        client.join("anggur", "buah");

        client.send("bayam", "sayur", "halo aku bayam");
        client.send("wortel", "sayur", "halo aku wortel");
        client.send("tomat","sayur","halo aku tomat");

        client.send("tomat", "buah", "halo aku tomat");
        client.send("anggur", "buah", "halo aku anggur");

        ChatResponse bayamResponse = client.recvAll("bayam");
        assertEquals(bayamResponse.getChatsList().size(),3);
        ChatResponse anggurResponse = client.recvAll("anggur");
        assertEquals(anggurResponse.getChatsList().size(),2);
        ChatResponse tomatResponse = client.recvAll("tomat");
        assertEquals(tomatResponse.getChatsList().size(),5);


    }

    @Test
    public void testBroadcast() {
        client.join("tomat","sayur");
        client.join("tomat","buah");
        client.join("bayam","sayur");
        client.join("anggur", "buah");
        client.sendAll("tomat", "halo aku tomat");

        ChatResponse bayamResponse = client.recvAll("bayam");
        assertEquals(bayamResponse.getChatsList().size(),1);
        ChatResponse anggurResponse = client.recvAll("anggur");
        assertEquals(anggurResponse.getChatsList().size(),1);

    }
}