package ChatHistoryInterfaces;

import ChatMessage.ChatMessage;

public interface ChatHistoryObserver {

    void update(ChatMessage chatMessage);
}
