package com.example.android.chatproject;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;


public class ChatServerCommunicationHandler implements Runnable {

    private final String SERVER_IP = "192.168.1.196";
    private final int SERVER_PORT = 65292;

    //Socket connection to server
    private Socket clientSocket;

    private ConversationUpdater reader;
    private MessageSender sender;

    MainActivity mainActivity;

    boolean setupDone;

    public ChatServerCommunicationHandler(MainActivity main) {
        this.setupDone = false;
        this.mainActivity = main;

    }

    public void run() {

        while(true) {
            if(!setupDone) {
                connectToServer();
                setupStreams();

                Thread senderThread = new Thread(sender);
                Thread updaterThread = new Thread(reader);

                senderThread.start();
                updaterThread.start();

                setupDone = true;
            }
        }
    }

    public void sendMessageToQueue(String msg) {
        sender.addMessage(msg);
    }

    private boolean connectToServer(){

        try {
            clientSocket = new Socket(SERVER_IP, SERVER_PORT);
            return true;
        } catch (IOException io) {
            return false;
        }
    }

    private void setupStreams() {

        try {
            reader = new ConversationUpdater(clientSocket.getInputStream(), mainActivity);
            sender = new MessageSender(new PrintStream(clientSocket.getOutputStream(),true));
        } catch(IOException io) {
            io.printStackTrace();
        }
    }
}