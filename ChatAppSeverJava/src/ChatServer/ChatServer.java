package ChatServer;

import CI.CommandInterpreter;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;

public class ChatServer {

    private HashSet<String> channels;

    public ChatServer() {

        this.channels = new HashSet<>();
        this.channels.add("Main");
    }

    public void serve() {

        try {

            ServerSocket server = new ServerSocket(0, 3);
            ChatConsole chatConsole = new ChatConsole();
            chatConsole.register();

            while(true) {

                System.out.println("Listening to port " + server.getLocalPort());
                Socket connectedUser = server.accept();
                System.out.println("New connection established from " + connectedUser.getInetAddress().toString());
                CommandInterpreter CI = new CommandInterpreter(connectedUser.getInputStream(),
                        new PrintStream(connectedUser.getOutputStream(), true), getChannels().get(0), this);
                Thread userThread = new Thread(CI);
                userThread.start();
            }
        } catch(IOException ioError) {
            ioError.printStackTrace();
            System.out.println("Connection error !");
        }
    }
    public ArrayList<String> getChannels() {
        return new ArrayList<String>(this.channels);
    }
}
