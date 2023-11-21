package com.jackhack2002.mychat;

import static com.jackhack2002.mychat.R.id.messageId;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private EditText messageEditText;
    private MessageAdapter chatAdapter;
    private List<String> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        messageEditText = findViewById(messageId);
        Button sendButton = findViewById(R.id.sendMessage);
        RecyclerView chatRecyclerView = findViewById(R.id.recycler);

        // Set up RecyclerView with a LinearLayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        chatRecyclerView.setLayoutManager(layoutManager);

        // Create an empty list to hold chat messages
        messages = new ArrayList<>();

        // Create and set the adapter for the RecyclerView
        chatAdapter = new MessageAdapter(messages);
        chatRecyclerView.setAdapter(chatAdapter);

       /* sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        }); */
    }

    private void sendMessage() {
        String message = messageEditText.getText().toString().trim();
        if (!message.isEmpty()) {
            // Add the message to the list
            messages.add(message);
            // Notify the adapter that a new item is inserted
            chatAdapter.notifyItemInserted(messages.size() - 1);
            // Clear the input field
            messageEditText.setText("");
        }
    }
}












