package com.example.conversapro.ui.chatScreen;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.conversapro.R;
import com.example.conversapro.databinding.FragmentChatScreenBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChatScreenFragment extends Fragment {
    private ListView listViewChat;
    private EditText editTextMessage;
    private Button buttonSendMessage;
    private DatabaseReference databaseReference;
    private ChatAdapter adapter;
    public FragmentChatScreenBinding binding;
    public ChatScreenFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Intent intent = new Intent("HIDE_MENU_ACTION");
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
        binding = FragmentChatScreenBinding.inflate(inflater, container, false);
        initializeViews(binding.getRoot());
        initializeFirebase();
        sendMessage();
        initMessageListener();

        return binding.getRoot();
    }
    private void initializeViews(View view) {
        listViewChat = view.findViewById(R.id.listViewChat);
        editTextMessage = view.findViewById(R.id.editTextMessage);
        buttonSendMessage = view.findViewById(R.id.buttonSendMessage);
    }
    private void initializeFirebase() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("messages");
    }
    private void sendMessage() {
        buttonSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editTextMessage.getText().toString().trim();
                if (!TextUtils.isEmpty(message)) {
                    String sender = getUserName(); // Set the sender's name or ID
                    MsgModel chatMessage = new MsgModel(message, sender);
                    databaseReference.push().setValue(chatMessage);
                    editTextMessage.setText("");
                }
            }
        });

    }
    private String getUserName() {
        // TO DO: Fetch the user name to send to database in message
        return "Jane Doe"; // Placeholder name
    }

    private void initMessageListener(){
        adapter = new ChatAdapter(getActivity(), new ArrayList<MsgModel>());
        listViewChat.setAdapter(adapter);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                MsgModel msgModel = snapshot.getValue(MsgModel.class);
                adapter.add(msgModel);
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
    }
}