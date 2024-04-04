package com.example.conversapro;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class ChatView extends AppCompatActivity {
    FirebaseDatabase database;
    private ListView listViewChat;
    private EditText editTextMessage;
    private Button buttonSendMessage;
    private DatabaseReference databaseReference;
    private List<MsgModel> messagesList = new ArrayList<>();
    private ChatAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        listViewChat = findViewById(R.id.listViewChat);
        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSendMessage = findViewById(R.id.buttonSendMessage);

        adapter = new ChatAdapter(this, messagesList);
        listViewChat.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("messages");
        buttonSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editTextMessage.getText().toString().trim();
                if (!TextUtils.isEmpty(message)) {
                    String sender = "Burhan"; // Set the sender's name or ID
                    MsgModel chatMessage = new MsgModel(message, sender);
                    databaseReference.push().setValue(chatMessage);
                    editTextMessage.setText("");
                }
            }
        });
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                MsgModel chatMessage = dataSnapshot.getValue(MsgModel.class);
                messagesList.add(chatMessage);
                adapter.notifyDataSetChanged();
                listViewChat.smoothScrollToPosition(adapter.getCount() - 1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //display_chat_messages();
        //FloatingActionButton fab =
               // (FloatingActionButton) findViewById(R.id.fab);
        //ChatController controller = new ChatController(this);
        //fab.setOnClickListener(controller);
    }
}