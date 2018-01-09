package ChatServer;

import ChatHistory.ChatHistory;
import ChatHistoryInterfaces.ChatHistoryObserver;
import ChatMessage.ChatMessage;

public class ChatConsole implements ChatHistoryObserver {

    public ChatConsole() {}

    public void register() {
        ChatHistory.getInstance().addObserver(this);
    }

    @Override
    public void update(ChatMessage chatMessage) {
        System.out.println(chatMessage.toString());
    }
}

