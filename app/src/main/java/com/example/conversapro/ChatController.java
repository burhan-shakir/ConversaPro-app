package com.example.conversapro;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ChatController extends MainActivity implements View.OnClickListener {
    private static Context chatContext;

    public ChatController(Context context) {
        ChatController.chatContext = context;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.chat_btn) {
            Intent intent = new Intent(chatContext, ChatView.class);
            chatContext.startActivity(intent);
        } else if (v.getId() == R.id.fab) {
            display_chat_messages();
            handleSendingMessage();

        }
    }

    private void handleSendingMessage(){
        new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText) findViewById(R.id.input);
                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database
                database.getInstance()
                        .getReference()
                        .push()
                        .setValue(new MsgModel(input.getText().toString(), "Burhan Shakir")
                        );
                // Clear the input
                input.setText("");
            }

        };

    }
    private void display_chat_messages() {
        //DatabaseReference reference = database.getInstance().getReference();
        ListView listOfMessages = (ListView) findViewById(R.id.list_of_messages);

        listOfMessages.setAdapter(new ChatAdapter());
    }
    private class ChatAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final MsgModel[] model = new MsgModel[1];
            DatabaseReference reference = database.getInstance().getReference();
            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    model[0] =  snapshot.getValue(MsgModel.class);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            };
            //TextView messageText = (TextView)convertView.findViewById(R.id.message_text);
            //TextView messageUser = (TextView)convertView.findViewById(R.id.message_user);
            //TextView messageTime = (TextView)convertView.findViewById(R.id.message_time);
            // Set their text
            //messageText.setText(model[0].getMessage());
            //messageUser.setText(model[0].getSenderID());
            // Format the date before showing it
            //messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
            //        model[0].getTimeStamp()));
            return null;
        }
    }

}
