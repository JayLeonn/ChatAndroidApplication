package com.example.android.chatproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    //Chat box, message box and button
    TextView convoWindow;
    EditText messageBox;
    Button sendMessageButton;
    ChatServerCommunicationHandler chatHandler;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        chatHandler = new ChatServerCommunicationHandler(this);
        convoWindow = (TextView) findViewById(R.id.convoWindow);
        convoWindow.setMovementMethod(new ScrollingMovementMethod());
        messageBox = (EditText) findViewById(R.id.messageBox);
        sendMessageButton = (Button) findViewById(R.id.sendButton);
        scrollView = (ScrollView) findViewById(R.id.scrollViewForConversation);


        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = messageBox.getText().toString();
                sendMessageToHandler(message);


                messageBox.getText().clear();
            }
        });

        Thread handlerThread = new Thread(chatHandler);
        handlerThread.start();

    }

    public void appendMessageToConversation(String newMessage) {

        convoWindow.append(newMessage);
        scrollDown();
    }

    public void sendMessageToHandler(String msg) {

        if(msg.equals("!quit")) {
            onDestroy();
        }
        chatHandler.sendMessageToQueue(msg);

    }

    private void scrollDown() {

        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.smoothScrollTo(scrollView.getScrollY(), scrollView.getScrollY()
                        + scrollView.getHeight());
            }
        });
    }
}