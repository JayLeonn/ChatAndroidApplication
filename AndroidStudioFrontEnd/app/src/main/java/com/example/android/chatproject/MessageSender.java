package com.example.android.chatproject;

import android.util.Log;
import java.io.PrintStream;
import java.util.concurrent.ArrayBlockingQueue;
import static android.content.ContentValues.TAG;

public class MessageSender implements Runnable {

    private ArrayBlockingQueue<String> messageQueue;
    private PrintStream output;

    public MessageSender(PrintStream outputStream) {
        this.output = outputStream;
        this.messageQueue = new ArrayBlockingQueue<>(10);
    }

    public void addMessage(String newMessage) {
        this.messageQueue.add(newMessage);
    }

    public void run() {
        while(true) {
            try {
                String newestMessage = this.messageQueue.take();
                sendMessage(newestMessage);
            } catch (InterruptedException i) {
                Log.d(TAG, "ERROR WHILE ACCESSING MESSAGE BLOCKING QUEUE");
                i.printStackTrace();
            }
        }
    }
    private void sendMessage(String msg) {

        String message = msg;
        output.println(message);
    }
}