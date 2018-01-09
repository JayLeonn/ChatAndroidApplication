package ChatMessage;

import java.text.SimpleDateFormat;
import java.util.Date;


public class ChatMessage {

    private String message;
    private String sender;
    private Date sendTime;
    private String channel;

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM 'at' HH:mm ");

    public ChatMessage(String msg, String userName, Date timeStamp, String channel) {

        this.message = msg;
        this.sender = userName;
        this.sendTime = timeStamp;
        this.channel = channel;
    }

    @Override
    public String toString() {
        return dateFormat.format(sendTime) + "| " + sender + ": " + this.message;
    }
    public String getChannel() {
        return this.channel;
    }
}

