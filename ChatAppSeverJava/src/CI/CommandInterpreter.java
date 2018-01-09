package CI;

import ChatHistory.ChatHistory;
import ChatHistoryInterfaces.ChatHistoryObserver;
import ChatMessage.ChatMessage;
import ChatServer.ChatServer;
import User.User;
import UsersList.UsersList;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;

public class CommandInterpreter implements Runnable, ChatHistoryObserver {

    private InputStream inputStream;
    private PrintStream printStream;
    private Scanner scanner;
    private HashSet<String> commandSet;
    private User user;

    //Command recognition symbol. A command has to start with this symbol.
    private static final char COMMAND_SYMBOL = '/';
    //Username reserved for server broadcast
    private static final String SERVER_USERNAME = "SYSTEM BROADCAST";
    //Maximum user name length
    private static final int MAX_USERNAME_LENGTH = 10;

    public CommandInterpreter(InputStream inputStream, PrintStream printStream, String defaultChannel, ChatServer currentServer) {
        this.inputStream = inputStream;
        this.printStream = printStream;
        this.scanner = new Scanner(inputStream);
        this.commandSet = loadCommandSet();
        this.user = new User("", defaultChannel);
    }

    public void run() {
        boolean running = true;
        boolean helpDisplayed = false;
        try {
            while (running) {
                if (!helpDisplayed) {
                    printStream.println("Connection has been established!");
                    printStream.println("Welcome to chatting! Hit ENTER and then type " + COMMAND_SYMBOL + "help to start");
                    helpDisplayed = true;
                }
                String message;
                message = scanner.nextLine();
                message = message.trim();
                if (commandSet.contains(message)) {
                    if (message.equals(COMMAND_SYMBOL + "help")) {

                        printStream.println("\r\n         Commands and their explanations: \r\n" +
                                "---------------------------------------------------------------\r\n" +
                                COMMAND_SYMBOL + "help - Displays this list of commands\r\n" +
                                COMMAND_SYMBOL + "user - Sets username. Must be set before messaging !\r\n" +
                                COMMAND_SYMBOL + "history - Displays message history for this channel\r\n" +
                                COMMAND_SYMBOL + "online - Displays every user online\r\n" +
                                COMMAND_SYMBOL + "quit - Disconnects user from the chat");
                    }
                    //quit
                    if (message.equals(COMMAND_SYMBOL + "quit")) {
                        printStream.println("Goodbye " + this.user.getUserName() + "!");
                        inputStream.close();
                    }
                    //history
                    if (message.equals(COMMAND_SYMBOL + "history")) {
                        if (!this.user.getUserName().equals("")) {
                            printStream.println("Chat history: ");
                            printStream.println(ChatHistory.getInstance().toString(user.getCurrentChannel()));
                        } else {
                            printStream.println("Cannot see history without username!");
                        }
                    }
                    //user
                    if (message.equals(COMMAND_SYMBOL + "user")) {
                        boolean userNameSet = false;
                        while (!userNameSet) {
                            printStream.println("Type in a username (max 10characters)");
                            String input = scanner.nextLine();
                            String possibleUserName = input.trim();
                            if (possibleUserName.length() >= 2 && possibleUserName.length() <= MAX_USERNAME_LENGTH && !possibleUserName.equals(SERVER_USERNAME)) {
                                if (!(UsersList.getInstance().checkIfUserNameExists(possibleUserName))) {
                                    while (true) {
                                        printStream.println("Selected username: " + possibleUserName + " is available, use Y/N ?");
                                        String choice = scanner.nextLine();
                                        if (choice.equals("Y") || choice.equals("y")) {
                                            user.setUserName(possibleUserName);
                                            UsersList.getInstance().addUser(user);
                                            printStream.println("Username set as " + user.getUserName() + ", start chatting !");
                                            printStream.println("You are now chatting!");
                                            userNameSet = true;

                                            ChatHistory.getInstance().addObserver(this);

                                            break;
                                        }
                                        if (choice.equals("N") || choice.equals("n")) {
                                            printStream.println("Username not selected !");
                                            break;
                                        } else if (!choice.equals("n") && !choice.equals("N") && !choice.equals("Y") && !choice.equals("Y")) {
                                            printStream.println("Invalid input, answer either Y or N !");
                                        }
                                    }
                                } else {
                                    printStream.println("This username is already taken. Try" + possibleUserName + "1");
                                }
                            } else {
                                printStream.println("Username too short or too long");
                            }
                        }
                    }
                    //Online
                    if (message.equals(COMMAND_SYMBOL + "online")) {
                        printStream.println("---- Users online ----");
                        for (User usr : UsersList.getInstance().getAllUsersInList()) {
                            printStream.println(usr.getUserName() + " @ " + usr.getCurrentChannel());
                        }
                    }
                } else if (!user.getUserName().equals("")) {
                    if (message.contains("" + COMMAND_SYMBOL)) {
                        printStream.println("Were you trying to input a command ? Commands start with the " + COMMAND_SYMBOL + " character.");
                    } else {
                        //Send message
                        if (!message.equals("")) {
                            Calendar calendar = Calendar.getInstance();
                            Date dateTime = calendar.getTime();
                            ChatMessage msg = new ChatMessage(message, user.getUserName(), dateTime, user.getCurrentChannel());
                            ChatHistory.getInstance().insertMessage(msg);
                        } else {
                            printStream.println("Cannot send an empty message");
                        }
                    }
                }
            }
        } catch (Exception exception) {
            UsersList.getInstance().removeUser(user);
            ChatHistory.getInstance().removeObserver(this);
            System.out.println("User " + user.getUserName() + " disconnected");
            this.user = null;
        }
    }
    private HashSet<String> loadCommandSet() {

        HashSet<String> commandSet = new HashSet<>();

        commandSet.add(COMMAND_SYMBOL + "help");
        commandSet.add(COMMAND_SYMBOL + "user");
        commandSet.add(COMMAND_SYMBOL + "history");
        commandSet.add(COMMAND_SYMBOL + "online");
        commandSet.add(COMMAND_SYMBOL + "quit");

        return commandSet;
    }
    @Override
    public void update(ChatMessage chatMessage) {

        if(chatMessage.getChannel().equals(user.getCurrentChannel()) || chatMessage.getChannel().equals("SERVER")){
            printStream.println(chatMessage);
        }
    }
}
