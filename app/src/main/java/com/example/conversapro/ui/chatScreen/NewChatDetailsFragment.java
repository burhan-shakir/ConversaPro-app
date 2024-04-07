package com.example.conversapro.ui.chatScreen;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


import com.example.conversapro.R;
import com.example.conversapro.databinding.FragmentNewChatDetailsBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NewChatDetailsFragment extends Fragment {
    private  EditText chatNameEditText;
    private EditText chatDescriptionEditText;
    private EditText recvNameEditText;
    private FragmentNewChatDetailsBinding binding;

    public NewChatDetailsFragment() {
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
        binding = FragmentNewChatDetailsBinding.inflate(inflater, container, false);
        View v = binding.getRoot();
        chatNameEditText = v.findViewById(R.id.chatNameNewChatForm);
        chatDescriptionEditText = v.findViewById(R.id.descriptionNewChatForm);
        recvNameEditText = v.findViewById(R.id.chatInviteeNewChatForm);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.newChatFormSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chatName = chatNameEditText.getText().toString().trim();
                String chatDescription =  chatDescriptionEditText.getText().toString().trim();
                String recvName = recvNameEditText.getText().toString().trim();
                int roomID = generateRoomID();
                verifyFormDetails(chatName, chatDescription, recvName, roomID);
                //passDetailsToChatScreen(roomID, chatName,recvName);
                Navigation.findNavController(v).navigate(R.id.action_newChatDetails_to_chatScreenFragment);
            }
        });
    }
    private void verifyFormDetails(String chatName, String chatDescription, String recvName, int roomID){
        // TO DO: Check database if roomID exists
        if (TextUtils.isEmpty(chatName) || TextUtils.isEmpty(recvName)){
            Toast.makeText(getContext().getApplicationContext(), "Please fill in all details!", Toast.LENGTH_LONG).show();
        }
        else {
            ChatModel chatModel = new ChatModel(chatName, chatDescription, recvName, getUserID(), roomID);
            DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("chats").child(String.valueOf(roomID));
            database.push().setValue(chatModel);
        }
    }

    private String getUserID(){
        // TO DO: Get the current User ID
        return "Burhan";
    }
    private int generateRoomID(){
        Random random = new Random();
        int rand = random.nextInt(100);
        return rand;
    }
    private void passDetailsToChatScreen(int roomID, String chatName, String recvName){
        ChatDetailsTransfer listener = (ChatDetailsTransfer) getActivity();
        listener.onChatDetailsRecv(String.valueOf(roomID), chatName, recvName);
    }
}