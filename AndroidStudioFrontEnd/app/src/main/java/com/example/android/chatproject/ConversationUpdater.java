package com.example.android.chatproject;


import java.io.InputStream;
import java.util.Scanner;

public class ConversationUpdater implements Runnable {

    private boolean updateRunning = true;
    private Scanner reader;
    private MainActivity mainActivity;

    public ConversationUpdater(InputStream connection, MainActivity main) {

        this.reader = new Scanner(connection);
        this.mainActivity = main;
    }

    public void run() {

        while(updateRunning) {
            final String message = reader.nextLine();
            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mainActivity.appendMessageToConversation(message + "\r\n");
                }
            });

        }
    }
}
