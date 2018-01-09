package ChatHistoryInterfaces;

import ChatMessage.ChatMessage;

public interface ChatHistoryObservable {

    void notifyObservers(ChatMessage chatMessage);
    void addObserver(ChatHistoryObserver observer);
    void removeObserver(ChatHistoryObserver observer);
}
