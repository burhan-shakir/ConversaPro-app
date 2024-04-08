package com.example.conversapro.ui.chatScreen;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.conversapro.KerberosProtocol.Client;
import com.example.conversapro.R;
import com.example.conversapro.databinding.FragmentChatScreenBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatScreenFragment extends Fragment{
    private ListView listViewChat;
    private EditText editTextMessage;
    private Button buttonSendMessage;
    private TextView chatName;
    private DatabaseReference databaseReferenceChats;
    private List<MsgModel> messages;
    private String currentUserName;
    private String currentRoomID;
    private String currentChatName;
    private String currentRcvName;
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
        initChatDetails();
        setUserName();
        initializeFirebase();
        sendMessage();
        initMessageListener();

        return binding.getRoot();
    }
    private void initializeViews(View view) {
        listViewChat = view.findViewById(R.id.listViewChat);
        editTextMessage = view.findViewById(R.id.editTextMessage);
        buttonSendMessage = view.findViewById(R.id.buttonSendMessage);
        chatName = view.findViewById(R.id.chatName);

    }
    private void initializeFirebase() {
        databaseReferenceChats = FirebaseDatabase.getInstance().getReference().child("chats");
    }

    private void sendMessage() {
        buttonSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = editTextMessage.getText().toString().trim();
                if (!TextUtils.isEmpty(message)) {
                     // Set the sender's name or ID
                    String receiver = getRecvName();
                    MsgModel chatMessage = new MsgModel(message, currentUserName, receiver);
                    databaseReferenceChats.child(currentRoomID).child("messages").push().setValue(chatMessage);
                    editTextMessage.setText("");
                    adapter.add(chatMessage);
                    adapter.notifyDataSetChanged();
                        }
                    }
        });

    }
    private void initMessageListener(){
        databaseReferenceChats.child(currentRoomID).child("messages").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messages = new ArrayList<>();
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    MsgModel chatMessage = messageSnapshot.getValue(MsgModel.class);
                    messages.add(chatMessage);
                }
                adapter = new ChatAdapter(getActivity(), messages);
                listViewChat.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });
    }
    private void setUserName() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
        String uid = Client.getInstance().getUid();
        mDatabase.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentUserName = snapshot.child("name").getValue(String.class);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private String getRecvName() {
        return currentRcvName;
    }
    private void initChatDetails(){

        if (getArguments() != null) {
            currentRoomID = getArguments().getString("roomID");
            currentChatName = getArguments().getString("chatName");
            currentRcvName = getArguments().getString("recvName");
            chatName.setText(currentChatName);
        }
        else{
            Toast.makeText(getContext(), "CHAT SCREEN LOAD FAILED", Toast.LENGTH_SHORT).show();
        }

    }
}