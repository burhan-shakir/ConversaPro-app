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

import com.example.conversapro.R;
import com.example.conversapro.databinding.FragmentChatScreenBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatScreenFragment extends Fragment implements ChatDetailsTransfer {
    private ListView listViewChat;
    private EditText editTextMessage;
    private Button buttonSendMessage;
    private TextView chatName;
    private DatabaseReference databaseReferenceChats;
    private List<MsgModel> messages;
    private String currentRoomID;
    private String currentChatName;
    private String currentRcvName;
    private ChatAdapter adapter;
    public FragmentChatScreenBinding binding;
    private ChatModel chatModel;
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
        setChatName();
        //initAdapter();
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

    private void setChatName() {
        /**databaseReferenceChats.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatModel = snapshot.getValue(ChatModel.class);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });**/
        chatName.setText("ConversaPro");
    }
    private void initAdapter(){
        adapter = new ChatAdapter(getActivity(), new ArrayList<MsgModel>());
        listViewChat.setAdapter(adapter);
    }

    private void sendMessage() {
        buttonSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = editTextMessage.getText().toString().trim();
                if (!TextUtils.isEmpty(message)) {
                    String sender = getUserName(); // Set the sender's name or ID
                    String receiver = getRecvName();
                    MsgModel chatMessage = new MsgModel(message, sender, receiver);
                    databaseReferenceChats.child("57").child("messages").push().setValue(chatMessage);
                    editTextMessage.setText("");
                    adapter.add(chatMessage);
                    adapter.notifyDataSetChanged();
                        }
                    }
        });

    }
    private void initMessageListener(){
        databaseReferenceChats.child("57").child("messages").addListenerForSingleValueEvent(new ValueEventListener() {
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
    private String getUserName() {
        // TO DO: Fetch the user name to send to database in message
        return "Jane Doe"; // Placeholder name
    }
    private String getRecvName() {
        // TO DO: Fetch the recvr name to send to database in message
        return "currentRcvName"; // Placeholder name
    }
    private String getRoomID() {
        // TO DO: Fetch the roomID to send to database in message
        return "currentRoomID"; // Placeholder name
    }

    @Override
    public void onChatDetailsRecv(String roomID, String chatName, String recvName) {
        currentRoomID = roomID;
        currentChatName = chatName;
        currentRcvName = recvName;
    }
}