package com.company;
import ChatServer.ChatServer;

public class Main {

    public static void main(String[] args) {

        ChatServer server = new ChatServer();
        server.serve();
    }
}
