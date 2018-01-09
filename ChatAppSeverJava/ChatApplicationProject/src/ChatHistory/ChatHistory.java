package ChatHistory;

import ChatHistoryInterfaces.ChatHistoryObservable;
import ChatHistoryInterfaces.ChatHistoryObserver;
import ChatMessage.ChatMessage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

public class ChatHistory implements ChatHistoryObservable {

    private static ChatHistory ourInstance = new ChatHistory();

    HashSet<ChatHistoryObserver> observers;
    ArrayList<ChatMessage> messageHistory;

    private ChatHistory() {

        this.messageHistory = new ArrayList<ChatMessage>();
        this.observers = new HashSet<>();
    }

    public static ChatHistory getInstance() {
        return ourInstance;
    }

    public void insertMessage(ChatMessage message) {

        messageHistory.add(message);
        notifyObservers(message);
    }

    @Override
    public String toString() {

        String allMessages = "";
        allMessages += "---CHAT HISTORY START---\r\n";

        for(ChatMessage message : this.messageHistory) {
            allMessages += message + "\r\n";
        }
        allMessages += "---END OF CHAT HISTORY---";
        return allMessages;
    }

    public String toString(String channel) {

        String wholeMessageHistory = "";
        wholeMessageHistory += "---CHAT HISTORY---\r\n";

        for(ChatMessage msg : this.messageHistory) {

            if(msg.getChannel().equals(channel)) {
                wholeMessageHistory += msg.toString() + "\r\n";
            }
        }

        wholeMessageHistory += "---END OF CHAT HISTORY---";
        return wholeMessageHistory;
    }

    @Override
    public void notifyObservers(ChatMessage chatMessage) {
        for (ChatHistoryObserver obs : observers)
            obs.update(chatMessage);

    }

    @Override
    public void addObserver(ChatHistoryObserver observer) {
        this.observers.add(observer);

    }

    @Override
    public void removeObserver(ChatHistoryObserver observer) {
        this.observers.remove(observer);
    }

    public void broadcastServerMessage(String message) {

        Calendar calendar = Calendar.getInstance();
        Date broadcastTime = calendar.getTime();

        ChatMessage serverMessage = new ChatMessage(message,"SERVER BROADCAST", broadcastTime, "SERVER");
        ChatHistory.getInstance().insertMessage(serverMessage);

    }
}

