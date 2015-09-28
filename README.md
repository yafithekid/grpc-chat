# grpc-chat
simple RPC chat with Google RPC

### Members

- 13512014 Muhammad Yafi
- 13512066 Calvin Sadewa

### Prerequisites

1. Install MongoDB. Ensure `mongod` is running on port 27017
2. Install Java JDK 1.8
3. Install Gradle, add it to your PATH environment variables.
4. Ensure gradle can works by typing `gradle` in your command prompt.

### Interface Definition Language

The IDL is located at `src/main/proto/rpcchat.proto`

### Proto to Java

1. Run `gradle generateProto`
2. The result is available at `src/generated/main/grpc` and `src/generated/main/java`

### Run the jar files

1. Run `gradle clientJar serverJar` on root project dir
2. Ensure MongoDB is running
3. In `build/libs` folder, run `java -jar chat-server-1.0.jar` and `java -jar chat-client-1.0.jar`

### Available commands

1. `/NICK <nickname>`: change your nickname to **nickname**. Default nickname is `paijo`
2. `/JOIN <channelname>`: join current nickname to **channelname**
3. `/LEAVE <channel>`: leave current nickname from **channel**
4. `/EXIT`: terminate the application
5. `@<channelname> <any_text>` send **any_text** to **channelname**
6. `<any_text>` send **any_text** to all channels joined by nickname.

### Test scripts

Ensure MongoDB is running. Run the src/test/java/com/if4031/proto/ChatHandlerTest.java. The file creates a server on localhost, create a client that connect to the server, and call the method using RPC.
